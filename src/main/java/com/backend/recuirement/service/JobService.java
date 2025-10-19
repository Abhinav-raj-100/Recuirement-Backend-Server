package com.backend.recuirement.service;

import com.backend.recuirement.entities.Job;
import com.backend.recuirement.entities.User;
import com.backend.recuirement.repository.JobRepository;
import com.backend.recuirement.repository.UserRepository;
import com.backend.recuirement.request.JobCreateDto;
import com.backend.recuirement.response.JobResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Job> getAllJob()
    {
        return this.jobRepository.findAll();
    }

    private User getCurrentUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return this.userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User not found with this email id "+email));
    }
    public JobResponseDto createJob(JobCreateDto jobCreateDto)
    {
        Job job = new Job();
        job.setPostedDate(new Date());
        job.setTitle(jobCreateDto.getTitle());
        job.setDescription(jobCreateDto.getDescription());
        job.setCompany(jobCreateDto.getCompany());
        job.setPostedBy(getCurrentUser());

        this.jobRepository.save(job);

        return JobResponseDto.builder()
                .title(job.getTitle())
                .id(job.getId())
                .company(job.getCompany())
                .totalApplication(job.getTotalApplications())
                .description(job.getDescription())
                .postedDate(job.getPostedDate())
                .build();
    }

    public JobResponseDto getJobById(String id)
    {
        Job job = this.jobRepository.findById(id).orElse(null);
        return JobResponseDto.builder()
                .id(job.getId())
                .title(job.getTitle())
                .company(job.getCompany())
                .description(job.getDescription())
                .postedDate(job.getPostedDate())
                .totalApplication(job.getTotalApplications())
                .build();
    }
}
