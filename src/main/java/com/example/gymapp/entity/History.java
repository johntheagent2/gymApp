package com.example.gymapp.entity;

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
@Table(name = "history")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "history_generator")
    @SequenceGenerator(name = "history_generator", sequenceName = "history_generator", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "exercise", nullable = false)
    private String exercise;

    @Column(name = "calories", nullable = false)
    private double calories;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "created_time", nullable = false)
    private LocalTime createdTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "progression_id", referencedColumnName = "id")
    private Progression progression;
}
