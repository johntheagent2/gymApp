package com.example.gymapp.controller.user;


import com.example.gymapp.dto.request.UserTargetRequest;
import com.example.gymapp.dto.response.UserInfoResponse;
import com.example.gymapp.enumeration.ActivityFrequency;
import com.example.gymapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

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

    @GetMapping("/activity-type")
    public ResponseEntity<List<ActivityFrequency>> getActivityFrequency(){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Arrays.stream(ActivityFrequency.values()).toList());
    }

    @GetMapping("/is-target")
    public ResponseEntity<Boolean> isTarget(){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.isUserHaveTarget());
    }
    @PutMapping
    public ResponseEntity<Void> updateUserTarget(@Valid @RequestBody UserTargetRequest request){
        userService.updateUserTarget(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
