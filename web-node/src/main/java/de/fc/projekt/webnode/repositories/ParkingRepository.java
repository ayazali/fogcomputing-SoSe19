package de.fc.projekt.webnode.repositories;

import de.fc.projekt.webnode.models.ParkingInfo;
import org.springframework.data.repository.CrudRepository;

public interface ParkingRepository extends CrudRepository<ParkingInfo, Long> {
}
