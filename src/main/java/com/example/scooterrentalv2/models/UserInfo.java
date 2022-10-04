package com.example.scooterrentalv2.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    public String userName;
    public Long scooterId;

    public String newLocation;
}
