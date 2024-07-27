package com.example.gymapp.repository;

import com.example.gymapp.entity.TrainingProgram;
import com.example.gymapp.enumeration.ProgramType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Long>, JpaSpecificationExecutor<TrainingProgram> {

    @Query("select p from TrainingProgram p where p.deleted = false")
    Page<TrainingProgram> getTrainingProgramsNotDeleted(Pageable pageable);

    @Query("select p from TrainingProgram p where p.deleted = false")
    Page<TrainingProgram> getAllUserTrainingProgram(String username, Pageable pageable, Specification<TrainingProgram> spec);

    @Query("select p from TrainingProgram p " +
            "where p.deleted = false " +
            "and p.type = :type and p.startDate >= :currentDate")
    Page<TrainingProgram> getOfflineTraining(Pageable pageable, ProgramType type, LocalDate currentDate);

    Page<TrainingProgram> findAllByType(ProgramType type, Specification<TrainingProgram> spec, Pageable pageable);

    @Query("select p from TrainingProgram p " +
            "where p.deleted = false " +
            "and p.type = :type")
    Page<TrainingProgram> getOnlineTraining(Pageable pageable, ProgramType type);
}