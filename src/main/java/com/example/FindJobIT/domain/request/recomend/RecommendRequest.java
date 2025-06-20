package com.example.FindJobIT.domain.request.recomend;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class RecommendRequest {
    private CandidateDTO candidate;
    private List<JobDTO> jobs;
}