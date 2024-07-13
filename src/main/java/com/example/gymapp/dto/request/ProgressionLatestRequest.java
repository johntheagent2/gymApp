package com.example.gymapp.dto.request;

import com.example.gymapp.enumeration.TrackingType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class ProgressionLatestRequest implements Serializable {
    TrackingType type;
}