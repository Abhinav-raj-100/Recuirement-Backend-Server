package com.backend.recuirement.service;

import com.backend.recuirement.entities.User;
import com.backend.recuirement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String requestAdminAccess(String email)
    {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found with email: " + email));

        if(user.getUserType()==User.UserType.ADMIN)
        {
            return "You are already an admin.";
        }

        if("PENDING_APPROVAL".equals(user.getStatus()))
        {
            return "Your admin request is already pending.";
        }

        user.setRequestedRole("ADMIN");
        user.setStatus("PENDING_APPROVAL");
        this.userRepository.save(user);

        return "Admin access requested successfully. Waiting for approval.";
    }
}
