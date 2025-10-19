package com.backend.recuirement.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SignupResponseDto {

    private String id;

    private String message;

    private String email;

    private String token;

    private String status;
}
