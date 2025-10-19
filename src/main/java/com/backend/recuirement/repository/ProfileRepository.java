package com.backend.recuirement.repository;

import com.backend.recuirement.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile,String> {

    Optional<Profile> findByApplicantId(String userId);
}
