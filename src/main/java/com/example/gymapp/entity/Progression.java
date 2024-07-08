package com.example.gymapp.entity;

import com.example.gymapp.enumeration.TrackingType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    private TrackingType trackingType;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}