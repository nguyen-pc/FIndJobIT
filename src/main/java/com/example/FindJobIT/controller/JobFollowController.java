package com.example.FindJobIT.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.FindJobIT.domain.Job;
import com.example.FindJobIT.domain.JobFollow;
import com.example.FindJobIT.domain.request.followJob.ReqFollowJob;
import com.example.FindJobIT.domain.response.followJob.JobFollowSimpleDTO;
import com.example.FindJobIT.service.JobFollowService;
import com.example.FindJobIT.util.annotation.ApiMessage;
import com.example.FindJobIT.util.error.IdInvalidException;

import jakarta.websocket.server.PathParam;

@Controller
@RequestMapping("/api/v1")
public class JobFollowController {
    private final JobFollowService jobFollowService;

    public JobFollowController(JobFollowService jobFollowService) {
        this.jobFollowService = jobFollowService;
    }

    @PostMapping("/jobs/follow")
    @ApiMessage("Follow a job")
    public ResponseEntity<JobFollowSimpleDTO> followJob(@RequestBody ReqFollowJob reqFollowJob) {
        JobFollow jobFollow = jobFollowService.createFollowJob(reqFollowJob.getJobId(), reqFollowJob.getUserId());
        JobFollowSimpleDTO dto = new JobFollowSimpleDTO();
        dto.setIdJobFollow(jobFollow.getIdJobFollow());
        dto.setJobId(jobFollow.getJob().getId());
        dto.setUserId(jobFollow.getUser().getId());
        dto.setCreatedAt(jobFollow.getCreatedAt());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dto);
    }

    @DeleteMapping("/jobs/follow/{id}")
    @ApiMessage("Unfollow a job by id")
    public ResponseEntity<Void> unfollowJob(@PathVariable("id") long id) throws IdInvalidException {
        this.jobFollowService.deleteFollowJob(id);
        return ResponseEntity.ok().body(null);

    }

    @GetMapping("/jobs/follow/{id}")
    @ApiMessage("Get all job follows")
    public ResponseEntity<?> getAllJobFollows(@PathVariable("id") long id) throws IdInvalidException {

        List<Job> jobs = jobFollowService.getJobsFollowedByUserId(id);
        return ResponseEntity.ok(jobs);
    }
}
