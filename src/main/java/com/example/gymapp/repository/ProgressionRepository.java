package com.example.gymapp.repository;

import com.example.gymapp.entity.Progression;
import com.example.gymapp.enumeration.TrackingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProgressionRepository extends JpaRepository<Progression, Long> {

    Optional<Progression> getProgressionByCreatedDateAndUser_UsernameAndTrackingType(LocalDate createdDate, String username, TrackingType trackingType);

    List<Progression> getProgressionByUser_Username(String username);

    @Query("SELECT p FROM Progression p " +
            "WHERE p.user.username = :username " +
            "AND p.trackingType = :type " +
            "AND p.createdDate = " +
            "(SELECT MAX(p2.createdDate) FROM Progression p2 " +
            "WHERE p2.user.username = :username " +
            "AND p2.trackingType = :type)")
    Optional<Progression> getProgressionByUsernameAndTrackingTypeAndCreatedDateMax(String username, TrackingType type);
}
