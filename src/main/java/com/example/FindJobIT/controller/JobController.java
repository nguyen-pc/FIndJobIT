package com.example.FindJobIT.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import com.example.FindJobIT.domain.Job;
import com.example.FindJobIT.domain.Skill;
import com.example.FindJobIT.domain.User;
import com.example.FindJobIT.domain.request.recomend.CandidateDTO;
import com.example.FindJobIT.domain.request.recomend.JobDTO;
import com.example.FindJobIT.domain.request.recomend.RecommendRequest;
import com.example.FindJobIT.domain.response.ResultPaginationDTO;
import com.example.FindJobIT.domain.response.job.ResCreateJobDTO;
import com.example.FindJobIT.domain.response.job.ResUpdateJobDTO;
import com.example.FindJobIT.service.JobService;
import com.example.FindJobIT.service.UserService;
import com.example.FindJobIT.util.SecurityUtil;
import com.example.FindJobIT.util.annotation.ApiMessage;
import com.example.FindJobIT.util.error.IdInvalidException;

@RestController
@RequestMapping("/api/v1")
public class JobController {

    private final JobService jobService;
    private final UserService userService;

    public JobController(JobService jobService, UserService userService) {
        this.jobService = jobService;
        this.userService = userService;
    }

    @PostMapping("/jobs")
    @ApiMessage("Create a job")
    public ResponseEntity<ResCreateJobDTO> create(@Valid @RequestBody Job job) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.jobService.create(job));
    }

    @PutMapping("/jobs")
    @ApiMessage("Update a job")
    public ResponseEntity<ResUpdateJobDTO> update(@Valid @RequestBody Job job) throws IdInvalidException {
        Optional<Job> currentJob = this.jobService.fetchJobById(job.getId());
        if (!currentJob.isPresent()) {
            throw new IdInvalidException("Job not found");
        }

        return ResponseEntity.ok()
                .body(this.jobService.update(job, currentJob.get()));
    }

    @DeleteMapping("/jobs/{id}")
    @ApiMessage("Delete a job by id")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) throws IdInvalidException {
        Optional<Job> currentJob = this.jobService.fetchJobById(id);
        if (!currentJob.isPresent()) {
            throw new IdInvalidException("Job not found");
        }
        this.jobService.delete(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/jobs/{id}")
    @ApiMessage("Get a job by id")
    public ResponseEntity<Job> getJob(@PathVariable("id") long id) throws IdInvalidException {
        Optional<Job> currentJob = this.jobService.fetchJobById(id);
        if (!currentJob.isPresent()) {
            throw new IdInvalidException("Job not found");
        }

        return ResponseEntity.ok().body(currentJob.get());
    }

    @GetMapping("/jobs")
    @ApiMessage("Get job with pagination")
    public ResponseEntity<ResultPaginationDTO> getAllJob(
            @Filter Specification<Job> spec,
            Pageable pageable) {

        return ResponseEntity.ok().body(this.jobService.fetchAll(spec, pageable));
    }

    @GetMapping("/jobs-company/{companyId}")
    @ApiMessage("Get job with company id")
    public ResponseEntity<List<Job>> getJobsByCompany(@PathVariable("companyId") long companyId) {
        return ResponseEntity.ok().body(this.jobService.getJobsByCompany(companyId));
    }

    @PostMapping("/jobs/recommend")
    @ApiMessage("Get recommended jobs")
    public ResponseEntity<?> getRecommendedJobs() {
        String currentUserEmail = SecurityUtil.getCurrentUserLogin()
                .orElseThrow(() -> new RuntimeException("User not logged in"));
        User candidate = userService.handleGetUserByUsername(currentUserEmail);
        System.out.println("Candidate: " + candidate);
        if (candidate == null) {
            throw new RuntimeException("Candidate not found");
        }

        CandidateDTO candidateDTO = new CandidateDTO();
        candidateDTO.setId(candidate.getId());
        candidateDTO.setName(candidate.getName());
        candidateDTO.setAddress(candidate.getAddress());
        candidateDTO.setSkills(candidate.getSkills().stream().map(Skill::getName).collect(Collectors.toList()));

        List<Job> allJobs = this.jobService.getRecommendedJobs();

        List<JobDTO> jobDTOs = allJobs.stream().map(job -> {
            JobDTO dto = new JobDTO();
            dto.setId(job.getId());
            dto.setTitle(job.getName());
            dto.setSkills(job.getSkills().stream()
                    .map(Skill::getName).collect(Collectors.toList()));
            dto.setLocation(job.getLocation());
            return dto;
        }).collect(Collectors.toList());

        // Tạo request gửi sang hệ thống gợi ý
        RecommendRequest recommendRequest = new RecommendRequest();
        recommendRequest.setCandidate(candidateDTO);
        recommendRequest.setJobs(jobDTOs);

        String pythonApiUrl = "http://localhost:8000/recommendations";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RecommendRequest> entity = new HttpEntity<>(recommendRequest, headers);

        ResponseEntity<Object> response = restTemplate.postForEntity(pythonApiUrl, entity, Object.class);
        return ResponseEntity.ok().body(response.getBody());
    }
}
