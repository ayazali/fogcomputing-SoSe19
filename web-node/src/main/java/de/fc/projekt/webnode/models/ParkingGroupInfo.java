package de.fc.projekt.webnode.models;

import javax.persistence.*;
import java.util.*;

@Entity
public class ParkingGroupInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private long GroupId;
    private String GroupName;
    private String GroupDescription;

    public List<ParkingSpotInfo> getParkingSpots() {
        return parkingSpots;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private List<ParkingSpotInfo> parkingSpots = new ArrayList<ParkingSpotInfo>();

    public void setGroupId(long groupId) {
        GroupId = groupId;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public void setGroupDescription(String groupDescription) {
        GroupDescription = groupDescription;
    }

    public long getGroupId() {
        return GroupId;
    }

    public String getGroupName() {
        return GroupName;
    }

    public String getGroupDescription() {
        return GroupDescription;
    }

    public ParkingGroupInfo() {

    }

    public ParkingGroupInfo(String groupName, String groupDescription) {
        GroupName = groupName;
        GroupDescription = groupDescription;
    }
}
