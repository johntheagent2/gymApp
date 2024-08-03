package com.example.gymapp.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FoodNutritionRequest {
    private String foodName;
    private Double value;
    private String unit;
}
