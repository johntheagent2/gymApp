package com.example.gymapp.service;

import com.example.gymapp.dto.request.TrainingLessonActionRequest;
import com.example.gymapp.dto.request.TrainingProgramActionRequest;
import com.example.gymapp.dto.request.TrainingProgramCreationRequest;
import com.example.gymapp.dto.response.TrainingProgramResponse;
import com.example.gymapp.enumeration.ProgramType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TrainingProgramService {

    void createProgram(TrainingProgramCreationRequest request);

    void deleteProgram(TrainingProgramActionRequest request);

    void editProgram(TrainingProgramActionRequest request);

    Page<TrainingProgramResponse> getAllTrainingProgram(Pageable page);

    Page<TrainingProgramResponse> getAllTrainingByType(Pageable page, ProgramType type);

    void addTrainingLesson(TrainingLessonActionRequest request);

    void subscribeTrainingProgram(Long id);

    TrainingProgramResponse getTrainingProgram(Long id);

    List<TrainingProgramResponse> getTrainingProgramList();
}
