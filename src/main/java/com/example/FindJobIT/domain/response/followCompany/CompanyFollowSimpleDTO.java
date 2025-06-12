package com.example.FindJobIT.domain.response.followCompany;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyFollowSimpleDTO {
    private Long idCompanyFollow;
    private Long companyId;
    private Long userId;
    private Instant createdAt;
}
