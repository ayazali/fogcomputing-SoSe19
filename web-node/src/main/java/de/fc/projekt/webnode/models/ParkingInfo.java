package de.fc.projekt.webnode.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ParkingInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long parkingId;
    private String parkingName;
    private String parkingDescription;

    public ParkingInfo(String parkingName, String parkingDescription) {
        this.parkingName = parkingName;
        this.parkingDescription = parkingDescription;
    }
}