package com.example.gymapp.controller.user;


import com.example.gymapp.dto.response.UserInfoResponse;
import com.example.gymapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${user-mapping}/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserInfoResponse> init(){
        UserInfoResponse response = userService.getCurrentUser();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @PutMapping
    public ResponseEntity<UserInfoResponse> updateUserTarget(){
        UserInfoResponse response = userService.getCurrentUser();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
