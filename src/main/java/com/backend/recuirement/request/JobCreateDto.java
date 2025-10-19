package com.backend.recuirement.request;

import com.backend.recuirement.entities.Job;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobCreateDto extends Job {

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    private String company;
}
