package com.backend.recuirement.controller;

import com.backend.recuirement.entities.User;
import com.backend.recuirement.repository.JobRepository;
import com.backend.recuirement.request.JobCreateDto;
import com.backend.recuirement.response.JobResponseDto;
import com.backend.recuirement.service.AdminService;
import com.backend.recuirement.service.JobService;
import com.backend.recuirement.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
@RestController
public class AdminController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JobService jobService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private JobRepository jobRepository;

    @PutMapping("/approve-admin/{userId}")
    public ResponseEntity<String> approveAdminRequest(@PathVariable(name = "userId") String userId,
                                                      Authentication auth)
    {
        String result = adminService.approveAdminRequest(userId, auth.getName());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get-all-pending-users")
    public ResponseEntity<List<User>> getAllPendingUsers() {
        List<User> pendingUsers = this.adminService.getAllPendingUsers();
        return ResponseEntity.ok(pendingUsers); // automatically 200 OK
    }

    @GetMapping("/applicants")
    public ResponseEntity<List<User>> getAllUsers()
    {
        List<User> userList = this.adminService.getAllUsers();
        return ResponseEntity.ok(userList);
    }

    @PostMapping("/job")
    public ResponseEntity<JobResponseDto> createJob(@RequestBody JobCreateDto jobCreateDto)
    {
        JobResponseDto response = this.jobService.createJob(jobCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/job/{job_id}")
    public ResponseEntity<?> getJobById(@PathVariable(name = "job_id") String jobId)
    {
        JobResponseDto jobResponseDto = this.jobService.getJobById(jobId);

        if(jobResponseDto !=null)
        {
            return ResponseEntity.status(HttpStatus.FOUND).body(jobResponseDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job Not Found for the id "+ jobId);
    }

    @GetMapping("/applicants/{applicant_id}")
    public ResponseEntity<?> getUserById(@PathVariable(name = "applicant_id") String userId)
    {
        User user  = this.adminService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.FOUND).body(user);
    }


}
