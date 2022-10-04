package com.example.scooterrentalv2.controllers;

import com.example.scooterrentalv2.Services.ScooterService;
import com.example.scooterrentalv2.models.Scooter;
import com.example.scooterrentalv2.models.ScooterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ScooterController {
    private final ScooterService scooterService;

    @Autowired
    public ScooterController(ScooterService scooterService){
        this.scooterService  =scooterService;
    }
    //Shows all scooters available for customers

    @PreAuthorize("hasRole('ROLE_USER')  or hasRole('ROLE_ADMIN')")
    @GetMapping("/scooters")
    public List<ScooterDto> showAllAvailableScooters(){
        return scooterService.showAllAvailableScooters();
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/scooters/{id}")
    public List<Optional<Scooter>> showScooterById (@PathVariable Long id) {
        return
                 Arrays.asList(scooterService.findScooterById(id));
    }
    //Show all scooters in the company
    @Secured("ROLE_ADMIN")
    @GetMapping("/scooters/admin")
    public List<ScooterDto> showAllScootersAdmin(){
        return scooterService.showAllScootersAdmin();
    }

    //Shows scooters by status for checking
    @Secured("ROLE_ADMIN")
    @GetMapping("/scooters/status/{status}")
    public List<ScooterDto> showScootersByStatus(@PathVariable String status){
        return scooterService.showScootersByStatus(status);
    }

    //Shows scooters by position to know where are the closest ones
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping("/scooters/position/{position}")
    public List<ScooterDto> showScootersByPosition(@PathVariable String position){
        return scooterService.showScootersByPosition(position);
    }

    //Show scooters under some percent of battery, good to know witch ones to take to charge
    @Secured("ROLE_ADMIN")
    @GetMapping("/scooters/battery/{battery}")
    public List<ScooterDto> showScootersByBattery(@PathVariable Integer battery){
        return scooterService.showScootersByBattery(battery);
    }


    //Introducing a new Scooter in the database
    @Secured("ROLE_ADMIN")
    @PostMapping("/scooters")
    public Scooter saveScooter(@Valid @RequestBody ScooterDto scooterDto){
        return scooterService.saveScooter(scooterDto);
    }

    //Updating existing scooter
    @Secured("ROLE_ADMIN")
    @PostMapping("/scooters/{id}")
    public Scooter updateScooter(@Valid @PathVariable  Long id,@RequestBody ScooterDto scooterDto){
        return scooterService.updateScooter(id,scooterDto);
    }

    //Changing the status of the scooter
    @Secured("ROLE_ADMIN")

    @PutMapping("/scooters/")
    public void updateStatus(@RequestParam ("scooterId") Long scooterId,@RequestParam("status") String status,@RequestParam("location") String location){
        scooterService.updateStatusAndPosition(scooterId,status, location);
    }
    @Secured("ROLE_ADMIN")
    //Soft deletes a scooter
    @DeleteMapping("/scooters/{scooterId}")
    public void deleteScooter(@PathVariable Long scooterId){
        scooterService.deleteScooter(scooterId);
    }


}
