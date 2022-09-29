package com.example.scooterrentalv2.controllers;

import com.example.scooterrentalv2.Services.UserService;
import com.example.scooterrentalv2.models.User;
import com.example.scooterrentalv2.models.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/users/{email}")
    public User updateCustomer(@Valid @PathVariable String email, @RequestBody UserDto userDto){
        return userService.updateUser(email,userDto);
    }
    @PutMapping("/users/")
    public void updatePassword(@RequestParam("userName")String userName,@RequestParam("newPass")String newPass){
        userService.updatePassword(userName,newPass);
    }
    @PutMapping("/users/start")
    public String startRenting(@RequestParam("userName")String userName,@RequestParam("scooterId") Long scooterId){
        userService.startRenting(userName,scooterId);
        return "The rental has started";

    }
    //    @Secured({"ROLE_ADMIN","ROLE_USER"})
    //Stops the rental based on the username and id, in addition it changes the scooter position
    @PutMapping("/users/stop")
    public String stopRenting(@RequestParam("userName")String userName,@RequestParam("scooterId") Long scooterId,@RequestParam("newLocation")String newLocation){
        userService.stopRenting(userName,scooterId,newLocation);
        return "The rental has stopped";
    }

    //    @Secured("ROLE_ADMIN")
    //Deletes a user account, the history remains in the database
    @DeleteMapping("/users/{userId}")
    public void deleteCustomer(@PathVariable Long userId){
        userService.deleteById(userId);
    }
}

