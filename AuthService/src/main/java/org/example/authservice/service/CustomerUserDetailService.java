package org.example.authservice.service;

import org.example.authservice.model.Account;
import org.example.authservice.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomerUserDetailService  implements UserDetailsService {
//    private final AccountRepo accountRepo;
//    public CustomerUserDetailService(AccountRepo accountRepo) {
//        this.accountRepo = accountRepo;
//    }
    @Autowired
    private AccountRepo accountRepo;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            Account account = accountRepo.findByUsername(username);
            String userName = account.getUsername();
            String password = account.getPassword();
            return new User(userName, password, new ArrayList<>());
        } catch (Exception e){
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
