package com.example.scooterrentalv2.Services;

import com.example.scooterrentalv2.models.HistoryDto;

import java.sql.Timestamp;
import java.util.List;

public interface HistoryService {
    List<HistoryDto> showCustomerHistory(String userName);

    List<HistoryDto> showAllRecords();

    List<HistoryDto> showRecordsBetweenDates(Timestamp startDate, Timestamp endDate);
}
