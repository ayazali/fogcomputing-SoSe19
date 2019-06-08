package de.fc.projekt.webnode.controllers;

import de.fc.projekt.webnode.models.DeviceInfo;
import de.fc.projekt.webnode.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/devices")
public class DeviceController {
    @Autowired
    private DeviceRepository deviceRepository;

    @GetMapping("/all")
    public @ResponseBody
    Iterable<DeviceInfo> GetAllDevices() {
        return deviceRepository.findAll();
    }

    @RequestMapping(path = "", method = RequestMethod.PUT)
    public void UpdateDevice(@RequestBody DeviceInfo deviceInfo) {
        deviceRepository.findById(deviceInfo.getDeviceId()).orElseThrow(() -> new IllegalArgumentException("Invalid device"));
    }

    @GetMapping("/{id}")
    public @ResponseBody
    Optional<DeviceInfo> GetDevice(long id) {
        return deviceRepository.findById(id);
    }

}
