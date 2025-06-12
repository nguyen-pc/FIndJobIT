package com.example.FindJobIT.domain.request.followCompany;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReqFollowCompany {
    private long companyId;
    private long userId;
}
