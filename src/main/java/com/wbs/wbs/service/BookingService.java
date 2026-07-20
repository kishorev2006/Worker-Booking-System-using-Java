package com.wbs.wbs.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wbs.wbs.model.Booking;
import com.wbs.wbs.repository.BookingRepository;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public boolean isWorkerAvailable(Long workerId, LocalDate date, LocalTime timeSlot, Integer duration) {
        LocalTime endTime = timeSlot.plusHours(duration);
        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(workerId, date, timeSlot, endTime);
        return overlappingBookings.isEmpty();
    }

    public boolean isUserBookedForTimeSlot(Long userId, LocalDate date, LocalTime timeSlot) {
        List<Booking> bookings = bookingRepository.findByUserIdAndDateAndTimeSlot(userId, date, timeSlot);
        return !bookings.isEmpty();
    }

    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
