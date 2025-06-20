package com.example.FindJobIT.domain.response.user;

import java.time.Instant;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.example.FindJobIT.domain.Skill;
import com.example.FindJobIT.util.constant.GenderEnum;

@Getter
@Setter
public class ResCreateUserDTO {
    private long id;
    private String name;
    private String email;
    private GenderEnum gender;
    private String address;
    private int age;
    private Instant createdAt;
    private CompanyUser company;
    private List<String> skills;

    @Setter
    @Getter
    public static class CompanyUser {
        private long id;
        private String name;
    }
}
