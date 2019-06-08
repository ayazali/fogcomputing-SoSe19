package de.fc.projekt.webnode.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DeviceInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long DeviceId;
    private String UUID;
    private String DeviceName;
    private String DeviceDescription;
    private DeviceStatus Status;

    public long getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(long deviceId) {
        DeviceId = deviceId;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getDeviceName() {
        return DeviceName;
    }

    public void setDeviceName(String deviceName) {
        DeviceName = deviceName;
    }

    public String getDeviceDescription() {
        return DeviceDescription;
    }

    public void setDeviceDescription(String deviceDescription) {
        DeviceDescription = deviceDescription;
    }

    public DeviceStatus getStatus() {
        return Status;
    }

    public void setStatus(DeviceStatus status) {
        Status = status;
    }

    public DeviceInfo(String UUID, String deviceName, String deviceDescription, DeviceStatus status) {
        this.UUID = UUID;
        DeviceName = deviceName;
        DeviceDescription = deviceDescription;
        Status = status;
    }
}
