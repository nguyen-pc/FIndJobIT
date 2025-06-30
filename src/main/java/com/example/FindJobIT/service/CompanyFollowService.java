package com.example.FindJobIT.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.FindJobIT.domain.Company;
import com.example.FindJobIT.domain.CompanyFollow;
import com.example.FindJobIT.domain.JobFollow;
import com.example.FindJobIT.domain.User;
import com.example.FindJobIT.repository.CompanyFollowRepository;
import com.example.FindJobIT.repository.CompanyRepository;
import com.example.FindJobIT.repository.UserRepository;

@Service
public class CompanyFollowService {
    private final CompanyRepository companyRepository;
    private final CompanyFollowRepository companyFollowRepository;
    private final UserRepository userRepository;

    public CompanyFollowService(CompanyRepository companyRepository, CompanyFollowRepository companyFollowRepository,
            UserRepository userRepository) {
        this.companyFollowRepository = companyFollowRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    public CompanyFollow createFollowCompany(long companyId, long userId) {
        Optional<Company> comOptional = this.companyRepository.findById(companyId);
        Optional<User> userOptional = this.userRepository.findById(userId);
        if (comOptional.isEmpty() || userOptional.isEmpty()) {
            throw new IllegalArgumentException("Company or User not found");
        }

        CompanyFollow companyFollow = new CompanyFollow();
        companyFollow.setCompany(comOptional.get());
        companyFollow.setUser(userOptional.get());
        companyFollow.setCreatedAt(Instant.now());
        return this.companyFollowRepository.save(companyFollow);
    }

    public void deleteFollowCompany(long companyId, long userId) {
        Optional<CompanyFollow> comFollowOptional = this.companyFollowRepository.findByCompanyIdAndUserId(companyId,
                userId);
        if (comFollowOptional.isEmpty()) {
            throw new IllegalArgumentException("JobFollow not found");
        }
        this.companyFollowRepository.delete(comFollowOptional.get());
    }

    // public Iterable<JobFollow> getAllJobFollowsByUserId(Long userId) {
    // Optional<User> userOptional = this.userRepository.findById(userId);
    // if (userOptional.isEmpty()) {
    // throw new IllegalArgumentException("User not found");
    // }
    // return this.jobFollowRepository.findAllByUser(userOptional.get());
    // }

    public List<Company> getCompanyFollowedByUserId(long userId) {
        List<CompanyFollow> follows = companyFollowRepository.findAllByUserId(userId);
        return follows.stream()
                .map(CompanyFollow::getCompany)
                .collect(Collectors.toList());
    }

    public boolean isCompanyFollowedByUser(long companyId, long userId) {
        Optional<CompanyFollow> jobFollowOptional = this.companyFollowRepository.findByCompanyIdAndUserId(companyId,
                userId);
        return jobFollowOptional.isPresent();
    }

    public long getFollowerCountForCompany(long companyId) {
        return companyFollowRepository.countByCompany_Id(companyId);
    }
}
