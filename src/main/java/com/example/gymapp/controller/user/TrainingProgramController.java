package com.example.gymapp.controller.user;

import com.example.gymapp.dto.request.ProgressionRequest;
import com.example.gymapp.dto.request.TrainingProgramCreationRequest;
import com.example.gymapp.enumeration.ProgramType;
import com.example.gymapp.service.TrainingProgramService;
import lombok.RequiredArgsConstructor;
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


}
