package de.fc.projekt.webnode.repositories;

import de.fc.projekt.webnode.models.ParkingLog;
import org.springframework.data.repository.CrudRepository;

public interface ParkingLogRepository extends CrudRepository<ParkingLog, Long> {
}
