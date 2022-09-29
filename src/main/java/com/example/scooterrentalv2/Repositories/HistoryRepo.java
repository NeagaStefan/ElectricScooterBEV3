package com.example.scooterrentalv2.Repositories;

import com.example.scooterrentalv2.models.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface HistoryRepo extends JpaRepository<History,Long> {

    @Transactional
    @Modifying
    @Query(value = "update History r set r.timeSpent=:timeSpent, r.stopDate=:stopDate,r.totalPrice=:totalPrice, r.endLocation=:endLocation where r.rentalId = :rentalId ")
    void saveById(@Param("rentalId") Long rentalId, @Param("timeSpent") Integer timeSpent, @Param("stopDate")Timestamp stopDate, @Param("totalPrice") Float totalPrice,@Param("endLocation")String endLocation);


    @Query(value ="select h from History h where h.userName=:userName" )
    List<History> findAllByCustomerUserName(@Param("userName") String userName);

    @Query(value = "select h from History h")
    List<History> showAllRecords();

    @Query("select  h from History h where h.startDate between :startDate and :endDate")
    List<History> showRecordsBetweenDates(@Param("startDate") Timestamp startDate,@Param("endDate") Timestamp endDate);
}
