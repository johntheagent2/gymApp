package com.example.gymapp.entity.base;

import com.example.gymapp.entity.TrainingLesson;
import com.example.gymapp.entity.User;
import com.example.gymapp.enumeration.ProgramType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "training_program")
public class TrainingProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "training_program_generator")
    @SequenceGenerator(name = "training_program_generator", sequenceName = "training_program_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProgramType type;

    @Column(name = "start_time")
    private Instant startTime;

    @ManyToMany(mappedBy = "trainingPrograms", fetch = FetchType.LAZY)
    private List<User> users;

    @OneToMany(mappedBy = "trainingProgram", fetch = FetchType.LAZY)
    private List<TrainingLesson> trainingLessons;
}
