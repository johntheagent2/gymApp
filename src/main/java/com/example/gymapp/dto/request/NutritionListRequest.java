package com.example.gymapp.dto.request;

import com.example.gymapp.enumeration.Meal;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class NutritionListRequest {
    private List<FoodNutritionRequest> nutritionList;
    private Meal meal;
    private LocalDate createdDate;
    private LocalTime createdTime;

    @Override
    public String toString() {
        StringBuilder queryBuilder = new StringBuilder();
        for (FoodNutritionRequest request : nutritionList) {
            queryBuilder.append(request.getFoodName())
                    .append(" ")
                    .append(request.getValue().intValue())
                    .append(request.getUnit())
                    .append(" ");
        }
        return queryBuilder.toString().trim();
    }
}
