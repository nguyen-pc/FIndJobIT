package com.example.FindJobIT.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @DeleteMapping("/companies/follow")
    @ApiMessage("Unfollow a company by id")
    public ResponseEntity<Void> unfollowCompany(@RequestBody ReqFollowCompany reqFollowCompany)
            throws IdInvalidException {
        this.companyFollowService.deleteFollowCompany(reqFollowCompany.getCompanyId(), reqFollowCompany.getUserId());
        return ResponseEntity.ok().body(null);

    }

    @GetMapping("/companies/follow/{id}")
    @ApiMessage("Get all company follows")
    public ResponseEntity<?> getAllCompanyFollows(@PathVariable("id") long id) throws IdInvalidException {

        List<Company> company = companyFollowService.getCompanyFollowedByUserId(id);
        return ResponseEntity.ok(company);
    }

    @GetMapping("/companies/{companyId}/follow-status")
    @ApiMessage("Get follow status for a job by user")
    public ResponseEntity<Map<String, Boolean>> getFollowStatus(
            @PathVariable("companyId") long companyId,
            @RequestParam("userId") long userId) {
        boolean followed = companyFollowService.isCompanyFollowedByUser(companyId, userId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("followed", followed);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/companies/{companyId}/follow-count")
    @ApiMessage("Get the number of followers for a company")
    public ResponseEntity<Map<String, Long>> getCompanyFollowCount(@PathVariable("companyId") long companyId) {
        long count = companyFollowService.getFollowerCountForCompany(companyId);
        Map<String, Long> response = new HashMap<>();
        response.put("followerCount", count);
        return ResponseEntity.ok(response);
    }
}
