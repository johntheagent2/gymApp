package com.example.gymapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TrainingLessonResponse {
    Long id;
    String title;
    String description;
    String url;
}
