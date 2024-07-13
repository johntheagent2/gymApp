package com.example.gymapp.service.implement;

import com.example.gymapp.dto.request.TrainingLessonActionRequest;
import com.example.gymapp.dto.request.TrainingProgramActionRequest;
import com.example.gymapp.dto.request.TrainingProgramCreationRequest;
import com.example.gymapp.entity.base.TrainingProgram;
import com.example.gymapp.enumeration.ProgramType;
import com.example.gymapp.repository.TrainingProgramRepository;
import com.example.gymapp.service.TrainingProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class TrainingProgramServiceImpl implements TrainingProgramService {

    private final TrainingProgramRepository trainingProgramRepository;

    @Override
    public void createProgram(TrainingProgramCreationRequest request) {
        Instant startTime = Instant.now();
        if(Objects.equals(request.getType(), ProgramType.OFFLINE)){
            startTime = Instant.parse(request.getStartTime());
        }
        trainingProgramRepository.save(TrainingProgram.builder()
                        .title(request.getTitle())
                        .description(request.getDescription())
                        .type(ProgramType.valueOf(request.getType()))
                        .startTime(startTime)
                        .build());
    }

    @Override
    public void deleteProgram(TrainingProgramActionRequest request) {

    }

    @Override
    public void editProgram(TrainingProgramActionRequest request) {

    }

    @Override
    public void addTrainingLesson(TrainingLessonActionRequest request) {

    }
}
