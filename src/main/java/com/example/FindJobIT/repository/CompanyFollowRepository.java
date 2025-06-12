package com.example.FindJobIT.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.FindJobIT.domain.CompanyFollow;


public interface CompanyFollowRepository
        extends JpaRepository<CompanyFollow, Long>, JpaSpecificationExecutor<CompanyFollow> {
    List<CompanyFollow> findAllByUserId(long userId);

}
// 