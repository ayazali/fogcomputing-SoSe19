package de.fc.projekt.comm;


import com.google.gson.JsonObject;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQConnector {

    private String QUEUE_NAME;
    private String MQ_HOSTNAME;
    private int MQ_PORT;
    private String MQ_USERNAME;
    private String MQ_PASSWORD;

    private boolean isConnected = false;
    private Channel channel;
    private Connection connection;

    public RabbitMQConnector(String qname, String qhostname, int qport, String qUsername, String qPassword){
        this.QUEUE_NAME = qname;
        this.MQ_HOSTNAME = qhostname;
        this.MQ_PORT = qport;
        this.MQ_USERNAME = qUsername;
        this.MQ_PASSWORD = qPassword;
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
        return this.isConnected = true;
    }

    public boolean post(JsonObject jsonObject) {
        try {
            this.channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, jsonObject.toString().getBytes());
            System.out.println(" [x] Sent '" + jsonObject.toString() + "'");
            return true;
        } catch (IOException e) {
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

    public boolean isConnectionOpen(){
        return this.connection.isOpen();
    }
}
