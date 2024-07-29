package com.example.gymapp.service.implement;

import com.example.gymapp.common.Global;
import com.example.gymapp.dto.request.TrainingLessonActionRequest;
import com.example.gymapp.dto.request.TrainingProgramActionRequest;
import com.example.gymapp.dto.request.TrainingProgramCreationRequest;
import com.example.gymapp.dto.response.TrainingLessonResponse;
import com.example.gymapp.dto.response.TrainingProgramResponse;
import com.example.gymapp.entity.*;
import com.example.gymapp.entity.criteria.EnumFilter;
import com.example.gymapp.entity.criteria.TrainingProgramCriteria;
import com.example.gymapp.enumeration.ProgramType;
import com.example.gymapp.exception.exception.BadRequestException;
import com.example.gymapp.exception.exception.NotFoundException;
import com.example.gymapp.repository.TrainingLessonRepository;
import com.example.gymapp.repository.TrainingProgramRepository;
import com.example.gymapp.service.TrainingProgramService;
import com.example.gymapp.service.UserService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.StringFilter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Transactional
public class TrainingProgramServiceImpl extends QueryService<TrainingProgram> implements TrainingProgramService {

    private final TrainingProgramRepository trainingProgramRepository;
    private final TrainingLessonRepository trainingLessonRepository;
    private final UserService userService;

    @Override
    public void createProgram(TrainingProgramCreationRequest request) {
        LocalTime startTime = LocalTime.parse("00:00:00");
        if(Objects.equals(request.getType(), ProgramType.OFFLINE.name())){
            startTime = LocalTime.parse(request.getStartTime());
        }

        LocalDate startDate = LocalDate.parse("2099-01-01");
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
        TrainingProgram trainingProgram = findTrainingProgram(request.getId());

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
    public Page<TrainingProgramResponse> getAllTrainingProgram(TrainingProgramCriteria request, Pageable page) {
        Page<TrainingProgram> result = getFilterProgram(request, page);
        return toPageTrainingProgramResponse(result);
    }

    @Override
    public Page<TrainingProgramResponse> getAllTrainingByType(TrainingProgramCriteria request,
                                                              Pageable page, ProgramType type) {
        request.setProgramType(
                (TrainingProgramCriteria.ProgramTypeFilter) new TrainingProgramCriteria
                        .ProgramTypeFilter().setEquals(type));
        Page<TrainingProgram> result = getFilterProgram(request, page);
        return toPageTrainingProgramResponse(result);
    }

    @Override
    public void addTrainingLesson(TrainingLessonActionRequest request) {
        TrainingProgram trainingProgram = findTrainingProgram(request.getProgramId());

        TrainingLesson trainingLesson = TrainingLesson.builder()
                .name(request.getName())
                .description(request.getDescription())
                .url(request.getUrl())
                .trainingProgram(trainingProgram) // Set the trainingProgram field
                .build();

        trainingProgram.getTrainingLessons().add(trainingLesson);
        trainingProgramRepository.save(trainingProgram);
    }


    @Override
    public void subscribeTrainingProgram(Long id) {
        TrainingProgram trainingProgram = findTrainingProgram(id);

        User user = userService.findByUsername(Global.getCurrentLogin().getUsername())
                .orElseThrow(() -> new NotFoundException("user.username.not-found"));
        user.getTrainingPrograms().add(trainingProgram);
    }

    @Override
    public TrainingProgramResponse getTrainingProgram(Long id) {
        TrainingProgram trainingProgram = findTrainingProgram(id);
        return trainingProgramMapper(trainingProgram);
    }

    @Override
    @Transactional
    public Page<TrainingProgramResponse> getTrainingProgramList(TrainingProgramCriteria request, Pageable page) {
        Page<TrainingProgram> result = getFilterProgram(request, page);
        return toPageTrainingProgramResponse(result);
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

    private boolean filterOfflineOutdatedProgram(TrainingProgram program){
        ProgramType type = program.getType();
        if(type.equals(ProgramType.ONLINE) && program.getStartDate().isBefore(LocalDate.now())){
            return false;
        }{
            return true;
        }
    }

    private TrainingProgram findTrainingProgram(Long id){
        return trainingProgramRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("training-program.program.not-found "));
    }

    private TrainingProgramResponse trainingProgramMapper(TrainingProgram program){
        List<TrainingLessonResponse> lessonList = trainingLessonRepository
                .findByTrainingProgram_Id(program.getId()).stream()
                .map(lesson -> TrainingLessonResponse.builder()
                        .id(lesson.getId())
                        .title(lesson.getName())
                        .description(lesson.getDescription())
                        .url(lesson.getUrl())
                        .build())
                .toList();

        return TrainingProgramResponse.builder()
                .id(program.getId())
                .title(program.getTitle())
                .description(program.getDescription())
                .type(program.getType())
                .startDate(program.getStartDate())
                .startTime(program.getStartTime())
                .trainingLessons(lessonList)
                .build();
    }

    private Page<TrainingProgram> getFilterProgram(TrainingProgramCriteria request, Pageable page){
        request.setDeleted(false);
        final Specification<TrainingProgram> specification = createSpecification(request);
        return trainingProgramRepository.findAll(specification, page);
    }

    private Page<TrainingProgramResponse> toPageTrainingProgramResponse(Page<TrainingProgram> programs){
        return programs
                .map(trainingProgram -> TrainingProgramResponse.builder()
                        .id(trainingProgram.getId())
                        .title(trainingProgram.getTitle())
                        .description(trainingProgram.getDescription())
                        .type(trainingProgram.getType())
                        .startDate(trainingProgram.getStartDate())
                        .startTime(trainingProgram.getStartTime())
                        .trainingLessons(trainingProgram.getTrainingLessons().stream().map(
                                trainingLesson -> TrainingLessonResponse.builder()
                                        .id(trainingLesson.getId())
                                        .title(trainingLesson.getName())
                                        .description(trainingLesson.getDescription())
                                        .url(trainingLesson.getUrl())
                                        .build()
                        ).toList())
                        .build());
    }

    private Specification<TrainingProgram> createSpecification(TrainingProgramCriteria criteria) {
        Specification<TrainingProgram> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getDeleted() != null) {
                specification = specification.and((root, query, builder) ->
                        builder.equal(root.get(TrainingProgram_.DELETED), criteria.getDeleted()));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), TrainingProgram_.title));
            }
            if (criteria.getUserName() != null) {
                specification = specification.and(buildSpecification(criteria.getUserName(), root ->
                        root.join(TrainingProgram_.USERS).get(User_.USERNAME)));
            }
            if (criteria.getProgramType() != null) {
                specification = specification.and(buildSpecification(criteria.getProgramType(), TrainingProgram_.type));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and((root, query, builder) ->
                        builder.greaterThanOrEqualTo(root.get(TrainingProgram_.START_DATE),
                                criteria.getStartDate().getGreaterThanOrEqual()));
            }
            if (criteria.getCurrentDate() != null) {
                specification = specification.and((root, query, builder) ->
                        builder.equal(root.get(TrainingProgram_.START_DATE),
                                criteria.getCurrentDate().getEquals()));
            }
        }
        return specification;
    }
}
