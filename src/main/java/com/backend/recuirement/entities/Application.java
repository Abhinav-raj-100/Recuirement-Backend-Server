package com.backend.recuirement.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Table(name = "applications")
@Entity
@Data
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(optional = false)
    @JoinColumn(name="job_id",nullable = false)
    @ToString.Exclude
    private Job job;

    @ManyToOne(optional = false)
    @JoinColumn(name = "applicant_id", nullable = false)
    @ToString.Exclude
    private User applicant;

    private Date appliedOn;

    protected void onCreate()
    {
        if(appliedOn==null)
        {
            appliedOn = new Date();
        }
    }
}
