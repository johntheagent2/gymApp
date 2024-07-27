package com.example.gymapp.entity;

import com.example.gymapp.enumeration.TrackingType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "progression")
public class Progression {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "progression_generator")
    @SequenceGenerator(name = "progression_generator", sequenceName = "progression_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "value", nullable = false)
    private float value;

    @Column(name = "tracking_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TrackingType trackingType;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "created_time")
    private LocalTime createdTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}