package com.example.scooterrentalv2.Services;

import com.example.scooterrentalv2.models.User;
import com.example.scooterrentalv2.models.UserDto;

public interface UserService {
    User updateUser(String userEmail, UserDto userDto);


    void updatePassword(String userName, String newPass);

    void startRenting(String userName, Long scooterId);

    void stopRenting(String userName, Long scooterId, String newLocation);

    void deleteById(Long userId);
}
