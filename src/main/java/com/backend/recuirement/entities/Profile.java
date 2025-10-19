package com.backend.recuirement.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String resumeUrl;

    @Column(length = 2000)
    private String skills;

    @Column(length = 2000)
    private String experience;

    @Column(length = 2000)
    private String education;

    private String phone;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonIgnore
    private User applicant;
}
