package com.example.gymapp.dto.response;

import com.example.gymapp.enumeration.Meal;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NutritionFoodResponse {
    private String name;
    private double calories;
    private double protein;
    private double fat;
    private double carbohydrates;
    private List<MealNutrition> mealNutritionList;

    @Getter
    @Setter
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MealNutrition {
        Meal meal;
        private double calories;
    }
}
