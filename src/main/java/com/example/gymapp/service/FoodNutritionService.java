package com.example.gymapp.service;

import com.example.gymapp.dto.request.FoodNutritionRequest;
import com.example.gymapp.dto.request.NutritionListRequest;
import com.example.gymapp.dto.response.NutritionDetailsResponse;
import com.example.gymapp.dto.response.NutritionFoodResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FoodNutritionService {

    void addFoodNutrition(NutritionListRequest requests);

    void deleteNutrition(Long id);

    Page<NutritionDetailsResponse> getAllLoggedList(Pageable page);

    NutritionFoodResponse getThisWeekNutritionIntake();

    NutritionFoodResponse getTodayMealNutrition();
}
