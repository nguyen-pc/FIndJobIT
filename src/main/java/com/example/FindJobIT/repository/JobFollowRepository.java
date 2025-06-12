package com.example.FindJobIT.repository;

import org.springframework.stereotype.Repository;

import com.example.FindJobIT.domain.Job;
import com.example.FindJobIT.domain.JobFollow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
@Repository
public interface JobFollowRepository extends JpaRepository<JobFollow, Long>,
        JpaSpecificationExecutor<JobFollow> {

        
}

