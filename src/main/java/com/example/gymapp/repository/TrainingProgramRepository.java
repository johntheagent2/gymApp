package com.example.gymapp.repository;

import com.example.gymapp.entity.TrainingProgram;
import com.example.gymapp.enumeration.ProgramType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Long> {

    @Query("select p from TrainingProgram p where p.deleted = false")
    Page<TrainingProgram> getTrainingProgramsNotDeleted(Pageable pageable);

    @Query("select p from TrainingProgram p where p.deleted = false and p.type = :type")
    Page<TrainingProgram> getTrainingProgramsByTypeAndNotDeleted(Pageable pageable, ProgramType type);
}