package de.fc.projekt.tinkerforge;

import com.google.gson.JsonObject;
import com.tinkerforge.BrickletDistanceIRV2;
import com.tinkerforge.IPConnection;
import com.tinkerforge.NotConnectedException;
import de.fc.projekt.comm.ZeroMQConnector;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TinkerForgeConnectorMockImpl implements TinkerForgeConnector {

    private String HOST = null;
    private int PORT = 0;

    // Change XYZ to the UID of your Distance IR Bricklet 2.0
    private String UID = null;

    private IPConnection ipcon; // Create IP connection
    private BrickletDistanceIRV2 dir = null;

    private ZeroMQConnector zeroMQConnector;

    public TinkerForgeConnectorMockImpl(){

        this.zeroMQConnector = new ZeroMQConnector("*", 5563);
        this.zeroMQConnector.connect();
    }

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
        //System.out.println("Distance: " + distance / 10.0 + " cm");
        JsonObject distJsonObj = new JsonObject();
        distJsonObj.addProperty("value", distance);
        Date now = new Date(System.currentTimeMillis());
        distJsonObj.addProperty("timestamp", this.toISO8601UTC(now));
        return this.zeroMQConnector.post(distJsonObj);
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

}
