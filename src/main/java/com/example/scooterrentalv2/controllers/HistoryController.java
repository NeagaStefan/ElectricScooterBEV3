package com.example.scooterrentalv2.controllers;

import com.example.scooterrentalv2.Services.HistoryService;
import com.example.scooterrentalv2.models.HistoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000" )
@RestController
public class HistoryController {
    private final HistoryService historyService;

    @Autowired
    public HistoryController(HistoryService historyService){
        this.historyService = historyService;
    }
//    @Secured("ROLE_ADMIN")
    @GetMapping("/history")
    public List<HistoryDto> showAllRecords() {
        return historyService.showAllRecords();
    }

//    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping("/history/")
    public List<HistoryDto> showRecordsBetweenDates(@RequestParam("startDate")Timestamp startDate,@RequestParam("endDate") Timestamp endDate){
        return historyService.showRecordsBetweenDates(startDate,endDate);

    }
}
