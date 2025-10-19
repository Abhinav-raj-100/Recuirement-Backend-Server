package com.backend.recuirement.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class LoginResponseDto {

    private String token;

    private String message;
}
