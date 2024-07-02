package com.example.gymapp.service.implement;

import com.example.gymapp.dto.request.RegistrationRequest;
import com.example.gymapp.entity.Admin;
import com.example.gymapp.entity.User;
import com.example.gymapp.entity.base.Account;
import com.example.gymapp.enumeration.Role;
import com.example.gymapp.exception.exception.NotFoundException;
import com.example.gymapp.repository.AccountRepository;
import com.example.gymapp.service.AccountService;
import com.example.gymapp.service.AdminService;
import com.example.gymapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AdminService adminService;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public <T extends Account> T findByUserName(String username, Class<T> instance) {
        if (instance.isAssignableFrom(Admin.class)) {
            return instance.cast(adminService.findByUsername(username)
                    .orElseThrow(() -> new NotFoundException("admin.username.not-found")));
        }else {
            return instance.cast(userService.findByUsername(username)
                    .orElseThrow(() -> new NotFoundException("user.username.not-found")));
        }
    }

    @Override
    public boolean isUsernameExisted(String username) {
        return accountRepository.findAccountByUsername(username).isPresent();
    }

    @Override
    public <T extends Account> void save(RegistrationRequest request, Class<T> instance) {
        if (instance.isAssignableFrom(Admin.class)) {
            Admin admin = new Admin(request.getLastName() + " " + request.getFirstName(),
                    request.getUsername(),
                    bCryptPasswordEncoder.encode(request.getPassword()),
                    Role.ADMIN);
            adminService.save(admin);
        }else {
            User user = new User(request.getFirstName(),
                    request.getLastName(),
                    request.getUsername(),
                    bCryptPasswordEncoder.encode(request.getPassword()),
                    Role.USER);
            userService.save(user);
        }
    }

    @Override
    public Optional<Account> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Account> findAll() {
        return null;
    }

    @Override
    public User update(Account account) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void updateCountWrongLogin(Account account) {
        account.setLoginAttempt(account.getLoginAttempt() + 1);
    }

    @Override
    public void resetWrongLoginCounter(Account account) {
        account.setLoginAttempt(0);
    }
}
