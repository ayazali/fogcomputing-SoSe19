package de.fc.projekt.webnode.repositories;

import de.fc.projekt.webnode.models.ParkingSpotInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParkingSpotRepository extends CrudRepository<ParkingSpotInfo, Long> {
    ParkingSpotInfo findByDeviceInfoDeviceId(Long deviceId);
}
