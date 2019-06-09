package de.fc.projekt.webnode.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class DeviceLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Long logId;
    private Long DeviceId;
    private Float Distance;
    private String  MessageUUID;
    private DeviceStatus Status;
    private Date CreatedOn;

    public DeviceLog() {
    }

    public Long getLogId() {
        return logId;
    }

    public DeviceLog setLogId(Long logId) {
        this.logId = logId;
        return this;
    }

    public Long getDeviceId() {
        return DeviceId;
    }

    public DeviceLog setDeviceId(Long deviceId) {
        DeviceId = deviceId;
        return this;
    }

    public Float getDistance() {
        return Distance;
    }

    public DeviceLog setDistance(Float distance) {
        Distance = distance;
        return this;
    }

    public String getMessageUUID() {
        return MessageUUID;
    }

    public DeviceLog setMessageUUID(String messageUUID) {
        MessageUUID = messageUUID;
        return this;
    }

    public DeviceStatus getStatus() {
        return Status;
    }

    public DeviceLog setStatus(DeviceStatus status) {
        Status = status;
        return this;
    }

    public Date getCreatedOn() {
        return CreatedOn;
    }

    public DeviceLog setCreatedOn(Date createdOn) {
        CreatedOn = createdOn;
        return this;
    }

}
