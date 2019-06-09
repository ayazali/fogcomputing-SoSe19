package de.fc.projekt.tinkerforge;

import com.google.gson.JsonObject;
import com.tinkerforge.BrickletDistanceIRV2;
import com.tinkerforge.IPConnection;
import com.tinkerforge.NotConnectedException;
import de.fc.projekt.comm.RabbitMQConnector;
import de.fc.projekt.comm.RedisConnector;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.*;

public class TinkerForgeConnectorMockImpl implements TinkerForgeConnector {

    private String HOST = null;
    private int PORT = 0;

    // Change XYZ to the UID of your Distance IR Bricklet 2.0
    private String UID = null;

    private IPConnection ipcon; // Create IP connection
    private BrickletDistanceIRV2 dir = null;

    private RabbitMQConnector rabbitMQConnector;
    private RedisConnector redisConnector;

    private boolean cachedMode = false;
    private ScheduledFuture<?> beeperHandle = null;

    private String deviceId;

    @Override
    public void connect(String hostname, Integer portNum, String UID) throws Exception {

        this.HOST = hostname;
        this.PORT = portNum;
        this.UID = UID;

    }

    @Override
    public void disconnect() throws NotConnectedException {
        //no functionality needed in mock impl.
    }

    @Override
    public boolean persistSensorReading(int distance) {

        JsonObject distJsonObj = new JsonObject();
        distJsonObj.addProperty("device-uuid", this.getDeviceId());
        distJsonObj.addProperty("uid", UUID.randomUUID().toString());
        distJsonObj.addProperty("value", distance);
        Date now = new Date(System.currentTimeMillis());
        distJsonObj.addProperty("timestamp", this.toISO8601UTC(now));

        if(cachedMode){
            return this.redisConnector.post(distJsonObj);
        } else {
            return this.rabbitMQConnector.post(distJsonObj);
        }
    }

    @Override
    public void setRabbitMQConnector(RabbitMQConnector rabbitMQConnector) throws IOException, TimeoutException {
        this.rabbitMQConnector = rabbitMQConnector;
        this.rabbitMQConnector.connect();
    }

    @Override
    public void setRedisConnector(RedisConnector redisConnector) {
        this.redisConnector = redisConnector;
        this.redisConnector.connect();
    }

    @Override
    public void startActiveCaching() {
        this.cachedMode = true;
        ScheduledExecutorService executor =
                Executors.newSingleThreadScheduledExecutor();

        Runnable periodicTask = () -> {
            // Invoke method(s) to do the work
            boolean statusOf = this.rabbitMQConnector.isConnectionOpen();
            if(statusOf) {
                this.cachedMode = false;
                this.beeperHandle.cancel(true);
                System.out.println("Connection restored to rabittmq.");
                this.flushCache();
            } else {
                System.out.println("No connection to rabittmq.");
            }

        };

        this.beeperHandle = executor.scheduleAtFixedRate(periodicTask, 0, 10, TimeUnit.SECONDS);
    }

    @Override
    public void flushCache() {

        List<JsonObject> cachedData = this.redisConnector.getValues();

        for(JsonObject singleEntry: cachedData){
            this.rabbitMQConnector.post(singleEntry);
        }
    }

//    public void generateRandomValues(){
//        int max = 120;
//        int min = 30;
//        int random = (int )(Math.random() * max + min);
//
//        this.persistSensorReading(random);
//    }


    public String toISO8601UTC(Date date) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        return df.format(date);
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
