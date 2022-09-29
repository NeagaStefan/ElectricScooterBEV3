package com.example.scooterrentalv2.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Scooter {
    @Id
    @SequenceGenerator(
            name="scooter_sequence",
            sequenceName ="scooter_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "scooter_sequence"
    )
    private Long scooterId;
    private String scooterModel;
    private String status;
    private String position;
    private Integer batteryPercentage;
    private Float price;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="Europe/Bucharest")
    private Timestamp startDate;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="Europe/Bucharest")
    private Timestamp stopDate;
    private Float totalPrice;
}
