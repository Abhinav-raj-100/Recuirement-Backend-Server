package com.backend.recuirement.repository;

import com.backend.recuirement.entities.Application;
import com.backend.recuirement.entities.Job;
import com.backend.recuirement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,String> {

    Optional<Application> findByJobAndApplicant(Job job, User applicant);

    List<Application> findByApplicant(User applicant);

    List<Application> findByJob(Job job);
}
