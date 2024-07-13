package com.example.gymapp.service;

import com.example.gymapp.dto.request.ProgressionLatestRequest;
import com.example.gymapp.dto.request.ProgressionRequest;
import com.example.gymapp.dto.response.LatestProgressResponse;

import java.util.List;

public interface ProgressionService {
    void addProgression(ProgressionRequest progressionRequest);
    void removeProgression(ProgressionRequest progressionRequest);
    void updateProgression(ProgressionRequest progressionRequest);
    List<ProgressionRequest> getProgressionList();
    LatestProgressResponse getLatestProgression(ProgressionLatestRequest type);
    List<LatestProgressResponse> getLatestProgressionList();
}
