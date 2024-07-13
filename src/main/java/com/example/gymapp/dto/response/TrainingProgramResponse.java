package com.example.gymapp.dto.response;

import com.example.gymapp.enumeration.ProgramType;
import com.example.gymapp.enumeration.TrackingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

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
}
