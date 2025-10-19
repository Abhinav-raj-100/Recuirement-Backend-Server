package com.backend.recuirement.controller;

import com.backend.recuirement.entities.Job;
import com.backend.recuirement.response.JobResponseDto;
import com.backend.recuirement.service.ApplicationService;
import com.backend.recuirement.service.JobService;
import com.backend.recuirement.util.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private JobMapper jobMapper;

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/")
    public ResponseEntity<List<JobResponseDto>> getAllJobs()
    {
        List<Job> jobList = this.jobService.getAllJob();
        List<JobResponseDto> jobResponseDtoList =jobList.stream().map(jobMapper::toDto).toList();
        return ResponseEntity.status(HttpStatus.OK).body(jobResponseDtoList);
    }

    @GetMapping("/apply")
    @PreAuthorize("hasRole('APPLICANT')")
    public ResponseEntity<String> applyToJob(@RequestParam("job_id") String jobId) {
        String result = applicationService.applyingJob(jobId);
        System.out.println(result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Job>> getAllJobsForAdmin()
    {
        List<Job> jobList =this.jobService.getAllJob();
        return ResponseEntity.status(HttpStatus.OK).body(jobList);
    }

}
