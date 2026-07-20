package com.wbs.wbs.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(name = "bookings", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"worker_id", "date", "timeSlot"})
})
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @ManyToOne
    @JoinColumn(name = "service_category_id", nullable = false)
    private ServiceCategory serviceCategory;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime timeSlot;

    @Column(nullable = false)
    private Integer duration; // in hours

    @Column(nullable = false)
    private String status; // e.g. "PENDING", "ASSIGNED", "COMPLETED"
}
