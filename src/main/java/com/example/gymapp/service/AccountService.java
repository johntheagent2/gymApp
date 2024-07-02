package com.example.gymapp.service;

import com.example.gymapp.dto.request.RegistrationRequest;
import com.example.gymapp.entity.User;
import com.example.gymapp.entity.base.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    <T extends Account> T findByUserName(String username, Class<T> instance);

    boolean isUsernameExisted(String username);

    <T extends Account> void save(RegistrationRequest request, Class<T> instance);

    Optional<Account> findById(Long id);

    List<Account> findAll();

    User update(Account account);

    void delete(Long id);

    void updateCountWrongLogin(Account account);

    void resetWrongLoginCounter(Account account);
}
