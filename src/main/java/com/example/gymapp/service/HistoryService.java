package com.example.gymapp.service;

import com.example.gymapp.dto.request.HistoryRequest;
import com.example.gymapp.dto.request.HistoryResponse;
import com.example.gymapp.dto.request.TimeStampRequest;
import com.example.gymapp.dto.response.HistoryResponseList;
import com.example.gymapp.dto.response.LatestProgressResponse;

import java.util.List;

public interface HistoryService {

    HistoryResponse getHistory(Long id);
    void addHistory(HistoryRequest historyRequest);
    void removeHistory(HistoryRequest historyRequest);
    void updateHistory(HistoryRequest historyRequest);
    List<HistoryResponse> getHistoryList();
    HistoryResponseList getHistoryCurrentWeek(String request);
    LatestProgressResponse getLatestHistory();
    List<LatestProgressResponse> getLatestHistoryList();
}
