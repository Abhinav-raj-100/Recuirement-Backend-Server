package com.backend.recuirement.request;

import com.backend.recuirement.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequestDto {


    @NotBlank(message = "Username is mandatory")
    private String username;

    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    private String address;

    private String profileHeadLine;

    private String requestedRole;

    private User.UserType userType;
}
