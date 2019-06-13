package de.fc.projekt.webnode.comm;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import de.fc.projekt.webnode.models.*;
import de.fc.projekt.webnode.repositories.DeviceLogRepository;
import de.fc.projekt.webnode.repositories.DeviceRepository;
import de.fc.projekt.webnode.repositories.ParkingLogRepository;
import de.fc.projekt.webnode.repositories.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeoutException;
@Component
public class RabbitMQConnector {

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private DeviceLogRepository deviceLogRepository;
    @Autowired
    private ParkingSpotRepository parkingSpotRepository;
    @Autowired
    private ParkingLogRepository parkingLogRepository;
    private String QUEUE_NAME;
    private String MQ_HOSTNAME;
    private int MQ_PORT;
    private String MQ_USERNAME;
    private String MQ_PASSWORD;

    private boolean isConnected = false;
    private Channel channel;
    private Connection connection;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");

    private float DISTANCE_MAX = 150 * 10;
    private float THRESHOLD_DISTANCE_MIN = 10 * 10;
    private float THRESHOLD_DISTANCE_MAX = 120 * 10;
    private float DISTANCE_MIN = 20 * 10;

    public RabbitMQConnector() {
    }

    public RabbitMQConnector setQUEUE_NAME(String QUEUE_NAME) {
        this.QUEUE_NAME = QUEUE_NAME;
        return this;
    }

    public RabbitMQConnector setMQ_HOSTNAME(String MQ_HOSTNAME) {
        this.MQ_HOSTNAME = MQ_HOSTNAME;
        return this;
    }

    public RabbitMQConnector setMQ_PORT(int MQ_PORT) {
        this.MQ_PORT = MQ_PORT;
        return this;
    }

    public RabbitMQConnector setMQ_USERNAME(String MQ_USERNAME) {
        this.MQ_USERNAME = MQ_USERNAME;
        return this;
    }

    public RabbitMQConnector setMQ_PASSWORD(String MQ_PASSWORD) {
        this.MQ_PASSWORD = MQ_PASSWORD;
        return this;
    }

    public boolean connect() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUsername(MQ_USERNAME);
        factory.setPassword(MQ_PASSWORD);
        factory.setHost(MQ_HOSTNAME);
        factory.setPort(MQ_PORT);

        this.connection = factory.newConnection();
        this.channel = connection.createChannel();
        //TODO Implement custom heartbeat interval in seconds
        //cf.setRequestedHeartbeat(60);
        this.channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        return this.isConnected = true;
    }

    public void startListener() throws IOException {
        Gson gson = new Gson();

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            this.processIncomingMessage(gson.fromJson(message, JsonObject.class));
        };

        System.out.println(" [*] Waiting for messages.");
        this.channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }

    private boolean processIncomingMessage(JsonObject jsonObject) {

        //TODO Implement model persistence for sensor data here
        System.out.println(" Processed message " + jsonObject.toString());

        try {
            var deviceuuid = jsonObject.get("device-uuid");
            var uid = jsonObject.get("uid").getAsString();
            var distance = jsonObject.get("value").getAsFloat();
            var timestamp = jsonObject.get("timestamp").getAsString();
            var date = formatter.parse(timestamp);
            var deviceStatus = ( distance > DISTANCE_MIN && distance < DISTANCE_MAX )?
                    DeviceStatus.ACTIVE_WITHIN_THRESHHOLD: DeviceStatus.ACTIVE_OUTSIDE_THRESHHOLD;
            var parkingSpotStatus = ( distance > THRESHOLD_DISTANCE_MIN && distance < THRESHOLD_DISTANCE_MAX )?
                    ParkingSpotStatus.OCCUPIED: ParkingSpotStatus.FREE ;
            DeviceInfo deviceInfo = deviceRepository.findByUniqueId(deviceuuid.getAsString());
            ParkingSpotInfo parkingSpotInfo = parkingSpotRepository.findByDeviceInfoDeviceId(deviceInfo.getDeviceId());
            var currentStatus = parkingSpotInfo.getStatus();
            DeviceLog deviceLog = new DeviceLog()
                    .setDeviceId(deviceInfo.getDeviceId())
                    .setCreatedOn(date)
                    .setDistance(distance)
                    .setMessageUUID(uid)
                    .setStatus(deviceStatus);
            deviceLogRepository.save(deviceLog);
            if(!currentStatus.equals(parkingSpotStatus))
            {
                parkingSpotInfo.setStatus(parkingSpotStatus);
                parkingSpotRepository.save(parkingSpotInfo);
                ParkingLog parkingLog = new ParkingLog()
                        .setParkingSpotId(parkingSpotInfo.getParkingSpotId())
                        .setStatus(parkingSpotInfo.getStatus())
                        .setLogDetail(uid)
                        .setCreatedOn(Date.from(Instant.now()));
                parkingLogRepository.save(parkingLog);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public boolean isConnectionOpen() {
        return this.connection.isOpen();
    }
}
