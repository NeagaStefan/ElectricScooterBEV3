package com.example.scooterrentalv2.controllers;

import com.example.scooterrentalv2.Services.HistoryService;
import com.example.scooterrentalv2.models.History;
import com.example.scooterrentalv2.models.HistoryDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class HistoryController {
    private final HistoryService historyService;

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }

    @Autowired
    public HistoryController(HistoryService historyService){
        this.historyService = historyService;
    }
//    @Secured("ROLE_ADMIN")
//    @GetMapping("/history")
//    public  ResponseEntity<Map<String, Object>> showAllRecords() {
//        return historyService.showAllRecords();
//    }

//    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping("/history/")
    public List<HistoryDto> showRecordsBetweenDates(@RequestParam("startDate") Timestamp startDate, @RequestParam("endDate") Timestamp endDate){
        return historyService.showRecordsBetweenDates(startDate,endDate);

    }
    @GetMapping("/history/user/{userName}")
    public ResponseEntity<Map<String, Object>> showRecordsByUserName(@PathVariable String userName, @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size,
                                                                     @RequestParam(defaultValue = "rentalId,desc") String[] sort) {
        try {
            List<Order> orders = new ArrayList<Order>();

            if (sort[0].contains(",")) {
                // will sort more than 2 fields
                // sortOrder="field, direction"
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                // sort=[field, direction]
                orders.add(new Order(getSortDirection(sort[1]), sort[0]));
            }

            List<History> histories= new ArrayList<History>();
            Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

            Page<History> pageTuts;
            if (userName == null)
                pageTuts = historyService.findAll(pagingSort);
            else
                pageTuts = historyService.showRecordsByUserName(userName, pagingSort);

            histories= pageTuts.getContent();

            if (histories.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("histories", histories);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    }

