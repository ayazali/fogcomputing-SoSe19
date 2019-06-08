package de.fc.projekt.webnode.controllers;

import de.fc.projekt.webnode.models.ParkingInfo;
import de.fc.projekt.webnode.repositories.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/parking")
public class ParkingController {

    @Autowired
    private ParkingRepository parkingRepository;

    @RequestMapping("/all")
    public @ResponseBody Iterable<ParkingInfo> GetAllParkingInfos()
    {
        return parkingRepository.findAll();
    }
}
