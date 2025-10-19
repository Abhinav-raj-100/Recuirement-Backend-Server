package com.backend.recuirement.service;

import com.backend.recuirement.entities.User;
import com.backend.recuirement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllPendingUsers() {
        return this.userRepository.findByStatus("PENDING_APPROVAL");
    }

    public List<User> getAllUsers()
    {
        return this.userRepository.findAll();
    }

    public User getUserById(String userId)
    {
        Optional<User> user = this.userRepository.findById(userId);

        return user.orElse(null);
    }

    public String approveAdminRequest(String userId, String email)
    {


        User approver = this.userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("Approver not found : " + email));

        if(approver.getUserType()!= User.UserType.ADMIN)
        {
            return "Only admins can approve admin requests.";
        }

        User user = this.userRepository.findById(userId)
                .orElseThrow(()-> new UsernameNotFoundException("User not found with id: " + userId));

        if(!"ADMIN".equalsIgnoreCase(user.getRequestedRole()) ||
                !"PENDING_APPROVAL".equalsIgnoreCase(user.getStatus()))
        {
            return "No pending admin request for this user.";
        }

        user.setUserType(User.UserType.ADMIN);
        user.setRequestedRole(null);
        user.setStatus("ACTIVE");
        this.userRepository.save(user);

        return "User "+ user.getEmail() +" has been granted admin access.";
    }

}
