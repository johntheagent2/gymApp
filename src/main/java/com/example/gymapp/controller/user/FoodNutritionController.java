package com.example.gymapp.controller.user;

import com.example.gymapp.dto.request.FoodNutritionRequest;
import com.example.gymapp.dto.request.NutritionListRequest;
import com.example.gymapp.dto.response.NutritionFoodResponse;
import com.example.gymapp.service.FoodNutritionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${user-mapping}/nutrition")
public class FoodNutritionController {

    private final FoodNutritionService foodNutritionService;

    @PostMapping
    public ResponseEntity<Void> addNutrition(@RequestBody NutritionListRequest request){
        foodNutritionService.addFoodNutrition(request);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNutrition(@PathVariable Long id){
        foodNutritionService.deleteNutrition(id);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<NutritionFoodResponse.MealNutrition>> getTodayNutrition(){
        return ResponseEntity.ok(foodNutritionService.getTodayMealNutrition().getMealNutritionList());
    }

    @GetMapping("/this-week")
    public ResponseEntity<NutritionFoodResponse> getThisWeekNutrition(){
        return ResponseEntity.ok(foodNutritionService.getThisWeekNutritionIntake());
    }
}
