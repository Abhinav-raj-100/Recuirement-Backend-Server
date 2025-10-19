package com.backend.recuirement.controller;


import com.backend.recuirement.request.LoginRequestDto;
import com.backend.recuirement.request.SignUpRequestDto;
import com.backend.recuirement.response.LoginResponseDto;
import com.backend.recuirement.response.SignupResponseDto;
import com.backend.recuirement.service.JwtService;
import com.backend.recuirement.service.UserDetailsServiceImpl;
import com.backend.recuirement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto signUpRequestDto)
    {
        try {

            Boolean isSignedUp = userDetailsService.signUp(signUpRequestDto);

            if (Boolean.FALSE.equals(isSignedUp)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User already exists");
            }

            String jwtToken = jwtService.generateToken(signUpRequestDto.getEmail());

            return ResponseEntity.ok(
                    SignupResponseDto.builder()
                            .token(jwtToken)
                            .id(userDetailsService.getUserIdByEmail(signUpRequestDto.getEmail()))
                            .email(signUpRequestDto.getEmail())
                            .status(userDetailsService.getUserStatus(signUpRequestDto.getEmail()))
                            .message("User Successfully Registered")
                            .build()
            );
        }
        catch (IllegalArgumentException e) {
            // Handle validation errors
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (Exception e) {
            // Handle all other errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal error: " + e.getMessage());
        }

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto)
    {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getEmail(),
                            loginRequestDto.getPassword()
                    )
            );

            String token = this.jwtService.generateToken(loginRequestDto.getEmail());

            return ResponseEntity.ok(
                    LoginResponseDto.builder()
                            .token(token)
                            .message("Login Successfully")
                            .build());
        }
        catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(LoginResponseDto.builder()
                            .message("Invalid email or password")
                            .build());
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(LoginResponseDto.builder()
                            .message("Authentication failed: " + ex.getMessage())
                            .build());
        }
    }

    @PutMapping("/request-admin")
    public ResponseEntity<String> requestAdmin(Authentication auth)
    {
        String email = auth.getName();
        String result = userService.requestAdminAccess(email);
        return ResponseEntity.ok(result);
    }

}
