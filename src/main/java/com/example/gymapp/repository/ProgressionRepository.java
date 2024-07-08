package com.example.gymapp.repository;

import com.example.gymapp.entity.Progression;
import com.example.gymapp.enumeration.TrackingType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProgressionRepository extends JpaRepository<Progression, Long> {

    Optional<Progression> getProgressionByCreatedDateAndUser_UsernameAndTrackingType(LocalDate createdDate, String username, TrackingType trackingType);

    List<Progression> getProgressionByUser_Username(String username);
}
