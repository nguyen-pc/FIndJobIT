package com.example.FindJobIT.repository;

import org.springframework.stereotype.Repository;

import com.example.FindJobIT.domain.Job;
import com.example.FindJobIT.domain.JobFollow;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface JobFollowRepository extends JpaRepository<JobFollow, Long>,
                JpaSpecificationExecutor<JobFollow> {
        List<JobFollow> findAllByUserId(long userId);

        Optional<JobFollow> findByJobIdAndUserId(long jobId, long userId);

        long countByJob_Company_Id(long companyId);
}
