package com.example.scooterrentalv2.Services;

import com.example.scooterrentalv2.Errors.*;
import com.example.scooterrentalv2.Repositories.HistoryRepo;
import com.example.scooterrentalv2.Repositories.ScooterRepo;
import com.example.scooterrentalv2.Repositories.UserRepository;
import com.example.scooterrentalv2.models.History;
import com.example.scooterrentalv2.models.LocationEnum;
import com.example.scooterrentalv2.models.User;
import com.example.scooterrentalv2.models.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.apache.commons.lang3.EnumUtils;

import java.sql.Timestamp;
import java.time.Clock;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepo;
    private final ModelMapper modelMapper;
    private final ScooterRepo scooterRepo;
    private final HistoryRepo historyRepo;
    private Long rentalId;
    private Boolean hasAlreadyStarted = false;
    
    @Autowired
    public UserServiceImpl(UserRepository userRepo, ModelMapper modelMapper,ScooterRepo scooterRepo,HistoryRepo historyRepo) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
        this.historyRepo = historyRepo;
        this.scooterRepo=scooterRepo;
    }

    @Override
    public User updateUser(String userEmail, UserDto userDto) {
    User userDb = userRepo.findByEmail(userEmail);
    User userRequest = convertToEntity(userDto);
        if (Objects.nonNull(userRequest.getEmail()) && !"".equalsIgnoreCase(userRequest.getEmail())) {
        userDb.setEmail(userRequest.getEmail());
    }
        if (Objects.nonNull(userRequest.getUsername()) && !"".equalsIgnoreCase(userRequest.getUsername())) {
        userDb.setUsername(userRequest.getUsername());
    }
        if (Objects.nonNull(userRequest.getPassword()) && !"".equalsIgnoreCase(userRequest.getPassword())) {
        userDb.setPassword(userRequest.getPassword());
    }
        if (Objects.nonNull(userRequest.getCardExpDate()) && !"".equalsIgnoreCase(userRequest.getCardExpDate())) {
            userDb.setCardExpDate(userRequest.getCardExpDate());
        }
        if (Objects.nonNull(userRequest.getCardCSV()) && !"".equalsIgnoreCase(userRequest.getCardCSV())) {
            userDb.setCardCSV(userRequest.getCardCSV());
        }
        if (Objects.nonNull(userRequest.getCardNumber()) && !"".equalsIgnoreCase(userRequest.getCardNumber())) {
            userDb.setCardNumber(userRequest.getCardNumber());
        }
        convertToDto(userDb);
        return userRepo.save(userDb);
}

    @Override
    public void updatePassword(String userName, String newPass) {
        
    }

    @Override
    public void startRenting(String userName, Long scooterId) {
        //This check is necessary only in postman, in a normal app the user can rent only a scooter

        if (!hasAlreadyStarted) {
            hasAlreadyStarted = true;
            checkUserName(userName);
            checkDisponibility(scooterId);
            Float scooterPrice;
            Timestamp startingTime = Timestamp.from(Clock.systemUTC().instant());
            try {
                scooterPrice = scooterRepo.findById(scooterId).get().getPrice();

            } catch (NoSuchElementException exception) {
                throw new ScooterNotFoundException("The scooter is not available");
            }

            History history = new History();
            try {
                userRepo.findByUserName(userName);

            } catch (NoSuchElementException exception) {
                throw new UserNotFoundException("The user does not exist. Try create an account first");
            }

            history.setUserName(userName);
            history.setScooterId(scooterId);
            history.setStartDate(startingTime);
            history.setPrice(scooterPrice);
            history.setStartLocation(scooterRepo.findById(scooterId).get().getPosition());
            this.rentalId = historyRepo.save(history).getRentalId();

            userRepo.startRenting(startingTime, scooterId);
            scooterRepo.updateStatus(scooterId, "Taken");
            hasAlreadyStarted = true;
        } else {
            throw new RentalAlreadyStartedException("The rental process has already started. You need to stop first.");
        }

    }
    public void checkUserName(String userName) {
        try {
            userRepo.findByUsername(userName);

        } catch (NullPointerException ex) {
            throw new UserNotFoundException("The customer does not exist");
        }
    }
    public String checkDisponibility(Long scooterId) {

        try {
            scooterRepo.findById(scooterId).get();

        } catch (NoSuchElementException ex) {
            throw new ScooterNotFoundException("The scooter does not exist");
        }
        if (scooterRepo.findById(scooterId).get().getStatus().equalsIgnoreCase("Available")) {
            return "The scooter is available";
        } else {
            throw new ScooterNotFoundException("The scooter is already taken");
        }
    }

    @Override
    public void stopRenting(String userName, Long scooterId, String newLocation) {History history = new History();
        Float totalPrice;
        String endLocation;
        Timestamp stopTime = Timestamp.from(Clock.systemUTC().instant());
        Timestamp startTime;
        if (rentalId != null) {
            startTime = historyRepo.findById(rentalId).get().getStartDate();
        } else {
            throw new StartNotFoundException("You need to start the rent first");
        }
        Float price = historyRepo.findById(rentalId).get().getPrice();

        history.setStopDate(stopTime);
        if (EnumUtils.isValidEnumIgnoreCase(LocationEnum.class, newLocation)) {
            endLocation = newLocation;
        } else {
            throw new LocationNotFoundException("The location is not permitted, look to leave the scooter in permitted areas");
        }
        Integer timeSpent = getDateDiff(startTime, stopTime);
        if (timeSpent < 1) {
            totalPrice = price * 2;
        } else {
            totalPrice = price * timeSpent;
        }
        System.out.println("alooooo aiciiii "+timeSpent);
        historyRepo.saveById(rentalId, timeSpent, stopTime, totalPrice, endLocation);
        scooterRepo.updateStatus(scooterId, "Available");
        scooterRepo.updatePosition(scooterId,newLocation);
        rentalId = null;
        hasAlreadyStarted = false;
    }

    @Override
    public void deleteById(Long userId) {

    }

    @Override
    public User findByUserName(String userName) {
        return userRepo.findByUserName(userName);
    }


    public User convertToEntity(UserDto userDto) {
        return (modelMapper.map(userDto, User.class));
    }
    public UserDto convertToDto(User user) {
        return (modelMapper.map(user, UserDto.class));
    }

    public List<UserDto> convertListToDto(List<User> users){
        return users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }
    public static int getDateDiff(Timestamp startDate, Timestamp stopDate) {
        long differenceInMinutes = stopDate.getTime() - startDate.getTime();
        return (int) TimeUnit.MILLISECONDS.toMinutes(differenceInMinutes);

    }
}
