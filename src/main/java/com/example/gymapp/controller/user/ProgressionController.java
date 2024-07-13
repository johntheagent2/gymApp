package com.example.gymapp.controller.user;

import com.example.gymapp.dto.request.ProgressionLatestRequest;
import com.example.gymapp.dto.request.ProgressionRequest;
import com.example.gymapp.dto.response.LatestProgressResponse;
import com.example.gymapp.enumeration.TrackingType;
import com.example.gymapp.service.ProgressionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("${user-mapping}/progression")
@RequiredArgsConstructor
public class ProgressionController {

    private final ProgressionService progressionService;

    @GetMapping("/latest")
    private ResponseEntity<LatestProgressResponse> getLatestProgressType(@RequestBody ProgressionLatestRequest type){
        return ResponseEntity.accepted().body(progressionService.getLatestProgression(type));
    }

    @GetMapping
    private ResponseEntity<List<ProgressionRequest>> getAllProgress(){
        List<ProgressionRequest> progressionRequestList = progressionService.getProgressionList();
        return ResponseEntity.accepted().body(progressionRequestList);
    }

    @PostMapping
    private ResponseEntity<Void> addProgression(@Valid @RequestBody ProgressionRequest progressionRequest){
        progressionService.addProgression(progressionRequest);
        return ResponseEntity.accepted().build();
    }

    @PutMapping
    private ResponseEntity<Void> updateProgression(@Valid @RequestBody ProgressionRequest progressionRequest){
        progressionService.updateProgression(progressionRequest);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping
    private ResponseEntity<Void> deleteProgression(@Valid @RequestBody ProgressionRequest progressionRequest){
        progressionService.removeProgression(progressionRequest);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/latest/list")
    private ResponseEntity<List<LatestProgressResponse>> getLatestProgress(){
        List<LatestProgressResponse> responseList = progressionService.getLatestProgressionList();
        return ResponseEntity.accepted().body(responseList);
    }

    @GetMapping("/type")
    private ResponseEntity<List<TrackingType>> getAllTrackingType(){
        List<TrackingType> responseList = Arrays.stream(TrackingType.values()).toList();
        return ResponseEntity.accepted().body(responseList);
    }

}
