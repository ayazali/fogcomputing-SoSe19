package de.fc.projekt.webnode.models;

import jdk.jfr.Name;

import javax.persistence.*;
import java.nio.MappedByteBuffer;

@Entity
@Table(name="deviceInfo")
public class DeviceInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private long deviceId;
    @Column(name="unique_id")
    private String uniqueId;
    private String deviceName;
    private String deviceDescription;
    private DeviceStatus status;
    @OneToOne(mappedBy = "deviceInfo")
    private ParkingSpotInfo parkingSpot;

    public String getUniqueId() {
        return uniqueId;
    }

    public DeviceInfo setDeviceId(long deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public ParkingSpotInfo getParkingSpot() {
        return parkingSpot;
    }

    public DeviceInfo setParkingSpot(ParkingSpotInfo parkingSpot) {
        this.parkingSpot = parkingSpot;
        return this;
    }

    public DeviceInfo setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
        return this;
    }

    public DeviceInfo setDeviceName(String deviceName) {
        this.deviceName = deviceName;
        return this;
    }

    public DeviceInfo setDeviceDescription(String deviceDescription) {
        deviceDescription = deviceDescription;
        return this;
    }

    public DeviceInfo setStatus(DeviceStatus status) {
        this.status = status;
        return this;
    }

    public DeviceInfo() {
    }

    public long getDeviceId() {
        return deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceDescription() {
        return deviceDescription;
    }

    public DeviceStatus getStatus() {
        return status;
    }


}
