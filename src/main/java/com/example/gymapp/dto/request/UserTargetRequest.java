package com.example.gymapp.dto.request;

import com.example.gymapp.enumeration.ActivityFrequency;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTargetRequest {
    double weight;
    double height;
    ActivityFrequency activityFrequency;
}
