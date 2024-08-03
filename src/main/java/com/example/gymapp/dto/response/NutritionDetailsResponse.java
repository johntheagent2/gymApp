package com.example.gymapp.dto.response;

import com.example.gymapp.enumeration.Meal;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class NutritionDetailsResponse {
    private Long id;
    private String name;
    private double calories;
    private double protein;
    private double fat;
    private double carbohydrates;
    private Meal meal;
    private LocalDate createdDate;
    private LocalTime createdTime;
}
