package de.fc.projekt.webnode.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ParkingGroupInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long GroupId;
    private String GroupName;
    private String GroupDescription;

    public ParkingGroupInfo(String groupName, String groupDescription) {
        GroupName = groupName;
        GroupDescription = groupDescription;
    }
}
