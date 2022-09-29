package com.example.scooterrentalv2.Services;

import com.example.scooterrentalv2.Repositories.HistoryRepo;
import com.example.scooterrentalv2.models.History;
import com.example.scooterrentalv2.models.HistoryDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryServiceImpl implements  HistoryService {
    private final HistoryRepo historyRepo;
    private final ModelMapper modelMapper;

    @Autowired
    private HistoryServiceImpl(HistoryRepo historyRepo, ModelMapper modelMapper){
        this.historyRepo = historyRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<HistoryDto> showCustomerHistory(String userName) {
        return historyRepo.findAllByCustomerUserName(userName).stream().map(history -> modelMapper.map(history,HistoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<HistoryDto> showAllRecords() {
        return convertListToDto(historyRepo.showAllRecords());

    }

    @Override
    public List<HistoryDto> showRecordsBetweenDates(Timestamp startDate, Timestamp endDate) {
        return convertListToDto(historyRepo.showRecordsBetweenDates(startDate,endDate));
    }

    private List<HistoryDto> convertListToDto(List<History> histories){
        return histories.stream().map(history -> modelMapper.map(history, HistoryDto.class)).collect(Collectors.toList());
    }
}
