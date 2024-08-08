package com.example.gymapp.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationRequest {

    @NotNull(message = "registration.first-name.not-found")
    @NotEmpty(message = "registration.first-name.not-found")
    private String firstName;

    @NotNull(message = "registration.last-name.not-found")
    @NotEmpty(message = "registration.last-name.not-found")
    private String lastName;

    @NotNull(message = "registration.user-name.not-found")
    @NotEmpty(message = "registration.user-name.not-found")
    private String username;

    @NotNull(message = "registration.gender.not-found")
    @NotEmpty(message = "registration.gender.not-found")
    private String gender;

    @NotNull(message = "registration.password.not-found")
    @Length(min = 8, max = 16, message = "registration.password.length")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$",
            message = "registration.password.pattern")
    private String password;

    @NotNull(message = "registration.birthYear.not-found")
    private Integer birthYear;
}
