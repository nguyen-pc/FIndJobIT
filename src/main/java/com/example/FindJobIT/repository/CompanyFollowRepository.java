package com.example.FindJobIT.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.FindJobIT.domain.CompanyFollow;
import com.example.FindJobIT.domain.JobFollow;

public interface CompanyFollowRepository
        extends JpaRepository<CompanyFollow, Long>, JpaSpecificationExecutor<CompanyFollow> {
    List<CompanyFollow> findAllByUserId(long userId);

    Optional<CompanyFollow> findByCompanyIdAndUserId(long jobId, long userId);

    long countByCompany_Id(long companyId);
}
//