package com.example.gymapp.service.implement;

import com.example.gymapp.common.Global;
import com.example.gymapp.dto.request.NutritionListRequest;
import com.example.gymapp.dto.response.LatestProgressResponse;
import com.example.gymapp.dto.response.NutritionFoodResponse;
import com.example.gymapp.dto.response.NutritionResponse;
import com.example.gymapp.entity.FoodNutrition;
import com.example.gymapp.entity.User;
import com.example.gymapp.enumeration.Meal;
import com.example.gymapp.enumeration.TrackingType;
import com.example.gymapp.repository.FoodNutritionRepository;
import com.example.gymapp.service.FoodNutritionService;
import com.example.gymapp.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FoodNutritionServiceImpl implements FoodNutritionService {

    @Value("${nutrition-api-key}")
    private String NUTRITION_KEY;
    private final FoodNutritionRepository foodNutritionRepository;
    private final UserService userService;

    @Override
    public void addFoodNutrition(NutritionListRequest requests) {

        String jsonResponse = fetchNutritionData(requests.toString());

        ObjectMapper mapper = new ObjectMapper();
        NutritionResponse response;

        try {
            response = mapper.readValue(jsonResponse, NutritionResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse nutrition data", e);
        }

        User user = userService.findByUsername(Global.getCurrentLogin().getUsername())
                .orElseThrow();

        if(requests.getCreatedTime().isBefore(LocalTime.parse("11:00:00"))){
            requests.setMeal(Meal.BREAKFAST);
        }else if(requests.getCreatedTime().isBefore(LocalTime.parse("13:00:00"))){
            requests.setMeal(Meal.LUNCH);
        }else if(requests.getCreatedTime().isBefore(LocalTime.parse("18:00:00"))){
            requests.setMeal(Meal.AFTERNOON);
        }else{
            requests.setMeal(Meal.DINNER);
        }

        List<FoodNutrition> foodNutritions = response.getItems().stream()
                .map(item ->
                    FoodNutrition.builder()
                            .foodName(item.getName())
                            .calories(item.getCalories())
                            .protein(item.getProtein_g()).
                            fat(item.getFat_total_g())
                            .carbohydrates(item.getCarbohydrates_total_g())
                            .meal(requests.getMeal())
                            .createdDate(requests.getCreatedDate())
                            .createdTime(requests.getCreatedTime())
                            .user(user).build())
                .toList();

        foodNutritionRepository.saveAll(foodNutritions);
    }

    @Override
    public void deleteNutrition(Long id) {
        foodNutritionRepository.deleteById(id);
    }

    @Override
    public NutritionFoodResponse getThisWeekNutritionIntake() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);
        String username = Global.getCurrentLogin().getUsername();

        List<FoodNutrition> foodNutritionList = foodNutritionRepository
                .findByCurrentWeek(username, startOfWeek, endOfWeek);

        return toThisWeekNutritionFoodResponse(foodNutritionList);
    }

    @Override
    public NutritionFoodResponse getTodayMealNutrition() {
        String username = Global.getCurrentLogin().getUsername();
        List<FoodNutrition> foodNutritionList = foodNutritionRepository.findTodayMeals(username, LocalDate.now());

        if (foodNutritionList.isEmpty()) {
            return null;
        }

        Map<Meal, Double> mealMap = new EnumMap<>(Meal.class);

        foodNutritionList.forEach(foodNutrition -> {
            mealMap.merge(foodNutrition.getMeal(), round(foodNutrition.getCalories(), 0), Double::sum);
        });

        List<NutritionFoodResponse.MealNutrition> mealNutritionList = mealMap.entrySet().stream()
                .map(entry -> new NutritionFoodResponse.MealNutrition(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return NutritionFoodResponse.builder()
                .mealNutritionList(mealNutritionList)
                .build();
    }



    private NutritionFoodResponse toThisWeekNutritionFoodResponse(List<FoodNutrition> foodNutritionList) {
        double totalCalories = 0;
        double totalProtein = 0;
        double totalFat = 0;
        double totalCarbohydrates = 0;

        for (FoodNutrition foodNutrition : foodNutritionList) {
            totalCalories += foodNutrition.getCalories();
            totalProtein += foodNutrition.getProtein();
            totalFat += foodNutrition.getFat();
            totalCarbohydrates += foodNutrition.getCarbohydrates();
        }

        return NutritionFoodResponse.builder()
                .calories(round(totalCalories, 2))
                .protein(round(totalProtein, 2))
                .fat(round(totalFat, 2))
                .carbohydrates(round(totalCarbohydrates, 2))
                .build();
    }

    private String fetchNutritionData(String query) {
        String url = "https://api.calorieninjas.com/v1/nutrition?query=" + query;
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", NUTRITION_KEY);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }

    private double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}
