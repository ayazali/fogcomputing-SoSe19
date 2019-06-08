package de.fc.projekt.webnode.models;

import javax.persistence.*;

@Entity
public class ParkingSpotInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long parkingSpotId;

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public String getParkingDescription() {
        return parkingDescription;
    }

    public void setParkingDescription(String parkingDescription) {
        this.parkingDescription = parkingDescription;
    }

    private String parkingName;
    private String parkingDescription;
    private ParkingSpotStatus Status;

    @OneToOne
    @JoinColumn(name = "device_id")
    private DeviceInfo Device;

    public ParkingSpotInfo() {
    }

    public ParkingSpotInfo(String parkingName, String parkingDescription) {
        this.parkingName = parkingName;
        this.parkingDescription = parkingDescription;
    }
}