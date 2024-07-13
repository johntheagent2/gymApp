package com.example.gymapp.dto.request;

import com.example.gymapp.enumeration.ProgramType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingProgramCreationRequest {

    @NotNull(message = "training-program.title.null")
    @NotEmpty(message = "training-program.title.null")
    private String title;

    @NotNull(message = "training-program.description.null")
    @NotEmpty(message = "training-program.description.null")
    private String description;

    @NotNull(message = "training-program.type.null")
    @NotEmpty(message = "training-program.type.null")
    private String type;

    private String startTime;
}
