package com.example.gymapp.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryResponse {
    private Long id;

    @NotNull(message = "history.exercise.empty")
    @NotEmpty(message = "history.exercise.empty")
    private String exercise;

    @NotNull(message = "progression.value.null")
    @Min(0)
    @Max(1000)
    private double calories;

    private LocalDate createdDate;

    private LocalTime createdTime;

}
