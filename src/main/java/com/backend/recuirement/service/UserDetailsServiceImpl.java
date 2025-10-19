package com.backend.recuirement.service;

import com.backend.recuirement.entities.User;
import com.backend.recuirement.repository.UserRepository;
import com.backend.recuirement.request.SignUpRequestDto;
import com.backend.recuirement.util.AuthValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new CustomUserDetails(user);
    }

    public Optional<User> checkIfUserAlreadyExists(SignUpRequestDto signUpRequestDto)
    {
        return this.userRepository.findByEmail(signUpRequestDto.getEmail());
    }

    public String getUserIdByEmail(String email)
    {
        return this.userRepository.findByEmail(email)
                .map(User::getId).orElse(null);
    }

    public String getUserStatus(String email)
    {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found with email: " + email));

        return  user.getStatus();
    }

    public Boolean signUp(SignUpRequestDto signUpRequestDto)
    {
        String email =  signUpRequestDto.getEmail();
        String password = signUpRequestDto.getPassword();


        if(!AuthValidator.isValidEmail(email))
        {
            throw new IllegalArgumentException("Invalid email format.");
        }

        String passwordError = AuthValidator.getPasswordValidationError(password);

        if(passwordError!=null)
        {
            throw new IllegalArgumentException("Invalid password: " + passwordError);
        }

        if(checkIfUserAlreadyExists(signUpRequestDto).isPresent())
        {
            return false;
        }

        signUpRequestDto.setPassword(passwordEncoder.encode(password));

        User user = new User();
        user.setEmail(signUpRequestDto.getEmail());
        user.setPasswordHashed(signUpRequestDto.getPassword());
        user.setName(signUpRequestDto.getUsername());

        if (signUpRequestDto.getUserType() == null || signUpRequestDto.getUserType() == User.UserType.APPLICANT) {
            // default applicant signup
            user.setUserType(User.UserType.APPLICANT);
            user.setRequestedRole(null);
            user.setStatus("ACTIVE");
        } else if (signUpRequestDto.getUserType() == User.UserType.ADMIN) {
            // user requested admin role
            user.setUserType(User.UserType.APPLICANT); // still applicant until approval
            user.setRequestedRole("ADMIN");
            user.setStatus("PENDING_APPROVAL");
        }
        user.setProfileHeadline(signUpRequestDto.getProfileHeadLine());
        user.setAddress(signUpRequestDto.getAddress());
        user.setProfile(null);

        this.userRepository.save(user);

        return true;
    }



}
