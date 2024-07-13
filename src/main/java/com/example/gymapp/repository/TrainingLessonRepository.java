package com.example.gymapp.repository;

import com.example.gymapp.entity.TrainingLesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingLessonRepository extends JpaRepository<TrainingLesson, Long> {
}