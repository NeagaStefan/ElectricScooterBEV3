package com.example.scooterrentalv2.Repositories;

import com.example.scooterrentalv2.models.Scooter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ScooterRepo extends JpaRepository<Scooter,Long> {
    @Query("select s from Scooter s where s.status='Available' or s.status='available' order by s.scooterId")
    List<Scooter> showAllScooters();

   @Transactional
   @Modifying
   @Query("update Scooter s set s.status='Unavailable' where s.scooterId = :scooterId")
    void deleteScooter(@Param("scooterId") Long scooterId);

   @Query("select s from Scooter s where s.status=:status")
    List<Scooter> showScootersByStatus(@Param ("status") String status);



    @Query("select s from Scooter s where s.batteryPercentage <:battery")
    List<Scooter> showScootersByBattery(@Param("battery") Integer battery);

    @Transactional
    @Modifying
    @Query("update Scooter  s set s.status=:status where s.scooterId=:scooterId")
    void updateStatus(@Param("scooterId") Long scooterId,@Param("status") String status);

    @Transactional
    @Modifying
    @Query("update Scooter  s set s.status=:status,s.position=:newLocation where s.scooterId=:scooterId")
    void updateStatusAndPosition(@Param("scooterId") Long scooterId,@Param("status") String status,@Param("newLocation") String newLocation);


    @Transactional
    @Modifying
    @Query("update Scooter  s set s.position =?2 where s.scooterId=?1")
    void updatePosition(Long scooterId, String newLocation);

    @Query("select s from Scooter s order by s.scooterId asc ")
    List<Scooter> showAllScootersAdmin();
    @Query("select s from Scooter s where s.status='Available' or s.status='available' order by s.scooterId")
    List<Scooter> showAllAvailableScooters();

    List<Scooter> findByPositionContainingIgnoreCase(String position);
}
