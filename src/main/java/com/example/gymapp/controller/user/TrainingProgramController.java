package com.example.gymapp.controller.user;

import com.example.gymapp.common.Global;
import com.example.gymapp.dto.request.ProgressionRequest;
import com.example.gymapp.dto.request.TrainingLessonActionRequest;
import com.example.gymapp.dto.request.TrainingProgramActionRequest;
import com.example.gymapp.dto.request.TrainingProgramCreationRequest;
import com.example.gymapp.dto.response.TrainingProgramResponse;
import com.example.gymapp.entity.TrainingProgram;
import com.example.gymapp.entity.TrainingProgram_;
import com.example.gymapp.entity.criteria.TrainingProgramCriteria;
import com.example.gymapp.enumeration.ProgramType;
import com.example.gymapp.service.TrainingProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.StringFilter;

import java.time.LocalDate;
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
    private ResponseEntity<Page<TrainingProgramResponse>> getTrainingPrograms(TrainingProgramCriteria request,
            @PageableDefault(size = 5) Pageable page){
        request.setStartDate(new LocalDateFilter().setGreaterThanOrEqual(LocalDate.now()));
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

    @PutMapping("/save/{id}")
    private ResponseEntity<Page<TrainingProgramResponse>> subscribeTrainingProgram(@PathVariable long id){
        trainingProgramService.subscribeTrainingProgram(id);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{id}")
    private ResponseEntity<TrainingProgramResponse> getTrainingProgram(@PathVariable long id){
        return ResponseEntity.accepted().body(trainingProgramService.getTrainingProgram(id));
    }

    @GetMapping("/user")
    private ResponseEntity<Page<TrainingProgramResponse>> getUserTrainingPrograms(TrainingProgramCriteria request
            , @PageableDefault(size = 5) Pageable page){
        return ResponseEntity.accepted().body(trainingProgramService.getTrainingProgramList(request, page));
    }

    @GetMapping("/user/online")
    private ResponseEntity<Page<TrainingProgramResponse>> getUserOnlineTrainingPrograms(TrainingProgramCriteria request
            ,@PageableDefault(size = 5) Pageable page){
        String username = Global.getCurrentLogin().getUsername();
        request.setUserName(
                (TrainingProgramCriteria.UsernameFilter) new TrainingProgramCriteria.UsernameFilter()
                        .setEquals(username));

        request.setStartDate(new LocalDateFilter().setGreaterThanOrEqual(LocalDate.now()));
        Page<TrainingProgramResponse> trainingProgram =
                trainingProgramService.getAllTrainingByType(request, page, ProgramType.ONLINE);
        return ResponseEntity.accepted().body(trainingProgram);
    }

    @GetMapping("/user/offline")
    private ResponseEntity<Page<TrainingProgramResponse>> getUserOfflineTrainingPrograms(TrainingProgramCriteria request
            ,@PageableDefault(size = 5) Pageable page){
        String username = Global.getCurrentLogin().getUsername();
        request.setUserName(
                (TrainingProgramCriteria.UsernameFilter) new TrainingProgramCriteria.UsernameFilter()
                        .setEquals(username));

        Page<TrainingProgramResponse> trainingProgram =
                trainingProgramService.getAllTrainingByType(request, page, ProgramType.OFFLINE);
        return ResponseEntity.accepted().body(trainingProgram);
    }

    @GetMapping("/user/today")
    private ResponseEntity<Page<TrainingProgramResponse>> getUserTodayProgram(TrainingProgramCriteria request
            ,@PageableDefault(size = 5) Pageable page){
        String username = Global.getCurrentLogin().getUsername();
        request.setUserName(
                (TrainingProgramCriteria.UsernameFilter) new TrainingProgramCriteria.UsernameFilter()
                        .setEquals(username));
        request.setCurrentDate(new LocalDateFilter().setEquals(LocalDate.now()));
        Page<TrainingProgramResponse> trainingProgram =
                trainingProgramService.getAllTrainingByType(request, page, ProgramType.OFFLINE);
        return ResponseEntity.accepted().body(trainingProgram);
    }
}
