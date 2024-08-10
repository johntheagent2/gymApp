package com.example.gymapp.controller.admin;

import com.example.gymapp.dto.request.ProgressionRequest;
import com.example.gymapp.dto.request.TrainingLessonActionRequest;
import com.example.gymapp.dto.request.TrainingProgramActionRequest;
import com.example.gymapp.dto.request.TrainingProgramCreationRequest;
import com.example.gymapp.dto.response.TrainingProgramResponse;
import com.example.gymapp.entity.criteria.TrainingProgramCriteria;
import com.example.gymapp.enumeration.ProgramType;
import com.example.gymapp.service.TrainingProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("${admin-mapping}/training-program")
@RequiredArgsConstructor
public class TrainingProgramPrivateController {

    private final TrainingProgramService trainingProgramService;

    @PostMapping
    private ResponseEntity<Void> createProgram(
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

    @GetMapping("/type")
    private ResponseEntity<List<ProgramType>> getAllProgramTypes(){
        return ResponseEntity.accepted().body(Arrays.stream(ProgramType.values()).toList());
    }

    @GetMapping
    private ResponseEntity<Page<TrainingProgramResponse>> getTrainingPrograms(TrainingProgramCriteria request,
                                                                              @PageableDefault(size = 5) Pageable page){
        Page<TrainingProgramResponse> trainingProgram = trainingProgramService.getAllTrainingProgram(request, page);
        return ResponseEntity.accepted().body(trainingProgram);
    }

    @GetMapping("/offline")
    private ResponseEntity<Page<TrainingProgramResponse>> getOfflineTrainingPrograms(TrainingProgramCriteria request,
            @PageableDefault(size = 5) Pageable page){
        Page<TrainingProgramResponse> trainingProgram =
                trainingProgramService.getAllTrainingByType(request, page, ProgramType.OFFLINE);
        return ResponseEntity.accepted().body(trainingProgram);
    }

    @GetMapping("/online")
    private ResponseEntity<Page<TrainingProgramResponse>> getOnlineTrainingPrograms(TrainingProgramCriteria request,
            @PageableDefault(size = 5) Pageable page){
        Page<TrainingProgramResponse> trainingProgram =
                trainingProgramService.getAllTrainingByType(request, page, ProgramType.ONLINE);
        return ResponseEntity.accepted().body(trainingProgram);
    }

    @PostMapping(value = "/add-lesson", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Void> addTrainingLesson(TrainingLessonActionRequest request){
        trainingProgramService.addTrainingLesson(request);
        return ResponseEntity.accepted().build();
    }

    @PutMapping(value = "/edit-lesson", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Void> editTrainingLesson(TrainingLessonActionRequest request){
        trainingProgramService.editTrainingLesson(request);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping(value = "/delete-lesson/{id}")
    public ResponseEntity<Void> deleteTrainingLesson(@PathVariable Long id){
        trainingProgramService.removeTrainingLesson(id);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("delete-video")
    public ResponseEntity<Void> deleteVideo(Long id){
        trainingProgramService.deleteVideo(id);
        return ResponseEntity.accepted().build();
    }
}
