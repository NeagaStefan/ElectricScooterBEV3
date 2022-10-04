package com.example.scooterrentalv2.Services;

import com.example.scooterrentalv2.models.History;
import com.example.scooterrentalv2.models.HistoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.List;

public interface HistoryService {
    List<HistoryDto> showCustomerHistory(String userName);



    List<HistoryDto> showRecordsBetweenDates(Timestamp startDate, Timestamp endDate);

    Page<History> findAll(Pageable pagingSort);

    Page<History> showRecordsByUserName(String userName, Pageable pagingSort);
}
