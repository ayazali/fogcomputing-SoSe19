package de.fc.projekt.tinkerforge;

import de.fc.projekt.comm.RabbitMQConnector;
import de.fc.projekt.comm.RedisConnector;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface TinkerForgeConnector {

    void connect(String hostname, Integer portNum, String UID) throws Exception;
    void disconnect() throws com.tinkerforge.NotConnectedException;
    boolean persistSensorReading(int distance);
    void setRabbitMQConnector(RabbitMQConnector rabbitMQConnector) throws IOException, TimeoutException;
    void setRedisConnector(RedisConnector redisConnector) throws IOException, TimeoutException;
    void startActiveCaching();
    void flushCache();
}
