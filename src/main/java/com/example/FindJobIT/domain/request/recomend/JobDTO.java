package com.example.FindJobIT.domain.request.recomend;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class JobDTO {
    private Long id;
    private String title;
    private List<String> skills;
    private String location;
    private double salary;
    private int quantity;
    private Instant startDate;
    private Instant endDate;
    private String logoUrl;
}