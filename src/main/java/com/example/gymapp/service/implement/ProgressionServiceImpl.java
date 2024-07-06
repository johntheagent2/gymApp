package com.example.gymapp.service.implement;

import com.example.gymapp.common.Global;
import com.example.gymapp.dto.request.ProgressionDto;
import com.example.gymapp.entity.Progression;
import com.example.gymapp.entity.User;
import com.example.gymapp.exception.exception.NotFoundException;
import com.example.gymapp.repository.ProgressionRepository;
import com.example.gymapp.service.ProgressionService;
import com.example.gymapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProgressionServiceImpl implements ProgressionService {

    private final ProgressionRepository progressionRepository;
    private final UserService userService;

    @Override
    public void addProgression(ProgressionDto progressionDto) {
        String username = Global.getCurrentLogin().getUsername();
        Progression progression = new Progression();
        progression.setWeight(progressionDto.getWeight());
        progression.setCreatedDate(getLoggedDate(progressionDto.getCreatedDate()));

        checkIfLoggedToday(progression.getCreatedDate(), username);

        User currentUser = userService.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("authentication.user-name.not-found"));
        progression.setUser(currentUser);
        progressionRepository.save(progression);
    }

    @Override
    public void removeProgression(ProgressionDto progressionDto) {
        progressionRepository.deleteById(progressionDto.getId());
    }

    @Override
    public void updateProgression(ProgressionDto progressionDto) {
        Progression progression = progressionRepository.findById(progressionDto.getId())
                .orElseThrow(() -> new NotFoundException("progression.progress.not-found"));

        if(!Objects.isNull(progressionDto.getWeight())){
            progression.setWeight(progressionDto.getWeight());
        }

        if(!Objects.isNull(progressionDto.getCreatedDate())){
            progression.setCreatedDate(progressionDto.getCreatedDate());
        }

        progressionRepository.save(progression);
    }

    @Override
    public List<ProgressionDto> getProgressionList() {
        String username = Global.getCurrentLogin().getUsername();
        return progressionRepository.getProgressionByUser_Username(username)
                .stream()
                .map(this::convertToProgressionDto)
                .sorted(Comparator.comparing(ProgressionDto::getCreatedDate))
                .collect(Collectors.toList());
    }

    @Override
    public ProgressionDto getLatestProgression(ProgressionDto progressionDto) {
        return null;
    }

    private LocalDate getLoggedDate(LocalDate createdDate){
        if(Objects.isNull(createdDate)){
            return LocalDate.now();
        }else{
            return createdDate;
        }
    }

    private void checkIfLoggedToday(LocalDate createdDate, String username){
        boolean isLogged = progressionRepository.getProgressionByCreatedDateAndUser_Username(createdDate, username).isPresent();
        if(isLogged){
            throw new NotFoundException("progression.progress.same-date");
        }
    }

    private ProgressionDto convertToProgressionDto(Progression entity) {
        ProgressionDto progressionDto = new ProgressionDto();
        BeanUtils.copyProperties(entity, progressionDto);
        return progressionDto;
    }
}
