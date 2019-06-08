package de.fc.projekt.webnode.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class ParkingLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private double logId;
    private long ParkingSpotId;
    private String Status;
    private String LogDetail;
    private Date CreatedOn;
}
