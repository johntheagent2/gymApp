package com.example.gymapp.entity;

import com.example.gymapp.enumeration.Meal;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "food_nutrition")
public class FoodNutrition {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_nutrition_generator")
    @SequenceGenerator(name = "food_nutrition_generator", sequenceName = "food_nutrition_generator", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "food", nullable = false)
    private String foodName;

    @Column(name = "calories", nullable = false)
    private double calories;

    @Column(name = "protein", nullable = false)
    private double protein;

    @Column(name = "fat", nullable = false)
    private double fat;

    @Column(name = "carbohydrates", nullable = false)
    private double carbohydrates;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "created_time", nullable = false)
    private LocalTime createdTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "meal", nullable = false)
    private Meal meal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
