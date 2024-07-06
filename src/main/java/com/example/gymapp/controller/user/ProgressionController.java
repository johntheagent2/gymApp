package com.example.gymapp.controller.user;

import com.example.gymapp.dto.request.AuthenticationRequest;
import com.example.gymapp.dto.request.ProgressionDto;
import com.example.gymapp.dto.response.UserInfoResponse;
import com.example.gymapp.service.ProgressionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${user-mapping}/progression")
@RequiredArgsConstructor
public class ProgressionController {

    private final ProgressionService progressionService;

    @GetMapping
    private ResponseEntity<List<ProgressionDto>> getAllProgress(){
        List<ProgressionDto> progressionDtoList = progressionService.getProgressionList();
        return ResponseEntity.accepted().body(progressionDtoList);
    }

    @PostMapping
    private ResponseEntity<Void> addProgression(@Valid @RequestBody ProgressionDto progressionDto){
        progressionService.addProgression(progressionDto);
        return ResponseEntity.accepted().build();
    }

    @PutMapping
    private ResponseEntity<Void> updateProgression(@Valid @RequestBody ProgressionDto progressionDto){
        progressionService.updateProgression(progressionDto);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping
    private ResponseEntity<Void> deleteProgression(@Valid @RequestBody ProgressionDto progressionDto){
        progressionService.removeProgression(progressionDto);
        return ResponseEntity.accepted().build();
    }
}
