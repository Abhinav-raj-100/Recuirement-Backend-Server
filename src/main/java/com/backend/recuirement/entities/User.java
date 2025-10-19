package com.backend.recuirement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;



@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordHashed;

    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.APPLICANT;

    private String requestedRole;

    private String status;

    private String profileHeadline;

    private String address;

    @OneToOne(mappedBy = "applicant", cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private Profile profile;

    public enum UserType
    {
        APPLICANT,
        ADMIN,
    }
}
