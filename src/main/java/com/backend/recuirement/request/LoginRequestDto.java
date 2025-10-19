package com.backend.recuirement.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {

    @NotBlank(message = "Email is mandatory")
    @Email(message="Invalid email format")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;
}
