package com.example.gymapp.repository;

import com.example.gymapp.dto.request.ProgressionDto;
import com.example.gymapp.entity.Admin;
import com.example.gymapp.entity.Progression;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProgressionRepository extends JpaRepository<Progression, Long> {

    Optional<Progression> getProgressionByCreatedDateAndUser_Username(LocalDate createdDate, String username);

    List<Progression> getProgressionByUser_Username(String username);
}
