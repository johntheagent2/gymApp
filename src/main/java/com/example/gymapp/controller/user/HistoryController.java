package com.example.gymapp.controller.user;

import com.example.gymapp.dto.request.HistoryRequest;
import com.example.gymapp.dto.request.HistoryResponse;
import com.example.gymapp.dto.request.ProgressionRequest;
import com.example.gymapp.dto.request.TimeStampRequest;
import com.example.gymapp.dto.response.HistoryResponseList;
import com.example.gymapp.service.HistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${user-mapping}/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("/{id}")
    private ResponseEntity<HistoryResponse> getHistoryWithId(@PathVariable Long id){
        return ResponseEntity.accepted().body(historyService.getHistory(id));
    }

    @GetMapping("/all")
    private ResponseEntity<List<HistoryResponse>> getAllHistory(){
        return ResponseEntity.accepted().body(historyService.getHistoryList());
    }

    @GetMapping("/this-week")
    private ResponseEntity<HistoryResponseList> getThisWeekHistory(){
        return ResponseEntity.accepted().body(historyService.getHistoryCurrentWeek());
    }

    @GetMapping("/today")
    private ResponseEntity<HistoryResponseList> getTodayWorkoutHistory(){
        return ResponseEntity.accepted().body(historyService.getTodayWorkoutHistory());
    }

    @PostMapping
    private ResponseEntity<Void> addHistory(@Valid @RequestBody HistoryRequest historyRequest){
        historyService.addHistory(historyRequest);
        return ResponseEntity.accepted().build();
    }

    @PutMapping
    private ResponseEntity<Void> updateHistory(@Valid @RequestBody HistoryRequest historyRequest){
        historyService.updateHistory(historyRequest);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping
    private ResponseEntity<Void> removeHistory(@Valid @RequestBody HistoryRequest historyRequest){
        historyService.removeHistory(historyRequest);
        return ResponseEntity.accepted().build();
    }
}
