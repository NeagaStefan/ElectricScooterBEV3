package com.example.scooterrentalv2.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDto {
    private Long rentalId;
    private Long scooterId;
    private String userName;
    private Integer timeSpent;
    private Float price;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Bucharest")
    private Timestamp startDate;
    private Float totalPrice;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Bucharest")
    private Timestamp stopDate;
}
