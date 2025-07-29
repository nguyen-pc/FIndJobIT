package com.example.FindJobIT.domain.request.question;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class QuestionUpdateDTO {
    private Long idQuestion;
    private String questionName;
    private String answer;
    private Long skillId;
    private Long positionId;
    // getters and setters
}
