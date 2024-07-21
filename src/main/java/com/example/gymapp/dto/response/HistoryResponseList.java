package com.example.gymapp.dto.response;

import com.example.gymapp.dto.request.HistoryRequest;
import com.example.gymapp.dto.request.HistoryResponse;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryResponseList {

    private double totalCalories;
    private List<HistoryResponse> historyResponseList;
}
