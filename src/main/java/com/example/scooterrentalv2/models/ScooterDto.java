package com.example.scooterrentalv2.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScooterDto {

    private Long scooterId;

    @NotBlank(message = "The model must be filled")
    private String scooterModel;

    @NotBlank(message = "The status must be filled")
    private String status;

    private String position;

    @Min(value = 10, message = "Put the scooter on the charger")
    @Max(value = 100, message = "The scooter cannot be filled after 100%")
    @NotNull(message = "The batteryPercentage must be filled")
    private Integer batteryPercentage;

    @Min(value = 1,message = "The price must be at least 1 euro")
    @Max(value = 5,message = "We are trying yo not rob people")
    private Float price;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Bucharest")
    private Timestamp startDate;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Bucharest")
    private Timestamp stopDate;

    private Float totalPrice;



}
