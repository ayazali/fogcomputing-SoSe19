package de.fc.projekt.webnode.repositories;

import de.fc.projekt.webnode.models.DeviceInfo;
import org.springframework.data.repository.CrudRepository;

public interface DeviceRepository extends CrudRepository<DeviceInfo, Long> {
    DeviceInfo findByUniqueId(String UUID);
}
