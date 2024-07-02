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
@Table(name = "admin_account")
public class Admin extends Account {

    @Column(name = "full_name", nullable = false)
    private String fullName;

    public Admin(String fullName, String username, String password, Role role){
        super(username, password, role);
        this.fullName = fullName;
    }
}
