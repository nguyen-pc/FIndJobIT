package com.example.FindJobIT.domain.request.recomend;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class JobDTO {
    private Long id;
    private String title;
    private List<String> skills;
    private String location;
}