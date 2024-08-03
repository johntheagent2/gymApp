package com.example.gymapp.repository;

import com.example.gymapp.entity.FoodNutrition;
import com.example.gymapp.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FoodNutritionRepository extends JpaRepository<FoodNutrition, Long> {

    @Query("SELECT h FROM FoodNutrition h " +
            "WHERE h.user.username = :username " +
            "AND h.createdDate " +
            "BETWEEN :startOfWeek AND :endOfWeek")
    List<FoodNutrition> findByCurrentWeek(
            @Param("username") String username,
            @Param("startOfWeek") LocalDate startOfWeek,
            @Param("endOfWeek") LocalDate endOfWeek);

    @Query("SELECT h FROM FoodNutrition h " +
            "WHERE h.user.username = :username " +
            "AND h.createdDate = :createdDate")
    List<FoodNutrition> findTodayMeals(
            @Param("username") String username,
            @Param("createdDate") LocalDate createdDate);

    Page<FoodNutrition> findAllByUser_Username(String username, Pageable pageable);
}
