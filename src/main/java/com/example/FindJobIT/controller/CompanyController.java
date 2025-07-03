package com.example.FindJobIT.controller;

import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import com.example.FindJobIT.domain.Company;
import com.example.FindJobIT.domain.User;
import com.example.FindJobIT.domain.response.ResultPaginationDTO;
import com.example.FindJobIT.service.CompanyService;
import com.example.FindJobIT.service.JobFollowService;
import com.example.FindJobIT.util.annotation.ApiMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1")
public class CompanyController {
    private final CompanyService companyService;
    private final JobFollowService jobFollowService;

    public CompanyController(CompanyService companyService, JobFollowService jobFollowService) {
        this.companyService = companyService;
        this.jobFollowService = jobFollowService;
    }

    @PostMapping("/companies")
    public ResponseEntity<?> createCompany(@Valid @RequestBody Company reqCompany) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.companyService.handleCreateCompany(reqCompany));
    }

    @GetMapping("/companies")
    public ResponseEntity<ResultPaginationDTO> getCompany(
            @Filter Specification<Company> spec,
            Pageable pageable
    // @RequestParam("current") Optional<String> currentOptional,
    // @RequestParam("pageSize") Optional<String> pageSizeOptional
    ) {

        // String sCurrent = currentOptional.isPresent() ? currentOptional.get() : "";
        // String sPageSize = pageSizeOptional.isPresent() ? pageSizeOptional.get() :
        // "";

        // int current = Integer.parseInt(sCurrent);
        // int pageSize = Integer.parseInt(sPageSize);
        // Pageable pageable = PageRequest.of(current - 1, pageSize);

        ResultPaginationDTO companies = this.companyService.handleGetCompany(spec, pageable);

        return ResponseEntity.ok(companies);
    }

    @PutMapping("/companies")
    public ResponseEntity<Company> updateCompany(@Valid @RequestBody Company reqCompany) {
        Company updateCompany = this.companyService.handleUpdateCompany(reqCompany);

        return ResponseEntity.ok(updateCompany);
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable("id") long id) {
        this.companyService.handleDeleteCompany(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/companies/{id}")
    @ApiMessage("fetch company by id")
    public ResponseEntity<Company> fetchCompanyById(@PathVariable("id") long id) {
        Optional<Company> cOptional = this.companyService.findById(id);
        return ResponseEntity.ok().body(cOptional.get());
    }

    @PutMapping("/companies/{id}/like")
    @ApiMessage("like company")
    public ResponseEntity<Void> likeCompany(@PathVariable("id") long id) {
        this.companyService.handleLikeCompany(id);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/companies/{id}/dislike")
    @ApiMessage("like company")
    public ResponseEntity<Void> disLikeCompany(@PathVariable("id") long id) {
        this.companyService.handleDisLikeCompany(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("companies/likest")
    @ApiMessage("get companies with most likes")
    public ResponseEntity<List<Company>> getCompaniesWithMostLikes() {
        List<Company> companies = this.companyService.getCompaniesWithMostLikes(4);
        return ResponseEntity.ok(companies);
    }

}
