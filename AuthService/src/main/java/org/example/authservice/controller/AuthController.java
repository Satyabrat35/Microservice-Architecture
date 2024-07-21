package org.example.authservice.controller;

import org.example.authservice.model.Account;
import org.example.authservice.model.LoginRequest;
import org.example.authservice.model.LoginResponse;
import org.example.authservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    public AuthController( UserDetailsService userDetailsService, AuthenticationManager authenticationManager, AuthService authService ) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.authService = authService;
    }

    @PostMapping("/register")
    public Account createAmsAccount(@RequestBody Account account) throws Exception {
        return authService.saveAccount(account);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        String token = "";
        if (authenticate.isAuthenticated()) {
            token = authService.generateToken(loginRequest.getUsername());
        } else {
            throw new RuntimeException("invalid access");
        }
        LoginResponse resp = LoginResponse.builder()
                .token(token)
                .userName(loginRequest.getUsername())
                .build();
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        try{
            authService.validateToken(token);
        } catch(Exception e){
            throw new BadCredentialsException("invalid token");
        }
        return "Token validated";
    }

}
