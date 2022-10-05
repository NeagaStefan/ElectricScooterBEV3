package com.example.scooterrentalv2.Services;

import com.example.scooterrentalv2.models.Scooter;
import com.example.scooterrentalv2.models.ScooterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ScooterService {
//    List<ScooterDto> showAllScooters();

    Scooter saveScooter(ScooterDto scooterDto);

    Scooter updateScooter(Long scooterId,ScooterDto scooterDto);

    void deleteScooter(Long scooterId);

    List<ScooterDto> showScootersByStatus(String status);

    List<ScooterDto> showScootersByPosition(String position);

    List<ScooterDto> showScootersByBattery(Integer battery);

    void updateStatusAndPosition(Long scooterId, String status, String location);

    void updateStatus(Long scooterId, String status);

    List<ScooterDto> showAllScootersAdmin();

    List<ScooterDto> showAllAvailableScooters();

    Optional<Scooter> findScooterById(Long id);

    Page<Scooter> findAll(Pageable pagingSort);
}
