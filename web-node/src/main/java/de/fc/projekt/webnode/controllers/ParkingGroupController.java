package de.fc.projekt.webnode.controllers;

import de.fc.projekt.webnode.models.ParkingGroupInfo;
import de.fc.projekt.webnode.models.ParkingSpotInfo;
import de.fc.projekt.webnode.repositories.ParkingGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ParkingGroupController {
    @Autowired
    private ParkingGroupRepository repository;

    @GetMapping("/group/all")
    public @ResponseBody
    Iterable<ParkingGroupInfo> GetAll() {
        return repository.findAll();
    }

    @GetMapping("/group/{id}")
    public @ResponseBody
    Optional<ParkingGroupInfo> GetParkingGroup(@PathVariable Long id) {
        return repository.findById(id);
    }
}
