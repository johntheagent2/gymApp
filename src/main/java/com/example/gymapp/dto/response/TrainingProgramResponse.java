package com.example.gymapp.dto.response;

import com.example.gymapp.entity.TrainingLesson;
import com.example.gymapp.enumeration.ProgramType;
import com.example.gymapp.enumeration.TrackingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TrainingProgramResponse {
    Long id;
    String title;
    String description;
    ProgramType type;
    LocalDate startDate;
    LocalTime startTime;
    List<TrainingLessonResponse> trainingLessons;
}
