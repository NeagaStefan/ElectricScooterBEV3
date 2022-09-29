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
public class History {
    @Id
    @SequenceGenerator(
            name="rental_sequence",
            sequenceName ="rental_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rental_sequence"
    )
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
    private String startLocation;
    private String endLocation;
}
