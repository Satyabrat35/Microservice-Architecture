package org.example.authservice.service;

import org.example.authservice.model.Account;
import org.example.authservice.repository.AccountRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AccountRepo accountRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(AccountRepo accountRepo, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.accountRepo = accountRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public Account saveAccount(Account account) throws Exception{
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepo.save(account);
    }
    public String generateToken(String userName) {
        return jwtService.generateToken(userName);
    }
    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

}
