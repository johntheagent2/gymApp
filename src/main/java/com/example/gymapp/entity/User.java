package com.example.gymapp.entity;

import com.example.gymapp.entity.base.Account;
import com.example.gymapp.enumeration.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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

    public User(String firstName, String lastName, String username, String password, Role role) {
        super(username, password, role);
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
