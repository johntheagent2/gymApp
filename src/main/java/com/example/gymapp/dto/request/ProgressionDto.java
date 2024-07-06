package com.example.gymapp.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.example.gymapp.entity.Progression}
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgressionDto implements Serializable {

    Long id;

    @NotNull(message = "progression.weight.null")
    @Min(0)
    @Max(1000)
    float weight;

    LocalDate createdDate;
}