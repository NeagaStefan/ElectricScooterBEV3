package com.example.scooterrentalv2.Repositories;

import java.sql.Timestamp;
import java.util.Optional;

import com.example.scooterrentalv2.models.User;
import com.example.scooterrentalv2.models.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    User findByEmail(String userEmail);

    @Transactional
    @Modifying
    @Query("update Scooter s set s.status='Taken', s.startDate=:startingTime where s.scooterId=:scooterId")
    void startRenting(@Param("startingTime") Timestamp startingTime, @Param("scooterId") Long scooterId);


    @Transactional
    @Modifying
    @Query("update User  c set c.password =:newPass where c.username =:userName")
    void updatePassword(@Param("userName") String userName,@Param("newPass") String newPass);


    @Query("select u from User u where u.username=?1")
    User findByUserName(String userName);
}
