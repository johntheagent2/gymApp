package com.example.gymapp.dto.request;

import com.example.gymapp.enumeration.TrackingType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for {@link com.example.gymapp.entity.Progression}
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgressionRequest implements Serializable {

    Long id;

    @NotNull(message = "progression.type.null")
    TrackingType trackingType;

    @NotNull(message = "progression.value.null")
    @Min(0)
    @Max(1000)
    float value;

    LocalDate createdDate;

    LocalTime createdTime;;

}