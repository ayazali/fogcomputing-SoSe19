package de.fc.projekt.webnode.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class ParkingLog {
    public Long getLogId() {
        return logId;
    }

    public long getParkingSpotId() {
        return ParkingSpotId;
    }

    public ParkingLog setParkingSpotId(long parkingSpotId) {
        ParkingSpotId = parkingSpotId;
        return this;
    }

    public ParkingSpotStatus getStatus() {
        return Status;
    }

    public ParkingLog setStatus(ParkingSpotStatus status) {
        Status = status;
        return this;
    }

    public String getLogDetail() {
        return LogDetail;
    }

    public ParkingLog setLogDetail(String logDetail) {
        LogDetail = logDetail;
        return this;
    }

    public Date getCreatedOn() {
        return CreatedOn;
    }

    public ParkingLog setCreatedOn(Date createdOn) {
        CreatedOn = createdOn;
        return this;
    }

    public ParkingLog setLogId(Long logId) {
        this.logId = logId;
        return this;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long logId;
    private long ParkingSpotId;
    private ParkingSpotStatus Status;
    private String LogDetail;
    private Date CreatedOn;

    public ParkingLog(){
    }
}
