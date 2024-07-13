package com.example.gymapp.controller.user;

import com.example.gymapp.dto.request.ProgressionRequest;
import com.example.gymapp.dto.request.TrainingProgramActionRequest;
import com.example.gymapp.dto.request.TrainingProgramCreationRequest;
import com.example.gymapp.dto.response.TrainingProgramResponse;
import com.example.gymapp.enumeration.ProgramType;
import com.example.gymapp.service.TrainingProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("${user-mapping}/training-program")
@RequiredArgsConstructor
public class TrainingProgramController {

    private final TrainingProgramService trainingProgramService;

    @GetMapping("/type")
    private ResponseEntity<List<ProgramType>> getAllProgramTypes(){
        return ResponseEntity.accepted().body(Arrays.stream(ProgramType.values()).toList());
    }

    @PostMapping
    private ResponseEntity<List<ProgressionRequest>> createProgram(
            @Validated @RequestBody TrainingProgramCreationRequest request){
        trainingProgramService.createProgram(request);
        return ResponseEntity.accepted().build();
    }

    @PutMapping
    private ResponseEntity<Void> editProgram(@Validated @RequestBody TrainingProgramActionRequest request){
        trainingProgramService.editProgram(request);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping
    private ResponseEntity<Void> deleteProgram(@Validated @RequestBody TrainingProgramActionRequest request){
        trainingProgramService.deleteProgram(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    private ResponseEntity<Page<TrainingProgramResponse>> getTrainingPrograms(
            @PageableDefault(size = 5) Pageable page){
        Page<TrainingProgramResponse> trainingProgram = trainingProgramService.getAllTrainingProgram(page);
        return ResponseEntity.accepted().body(trainingProgram);
    }

    @GetMapping("/offline")
    private ResponseEntity<Page<TrainingProgramResponse>> getOfflineTrainingPrograms(
            @PageableDefault(size = 5) Pageable page){
        Page<TrainingProgramResponse> trainingProgram =
                trainingProgramService.getAllTrainingByType(page, ProgramType.OFFLINE);
        return ResponseEntity.accepted().body(trainingProgram);
    }

    @GetMapping("/online")
    private ResponseEntity<Page<TrainingProgramResponse>> getOnlineTrainingPrograms(
            @PageableDefault(size = 5) Pageable page){
        Page<TrainingProgramResponse> trainingProgram =
                trainingProgramService.getAllTrainingByType(page, ProgramType.ONLINE);
        return ResponseEntity.accepted().body(trainingProgram);
    }
}
