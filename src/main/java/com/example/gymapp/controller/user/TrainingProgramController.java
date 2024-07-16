package com.example.gymapp.controller.user;

import com.example.gymapp.dto.request.ProgressionRequest;
import com.example.gymapp.dto.request.TrainingLessonActionRequest;
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

    @GetMapping("/all")
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

    @PutMapping("/save/{id}")
    private ResponseEntity<Page<TrainingProgramResponse>> subscribeTrainingProgram(@PathVariable long id){
        trainingProgramService.subscribeTrainingProgram(id);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{id}")
    private ResponseEntity<TrainingProgramResponse> getTrainingProgram(@PathVariable long id){
        return ResponseEntity.accepted().body(trainingProgramService.getTrainingProgram(id));
    }

    @GetMapping()
    private ResponseEntity<List<TrainingProgramResponse>> getUserTrainingPrograms(){
        return ResponseEntity.accepted().body(trainingProgramService.getTrainingProgramList());
    }
}