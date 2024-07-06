package com.example.gymapp.service;

import com.example.gymapp.dto.request.ProgressionDto;

import java.util.List;

public interface ProgressionService {
    void addProgression(ProgressionDto progressionDto);
    void removeProgression(ProgressionDto progressionDto);
    void updateProgression(ProgressionDto progressionDto);
    List<ProgressionDto> getProgressionList();
    ProgressionDto getLatestProgression(ProgressionDto progressionDto);
}
