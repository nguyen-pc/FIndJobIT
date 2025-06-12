package com.example.FindJobIT.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.FindJobIT.domain.Company;
import com.example.FindJobIT.domain.CompanyFollow;
import com.example.FindJobIT.domain.request.followCompany.ReqFollowCompany;
import com.example.FindJobIT.domain.response.followCompany.CompanyFollowSimpleDTO;
import com.example.FindJobIT.service.CompanyFollowService;
import com.example.FindJobIT.util.annotation.ApiMessage;
import com.example.FindJobIT.util.error.IdInvalidException;

@Controller
@RequestMapping("/api/v1")
public class CompanyFollowController {
    private final CompanyFollowService companyFollowService;

    public CompanyFollowController(CompanyFollowService companyFollowService) {
        this.companyFollowService = companyFollowService;
    }

    @PostMapping("/companies/follow")
    @ApiMessage("Follow a company")
    public ResponseEntity<CompanyFollowSimpleDTO> followJob(@RequestBody ReqFollowCompany reqFollowCompany) {
        CompanyFollow comFollow = companyFollowService.createFollowCompany(reqFollowCompany.getCompanyId(),
                reqFollowCompany.getUserId());
        CompanyFollowSimpleDTO dto = new CompanyFollowSimpleDTO();
        dto.setIdCompanyFollow(comFollow.getIdCompanyFollow());
        dto.setCompanyId(comFollow.getCompany().getId());
        dto.setUserId(comFollow.getUser().getId());
        dto.setCreatedAt(comFollow.getCreatedAt());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dto);
    }

    @DeleteMapping("/companies/follow/{id}")
    @ApiMessage("Unfollow a company by id")
    public ResponseEntity<Void> unfollowCompany(@PathVariable("id") long id) throws IdInvalidException {
        this.companyFollowService.deleteFollowCompany(id);
        return ResponseEntity.ok().body(null);

    }

    @GetMapping("/companies/follow/{id}")
    @ApiMessage("Get all company follows")
    public ResponseEntity<?> getAllCompanyFollows(@PathVariable("id") long id) throws IdInvalidException {

        List<Company> company = companyFollowService.getCompanyFollowedByUserId(id);
        return ResponseEntity.ok(company);
    }
}
