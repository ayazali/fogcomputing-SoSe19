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


    public String getParkingDescription() {
        return parkingDescription;
    }

    private String parkingName;
    private String parkingDescription;
    private ParkingSpotStatus status;

    public long getParkingSpotId() {
        return parkingSpotId;
    }

    public ParkingSpotInfo setParkingName(String parkingName) {
        this.parkingName = parkingName;
        return this;
    }

    public ParkingSpotInfo setParkingDescription(String parkingDescription) {
        this.parkingDescription = parkingDescription;
        return this;
    }

    public ParkingSpotStatus getStatus() {
        return status;
    }

    public ParkingSpotInfo setStatus(ParkingSpotStatus status) {
        this.status = status;
        return this;
    }

    public ParkingSpotInfo setParkingSpotId(long parkingSpotId) {
        this.parkingSpotId = parkingSpotId;
        return this;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "device_id", referencedColumnName = "device_id")
    private DeviceInfo deviceInfo;

    public ParkingSpotInfo() {
    }

}