package de.fc.projekt.tinkerforge;

import com.tinkerforge.*;
import de.fc.projekt.comm.RabbitMQConnector;
import de.fc.projekt.comm.RedisConnector;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TinkerForgeConnectorImpl implements TinkerForgeConnector {

    private String HOST = null;
    private int PORT = 0;

    // Change XYZ to the UID of your Distance IR Bricklet 2.0
    private String UID = null;

    private IPConnection ipcon; // Create IP connection
    private BrickletDistanceIRV2 dir = null;

    private RabbitMQConnector rabbitMQConnector;

    @Override
    public void connect(String hostname, Integer portNum, String UID) throws Exception {

        this.HOST = hostname;
        this.PORT = portNum;
        this.UID = UID;


        this.ipcon = new IPConnection(); // Create IP connection
        this.dir = new BrickletDistanceIRV2(this.UID, this.ipcon); // Create device object

        this.ipcon.connect(HOST, PORT); // Connect to brickd
        // Don't use device before ipcon is connected

        // Add distance listener
        dir.addDistanceListener(this::persistSensorReading);

        // Configure threshold for distance "smaller than 30 cm"
        // with a debounce period of 1s (1000ms)
        dir.setDistanceCallbackConfiguration(500, false, '<', 30 * 10, 0);
    }

    @Override
    public void disconnect() throws NotConnectedException {
        this.ipcon.disconnect();
    }

    @Override
    public boolean persistSensorReading(int distance) {
        System.out.println("Distance: " + distance / 10.0 + " cm");
        return false;
    }

    @Override
    public void setRedisConnector(RedisConnector redisConnector) throws IOException, TimeoutException {
        //TODO Implement in next iteration
    }

    @Override
    public void startActiveCaching() {
        //TODO Implement in next iteration
    }

    @Override
    public void flushCache() {
        //TODO Implement in next iteration
    }

    @Override
    public void setRabbitMQConnector(RabbitMQConnector rabbitMQConnecter) throws IOException, TimeoutException {
        this.rabbitMQConnector = rabbitMQConnecter;
        this.rabbitMQConnector.connect();
    }


}
