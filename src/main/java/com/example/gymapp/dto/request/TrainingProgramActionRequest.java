package com.example.gymapp.dto.request;

import com.example.gymapp.enumeration.ProgramType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingProgramActionRequest {
    @NotNull
    @NotEmpty
    private Long id;

    private String title;
    private String description;
    private ProgramType type;
    private String startDate;
    private String startTime;
}
