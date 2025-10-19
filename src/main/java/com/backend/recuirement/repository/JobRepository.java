package com.backend.recuirement.repository;

import com.backend.recuirement.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job,String> {

    List<Job> findByPostedById(String adminId);

    List<Job> findByCompanyContainingIgnoreCase(String company);

    List<Job> findByTitleContainingIgnoreCase(String title);

}
