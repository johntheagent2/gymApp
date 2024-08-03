package com.example.gymapp.service;

import com.example.gymapp.dto.request.FoodNutritionRequest;
import com.example.gymapp.dto.request.NutritionListRequest;
import com.example.gymapp.dto.response.NutritionFoodResponse;

import java.util.List;

public interface FoodNutritionService {

    void addFoodNutrition(NutritionListRequest requests);

    void deleteNutrition(Long id);

    NutritionFoodResponse getThisWeekNutritionIntake();

    NutritionFoodResponse getTodayMealNutrition();
}
