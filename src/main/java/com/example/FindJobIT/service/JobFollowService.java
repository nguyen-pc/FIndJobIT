package com.example.FindJobIT.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.FindJobIT.domain.Job;
import com.example.FindJobIT.domain.JobFollow;
import com.example.FindJobIT.domain.User;
import com.example.FindJobIT.repository.JobFollowRepository;
import com.example.FindJobIT.repository.JobRepository;
import com.example.FindJobIT.repository.UserRepository;

@Service
public class JobFollowService {
    private final JobRepository jobRepository;
    private final JobFollowRepository jobFollowRepository;
    private final UserRepository userRepository;

    public JobFollowService(JobRepository jobRepository, JobFollowRepository jobFollowRepository,
            UserRepository userRepository) {
        this.jobFollowRepository = jobFollowRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    public JobFollow createFollowJob(long jobId, long userId) {
        Optional<Job> jobOptional = this.jobRepository.findById(jobId);
        Optional<User> userOptional = this.userRepository.findById(userId);
        if (jobOptional.isEmpty() || userOptional.isEmpty()) {
            throw new IllegalArgumentException("Job or User not found");
        }

        JobFollow jobFollow = new JobFollow();
        jobFollow.setJob(jobOptional.get());
        jobFollow.setUser(userOptional.get());
        jobFollow.setCreatedAt(Instant.now());
        return this.jobFollowRepository.save(jobFollow);
    }

    public void deleteFollowJob(long idJobFollow) {
        Optional<JobFollow> jobFollowOptional = this.jobFollowRepository.findById(idJobFollow);
        if (jobFollowOptional.isEmpty()) {
            throw new IllegalArgumentException("JobFollow not found");
        }
        this.jobFollowRepository.delete(jobFollowOptional.get());
    }

    // public Iterable<JobFollow> getAllJobFollowsByUserId(Long userId) {
    //     Optional<User> userOptional = this.userRepository.findById(userId);
    //     if (userOptional.isEmpty()) {
    //         throw new IllegalArgumentException("User not found");
    //     }
    //     return this.jobFollowRepository.findAllByUser(userOptional.get());
    // }

    public List<Job> getJobsFollowedByUserId(long userId) {
        List<JobFollow> follows = jobFollowRepository.findAllByUserId(userId);
        return follows.stream()
                .map(JobFollow::getJob)
                .collect(Collectors.toList());
    }
}
