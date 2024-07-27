package com.example.gymapp.service.implement;

import com.example.gymapp.common.Global;
import com.example.gymapp.dto.request.ProgressionLatestRequest;
import com.example.gymapp.dto.request.ProgressionRequest;
import com.example.gymapp.dto.response.LatestProgressResponse;
import com.example.gymapp.entity.Progression;
import com.example.gymapp.entity.User;
import com.example.gymapp.enumeration.TrackingType;
import com.example.gymapp.exception.exception.NotFoundException;
import com.example.gymapp.repository.ProgressionRepository;
import com.example.gymapp.service.ProgressionService;
import com.example.gymapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProgressionServiceImpl implements ProgressionService {

    private final ProgressionRepository progressionRepository;
    private final UserService userService;

    @Override
    public void addProgression(ProgressionRequest progressionRequest) {
        String username = Global.getCurrentLogin().getUsername();
        Progression progression = Progression.builder()
                .trackingType(progressionRequest.getTrackingType())
                .value(progressionRequest.getValue())
                .createdDate(Global.getLoggedDate(progressionRequest.getCreatedDate()))
                .createdTime(LocalTime.now()).build();

        checkIfLoggedToday(progression.getCreatedDate(), username, progressionRequest.getTrackingType());

        User currentUser = userService.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("authentication.user-name.not-found"));
        progression.setUser(currentUser);
        progressionRepository.save(progression);
    }

    @Override
    public void removeProgression(ProgressionRequest progressionRequest) {
        progressionRepository.deleteById(progressionRequest.getId());
    }

    @Override
    public void updateProgression(ProgressionRequest progressionRequest) {
        Progression progression = progressionRepository.findById(progressionRequest.getId())
                .orElseThrow(() -> new NotFoundException("progression.progress.not-found"));

        progression.setValue(progressionRequest.getValue());

        if(!Objects.isNull(progressionRequest.getCreatedDate())){
            progression.setCreatedDate(progressionRequest.getCreatedDate());
        }

        progressionRepository.save(progression);
    }

    @Override
    public List<ProgressionRequest> getProgressionList() {
        String username = Global.getCurrentLogin().getUsername();
        return progressionRepository.getProgressionByUser_Username(username)
                .stream()
                .map(this::convertToProgressionDto)
                .sorted(Comparator.comparing(ProgressionRequest::getCreatedDate))
                .collect(Collectors.toList());
    }

    @Override
    public LatestProgressResponse getLatestProgression(ProgressionLatestRequest type) {
        String username = Global.getCurrentLogin().getUsername();
        return progressionRepository
                .getProgressionByUsernameAndTrackingTypeAndCreatedDateTimeMax(username, type.getType())
                .map(this::convertToLatestProgressionDto)
                .orElseGet(() -> LatestProgressResponse.builder().build());
    }

    @Override
    public List<LatestProgressResponse> getLatestProgressionList() {
        String username = Global.getCurrentLogin().getUsername();

        List<LatestProgressResponse> allProgressions = progressionRepository.getProgressionByUser_Username(username)
                .stream()
                .map(this::convertToLatestProgressionDto)
                .toList();

        Map<TrackingType, LatestProgressResponse> latestProgression = new EnumMap<>(TrackingType.class);
        getLatestForEachType(allProgressions, latestProgression);

        return new ArrayList<>(latestProgression.values());
    }

    private void checkIfLoggedToday(LocalDate createdDate, String username, TrackingType type){
        boolean isLogged = progressionRepository.getProgressionByCreatedDateAndUser_UsernameAndTrackingType(createdDate, username, type).isPresent();
        if(isLogged){
            throw new NotFoundException("progression.progress.same-date");
        }
    }

    private ProgressionRequest convertToProgressionDto(Progression entity) {
        ProgressionRequest progressionRequest = new ProgressionRequest();
        BeanUtils.copyProperties(entity, progressionRequest);
        return progressionRequest;
    }

    private LatestProgressResponse convertToLatestProgressionDto(Progression entity) {
        LatestProgressResponse progressionRequest = new LatestProgressResponse();
        BeanUtils.copyProperties(entity, progressionRequest);
        return progressionRequest;
    }

    private void getLatestForEachType(List<LatestProgressResponse> allProgressions, Map<TrackingType, LatestProgressResponse> latestProgressions) {
        for (LatestProgressResponse progression : allProgressions) {
            TrackingType type = progression.getTrackingType();
            if (!latestProgressions.containsKey(type)) {
                latestProgressions.put(type, progression);
            } else {
                LatestProgressResponse existingProgression = latestProgressions.get(type);
                if (existingProgression.getCreatedDate().isBefore(progression.getCreatedDate()) ||
                        (existingProgression.getCreatedDate().isEqual(progression.getCreatedDate()) &&
                                existingProgression.getCreatedTime().isBefore(progression.getCreatedTime()))) {
                    latestProgressions.put(type, progression);
                }
            }
        }
    }

}
