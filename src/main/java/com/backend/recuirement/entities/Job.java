package com.backend.recuirement.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "jobs")
@Entity
@Data
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(name = "posted_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date postedDate;


    private Integer totalApplications;

    private String company;

    @ManyToOne
    @JoinColumn(name = "posted_by", nullable = false)
    @ToString.Exclude
    private User postedBy;

    @OneToMany(mappedBy = "job",cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private List<Application> applicationList = new ArrayList<>();

    @PrePersist
    protected void onCreate()
    {
        if(postedDate==null)
        {
            postedDate = new Date();
        }

        if(totalApplications ==null)
        {
            totalApplications = 0;
        }
    }
}
