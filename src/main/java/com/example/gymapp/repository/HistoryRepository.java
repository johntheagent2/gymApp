package com.example.gymapp.repository;

import com.example.gymapp.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {

    List<History> findByUser_Username(String username);

    @Query("SELECT h FROM History h " +
            "WHERE h.createdDate " +
            "BETWEEN :startOfWeek AND :endOfWeek")
    List<History> findHistoriesWithinCurrentWeek(
            @Param("startOfWeek") LocalDate startOfWeek,
            @Param("endOfWeek") LocalDate endOfWeek);
}
