package com.example.gymapp.repository;

import com.example.gymapp.entity.TrainingLesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingLessonRepository extends JpaRepository<TrainingLesson, Long> {

    List<TrainingLesson> findByTrainingProgram_Id(Long id);
}