package com.example.gymapp.service.implement;

import com.example.gymapp.common.Global;
import com.example.gymapp.dto.request.HistoryRequest;
import com.example.gymapp.dto.request.HistoryResponse;
import com.example.gymapp.dto.request.ProgressionRequest;
import com.example.gymapp.dto.request.TimeStampRequest;
import com.example.gymapp.dto.response.HistoryResponseList;
import com.example.gymapp.dto.response.LatestProgressResponse;
import com.example.gymapp.entity.History;
import com.example.gymapp.entity.Progression;
import com.example.gymapp.entity.User;
import com.example.gymapp.enumeration.TrackingType;
import com.example.gymapp.exception.exception.NotFoundException;
import com.example.gymapp.repository.HistoryRepository;
import com.example.gymapp.repository.ProgressionRepository;
import com.example.gymapp.service.HistoryService;
import com.example.gymapp.service.ProgressionService;
import com.example.gymapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;
    private final ProgressionService progressionService;
    private final UserService userService;

    @Override
    public HistoryResponse getHistory(Long id) {
        return historyRepository.findById(id)
                .map(this::convertToHistoryResponse)
                .orElseThrow(() -> new NotFoundException("history.entity.not-found"));
    }

    @Override
    public void addHistory(HistoryRequest historyRequest) {
        String username = Global.getCurrentLogin().getUsername();
        User currentUser = userService.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("authentication.user-name.not-found"));
        History history = History.builder()
                .exercise(historyRequest.getExercise())
                .calories(historyRequest.getCalories())
                .createdDate(Global.getLoggedDate(historyRequest.getCreatedDate()))
                .createdTime(Global.getLoggedTime(historyRequest.getCreatedTime()))
                .progression(Progression.builder()
                        .value(historyRequest.getCalories())
                        .trackingType(TrackingType.CALORIES)
                        .createdDate(historyRequest.getCreatedDate())
                        .createdTime(historyRequest.getCreatedTime())
                        .user(currentUser).build()
                        )
                .user(currentUser)
                .build();

        historyRepository.save(history);
    }

    @Override
    public void removeHistory(HistoryRequest historyRequest) {
        historyRepository.deleteById(historyRequest.getId());
    }

    @Override
    public void updateHistory(HistoryRequest historyRequest) {
        History history = historyRepository.findById(historyRequest.getId())
                .orElseThrow(() -> new NotFoundException("history.entity.not-found"));
        history.setExercise(historyRequest.getExercise());
        history.setCalories(historyRequest.getCalories());
        history.setCreatedDate(historyRequest.getCreatedDate());
        history.setCreatedTime(historyRequest.getCreatedTime());

        Progression progression = history.getProgression();
        progression.setValue(historyRequest.getCalories());
        progression.setCreatedDate(historyRequest.getCreatedDate());
        progression.setCreatedTime(historyRequest.getCreatedTime());

        history.setProgression(progression);
        historyRepository.save(history);
    }

    @Override
    public List<HistoryResponse> getHistoryList() {
        String username = Global.getCurrentLogin().getUsername();
        List<History> history = historyRepository.findByUser_Username(username);

        return history.stream()
                .map(this::convertToHistoryResponse)
                .toList();
    }

    public HistoryResponseList getHistoryCurrentWeek() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);

        List<History> histories = historyRepository.findHistoriesWithinCurrentWeek(startOfWeek, endOfWeek);
        List<HistoryResponse> historyResponseList = histories.stream()
                .map(this::convertToHistoryResponse)
                .toList();
        double totalCalories = historyResponseList.stream().mapToDouble(HistoryResponse::getCalories).sum();

        return HistoryResponseList.builder()
                .historyResponseList(historyResponseList)
                .totalCalories(totalCalories)
                .build();
    }

    @Override
    public HistoryResponseList getTodayWorkoutHistory() {
        LocalDate today = LocalDate.now();

        List<History> histories = historyRepository.findHistoryByCreatedDate(today);
        List<HistoryResponse> historyResponseList = histories.stream()
                .map(this::convertToHistoryResponse)
                .toList();
        double totalCalories = historyResponseList.stream().mapToDouble(HistoryResponse::getCalories).sum();

        return HistoryResponseList.builder()
                .historyResponseList(historyResponseList)
                .totalCalories(totalCalories)
                .build();
    }

    @Override
    public LatestProgressResponse getLatestHistory() {
        return null;
    }

    @Override
    public List<LatestProgressResponse> getLatestHistoryList() {
        return null;
    }

    public HistoryResponse convertToHistoryResponse(History history){
        return HistoryResponse.builder()
                .id(history.getId())
                .exercise(history.getExercise())
                .calories(history.getCalories())
                .createdDate(history.getCreatedDate())
                .createdTime(history.getCreatedTime())
                .build();
    }
}
