package de.fc.projekt.webnode.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class DeviceLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private double logId;
    private long DeviceId;
    private DeviceStatus Status;
    private Date CreatedOn;
}
