package de.fc.projekt.tinkerforge;

public interface TinkerForgeConnector {

    void connect(String hostname, Integer portNum, String UID) throws Exception;
    void disconnect() throws com.tinkerforge.NotConnectedException;
    boolean persistSensorReading(int distance);
}
