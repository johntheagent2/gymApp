package com.example.gymapp.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "training_lesson")
@AllArgsConstructor
public class TrainingLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "training_lesson_generator")
    @SequenceGenerator(name = "training_lesson_generator", sequenceName = "training_lesson_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "varchar(500)")
    private String description;

    @Column(name = "url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_program_id")
    private TrainingProgram trainingProgram;
}
