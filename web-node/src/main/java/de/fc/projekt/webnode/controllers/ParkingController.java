package de.fc.projekt.webnode.controllers;

import de.fc.projekt.webnode.models.ParkingSpotInfo;
import de.fc.projekt.webnode.models.ParkingSpotStatus;
import de.fc.projekt.webnode.repositories.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ParkingController {
    @Autowired
    private ParkingSpotRepository repository;

    @GetMapping("/spot")
    public @ResponseBody Iterable<ParkingSpotInfo> GetAllParkingInfos()
    {
        return repository.findAll();
    }
    @GetMapping("/spot/free")
    public @ResponseBody ParkingSpotInfo GetAFreeParkingSpot(){
        return repository.findByStatus(ParkingSpotStatus.FREE);
    }
    @GetMapping("/spot/{id}")
    public Optional<ParkingSpotInfo> GetParkingSpotInfo(@PathVariable Long id)
    {
        return repository.findById(id);
    }
}
