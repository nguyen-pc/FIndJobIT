package com.example.FindJobIT.domain.request.question;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReqQuestion {
    private long skillId;
    private long positionId;
    private String questionName;
    private String answer;
}
