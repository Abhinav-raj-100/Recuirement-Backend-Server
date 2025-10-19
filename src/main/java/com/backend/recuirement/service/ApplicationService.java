package com.backend.recuirement.service;

import com.backend.recuirement.entities.Application;
import com.backend.recuirement.entities.Job;
import com.backend.recuirement.entities.User;
import com.backend.recuirement.repository.ApplicationRepository;
import com.backend.recuirement.repository.JobRepository;
import com.backend.recuirement.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class ApplicationService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    public String applyingJob(String jobId)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = this.userRepository.findByEmail(email).orElseThrow(()->
                new UsernameNotFoundException("User not found"));

        Job job = this.jobRepository.findById(jobId)
                .orElseThrow(()->new RuntimeException("Job not found"));

        Optional<Application> existing =this.applicationRepository.findByJobAndApplicant(job,user);

        if(existing.isPresent())
        {
            return "You have already applied for this job.";
        }

        Application application  = new Application();
        application.setJob(job);
        application.setApplicant(user);
        application.setAppliedOn(new Date());

        this.applicationRepository.save(application);

        job.getApplicationList().add(application);
        job.setTotalApplications(job.getTotalApplications()+1);
        this.jobRepository.save(job);

        return "Successfully applied for job : "+job.getTitle();
    }
}
