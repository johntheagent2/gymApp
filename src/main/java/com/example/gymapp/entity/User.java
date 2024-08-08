package com.example.gymapp.entity;

import com.example.gymapp.entity.base.Account;
import com.example.gymapp.enumeration.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "user_account")
public class User extends Account {
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "gender", nullable = false, length = 10)
    private String gender;

    @Column(name = "birth_year", nullable = false, length = 10)
    private Integer brithYear;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "height")
    private Double height;

    @Column(name = "daily_target_calories")
    private Double targetCalories;

    @Column(name = "daily_target_protein")
    private Double targetProtein;

    @Column(name = "daily_target_fat")
    private Double targetFat;

    @Column(name = "daily_target_carbs")
    private Double targetCarbs;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Progression> progressions;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<History> histories;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_training_program",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "program_id")
    )
    private List<TrainingProgram> trainingPrograms;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<FoodNutrition> foodNutritions;

    public User(String firstName, String lastName, String gender, Integer brithYear,
                String username, String password, Role role) {
        super(username, password, role);
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.brithYear = brithYear;
    }
}
