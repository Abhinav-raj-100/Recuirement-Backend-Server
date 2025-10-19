package com.backend.recuirement.util;

import com.backend.recuirement.entities.Job;
import com.backend.recuirement.response.JobResponseDto;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {
    public JobResponseDto toDto(Job job) {

        return JobResponseDto.builder()
                .title(job.getTitle())
                .id(job.getId())
                .totalApplication(job.getTotalApplications())
                .company(job.getCompany())
                .postedDate(job.getPostedDate())
                .description(job.getDescription())
                . build();
    }
}

