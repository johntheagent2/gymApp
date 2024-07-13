package com.example.gymapp.service;

import com.example.gymapp.dto.request.TrainingLessonActionRequest;
import com.example.gymapp.dto.request.TrainingProgramActionRequest;
import com.example.gymapp.dto.request.TrainingProgramCreationRequest;

public interface TrainingProgramService {

    void createProgram(TrainingProgramCreationRequest request);

    void deleteProgram(TrainingProgramActionRequest request);

    void editProgram(TrainingProgramActionRequest request);

    void addTrainingLesson(TrainingLessonActionRequest request);
}
