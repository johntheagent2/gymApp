package com.example.gymapp.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoResponse {
    private String firstName;
    private String lastName;
    private String gender;
    private int age;
    private double height;
    private double weight;
    private String profilePicture;
}
