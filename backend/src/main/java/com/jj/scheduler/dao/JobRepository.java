package com.jj.scheduler.dao;

import com.jj.scheduler.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByUserId(Long userID);
}
