package com.wbs.wbs.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wbs.wbs.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByWorkerIdAndDateAndTimeSlot(Long workerId, LocalDate date, LocalTime timeSlot);
    List<Booking> findByUserId(Long userId);

    List<Booking> findByUserIdAndDateAndTimeSlot(Long userId, LocalDate date, LocalTime timeSlot);

    @Query(value = "SELECT * FROM bookings b WHERE b.worker_id = :workerId AND b.date = :date AND " +
                   "((b.time_slot < :endTime) AND (ADDTIME(b.time_slot, SEC_TO_TIME(b.duration * 3600)) > :startTime))",
           nativeQuery = true)
    List<Booking> findOverlappingBookings(@Param("workerId") Long workerId,
                                          @Param("date") LocalDate date,
                                          @Param("startTime") java.time.LocalTime startTime,
                                          @Param("endTime") java.time.LocalTime endTime);
}
