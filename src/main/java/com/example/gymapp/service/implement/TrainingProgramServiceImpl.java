package com.example.gymapp.service.implement;

import com.example.gymapp.dto.request.TrainingLessonActionRequest;
import com.example.gymapp.dto.request.TrainingProgramActionRequest;
import com.example.gymapp.dto.request.TrainingProgramCreationRequest;
import com.example.gymapp.dto.response.TrainingProgramResponse;
import com.example.gymapp.entity.TrainingProgram;
import com.example.gymapp.enumeration.ProgramType;
import com.example.gymapp.exception.exception.BadRequestException;
import com.example.gymapp.repository.TrainingProgramRepository;
import com.example.gymapp.service.TrainingProgramService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class TrainingProgramServiceImpl implements TrainingProgramService {

    private final TrainingProgramRepository trainingProgramRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createProgram(TrainingProgramCreationRequest request) {
        LocalTime startTime = LocalTime.parse("00:00:00");
        if(Objects.equals(request.getType(), ProgramType.OFFLINE.name())){
            startTime = LocalTime.parse(request.getStartTime());
        }

        LocalDate startDate = LocalDate.parse("2002-04-17");
        if(Objects.equals(request.getType(), ProgramType.OFFLINE.name())){
            startDate = LocalDate.parse(request.getStartDate());
        }
        trainingProgramRepository.save(TrainingProgram.builder()
                        .title(request.getTitle())
                        .description(request.getDescription())
                        .type(ProgramType.valueOf(request.getType()))
                        .startDate(startDate)
                        .startTime(startTime)
                        .deleted(false)
                        .build());
    }

    @Override
    public void deleteProgram(TrainingProgramActionRequest request) {
        trainingProgramRepository.findById(request.getId())
                .ifPresent(trainingProgram -> trainingProgram.setDeleted(true));
    }

    public void editProgram(TrainingProgramActionRequest request) {
        TrainingProgram trainingProgram = trainingProgramRepository.findById(request.getId())
                .orElseThrow(() -> new BadRequestException("training-program.program.not-found"));

        if (request.getTitle() != null) {
            trainingProgram.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            trainingProgram.setDescription(request.getDescription());
        }
        if (request.getType() != null) {
            trainingProgram.setType(request.getType());
        }
        if (request.getStartDate() != null) {
            trainingProgram.setStartDate(LocalDate.parse(request.getStartDate()));
        }
        if (request.getStartTime() != null) {
            trainingProgram.setStartTime(LocalTime.parse(request.getStartTime()));
        }

        trainingProgramRepository.save(trainingProgram);
    }

    @Override
    public Page<TrainingProgramResponse> getAllTrainingProgram(Pageable page) {
        return convertToPageTrainingProgramResponse(trainingProgramRepository.getTrainingProgramsNotDeleted(page));
    }

    @Override
    public Page<TrainingProgramResponse> getAllTrainingByType(Pageable page, ProgramType type) {
        return convertToPageTrainingProgramResponse(
                trainingProgramRepository.getTrainingProgramsByTypeAndNotDeleted(page, type));
    }

    @Override
    public void addTrainingLesson(TrainingLessonActionRequest request) {

    }

    private Page<TrainingProgramResponse> convertToPageTrainingProgramResponse(Page<TrainingProgram> request){
        return request.map(trainingProgram -> TrainingProgramResponse.builder()
                .id(trainingProgram.getId())
                .title(trainingProgram.getTitle())
                .description(trainingProgram.getDescription())
                .type(trainingProgram.getType())
                .startDate(trainingProgram.getStartDate())
                .startTime(trainingProgram.getStartTime())
                .build());
    }
}
