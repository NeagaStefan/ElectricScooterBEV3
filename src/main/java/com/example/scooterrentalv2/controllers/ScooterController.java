package com.example.scooterrentalv2.controllers;

import com.example.scooterrentalv2.Services.ScooterService;
import com.example.scooterrentalv2.models.History;
import com.example.scooterrentalv2.models.Scooter;
import com.example.scooterrentalv2.models.ScooterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ScooterController {
    private final ScooterService scooterService;

    @Autowired
    public ScooterController(ScooterService scooterService){
        this.scooterService  =scooterService;
    }

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
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
    @GetMapping("/scooters/admin/")
    public ResponseEntity<Map<String, Object>> showAllScootersAdmin(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size,
                                                                    @RequestParam(defaultValue = "scooterId,asc") String[] sort){
        try {
            List<Sort.Order> orders = new ArrayList<Sort.Order>();

            if (sort[0].contains(",")) {
                // will sort more than 2 fields
                // sortOrder="field, direction"
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                // sort=[field, direction]
                orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
            }

            List<Scooter> scooters = new ArrayList<Scooter>();
            Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

            Page<Scooter> pageTuts;

                pageTuts = scooterService.findAll(pagingSort);


            scooters = pageTuts.getContent();

            if (scooters.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("scooters", scooters);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
