package com.example.FindJobIT.domain.response.followJob;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JobFollowSimpleDTO {
    private Long idJobFollow;
    private Long jobId;
    private Long userId;
    private Instant createdAt;
}