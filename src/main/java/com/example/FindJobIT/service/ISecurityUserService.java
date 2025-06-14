package com.example.FindJobIT.service;

public interface ISecurityUserService {
    String validatePasswordResetToken(long id, String token);

}