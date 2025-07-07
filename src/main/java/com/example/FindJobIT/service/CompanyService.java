package com.example.FindJobIT.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.example.FindJobIT.domain.Company;
import com.example.FindJobIT.domain.User;
import com.example.FindJobIT.domain.response.ResultPaginationDTO;
import com.example.FindJobIT.repository.CompanyRepository;
import com.example.FindJobIT.repository.UserRepository;

@Service
public class CompanyService {

    public final CompanyRepository companyRepository;
    public final UserRepository userRepository;

    public CompanyService(CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    public Company handleCreateCompany(Company company) {
        return this.companyRepository.save(company);
    }

    public ResultPaginationDTO handleGetCompany(Specification<Company> specification, Pageable pageable) {
        Page<Company> PageCompany = this.companyRepository.findAll(specification, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();

        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());

        meta.setPages(PageCompany.getTotalPages());
        meta.setTotal(PageCompany.getTotalElements());

        rs.setMeta(meta);
        rs.setResult(PageCompany.getContent());

        return rs;

    }

    public Company handleUpdateCompany(Company company) {
        Optional<Company> companyOptional = this.companyRepository.findById(company.getId());
        if (companyOptional.isPresent()) {
            Company currentCompany = companyOptional.get();
            currentCompany.setLogo(company.getLogo());
            currentCompany.setBanner(company.getBanner());
            currentCompany.setName(company.getName());
            currentCompany.setDescription(company.getDescription());
            currentCompany.setAddress(company.getAddress());

            return this.companyRepository.save(currentCompany);
        }
        return null;
    }

    public List<Company> getCompaniesWithMostLikes(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<Company> companies = companyRepository.findAllByOrderByLikeCountDesc(pageable);
        return companies;
    }

    public void handleLikeCompany(long id) {
        Optional<Company> comOptional = this.companyRepository.findById(id);
        if (comOptional.isPresent()) {
            Company com = comOptional.get();
            com.setLikeCount(com.getLikeCount() + 1);
            this.companyRepository.save(com);
        }
    }

    public void handleDisLikeCompany(long id) {
        Optional<Company> comOptional = this.companyRepository.findById(id);
        if (comOptional.isPresent()) {
            Company com = comOptional.get();
            if (com.getLikeCount() <= 0) {
                return; // prevent like count from going negative
            }
            com.setLikeCount(com.getLikeCount() - 1);
            this.companyRepository.save(com);
        }
    }

    public void handleDeleteCompany(long id) {
        Optional<Company> comOptional = this.companyRepository.findById(id);
        if (comOptional.isPresent()) {
            Company com = comOptional.get();
            // fetch all user belong to this company

            List<User> users = this.userRepository.findByCompany(com);
            this.userRepository.deleteAll(users);
        }

        this.companyRepository.deleteById(id);
    }

    public Optional<Company> findById(long id) {
        return this.companyRepository.findById(id);
    }

}
