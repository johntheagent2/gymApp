package com.example.gymapp.entity;

import com.example.gymapp.entity.base.Account;
import com.example.gymapp.entity.base.TrainingProgram;
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

    public User(String firstName, String lastName, String username, String password, Role role) {
        super(username, password, role);
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
