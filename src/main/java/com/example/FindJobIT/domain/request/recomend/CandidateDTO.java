package com.example.FindJobIT.domain.request.recomend;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CandidateDTO {
    private Long id;
    private String name;
    private List<String> skills;
    private String address;
}