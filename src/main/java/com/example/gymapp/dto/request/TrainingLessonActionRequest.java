package com.example.gymapp.dto.request;

import com.example.gymapp.enumeration.ProgramType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingLessonActionRequest {
    private Long id;

    private Long programId;

    private String name;

    private String description;

    private String url;

    private MultipartFile file;
}
