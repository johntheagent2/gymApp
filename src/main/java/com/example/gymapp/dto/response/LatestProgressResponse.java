package com.example.gymapp.dto.response;

import com.example.gymapp.enumeration.TrackingType;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LatestProgressResponse {
    Long id;
    TrackingType trackingType;
    float value;
    LocalDate createdDate;
}
