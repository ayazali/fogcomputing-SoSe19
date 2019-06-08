package de.fc.projekt.webnode.repositories;

import de.fc.projekt.webnode.models.ParkingGroupInfo;
import org.springframework.data.repository.CrudRepository;

public interface ParkingGroupRepositroy extends CrudRepository<ParkingGroupInfo, Long> {
}
