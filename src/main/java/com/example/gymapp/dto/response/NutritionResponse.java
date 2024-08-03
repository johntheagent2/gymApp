package com.example.gymapp.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NutritionResponse {
    private List<Item> items;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {
        private String name;
        private double calories;
        private double protein_g;
        private double fat_total_g;
        private double carbohydrates_total_g;
    }
}
