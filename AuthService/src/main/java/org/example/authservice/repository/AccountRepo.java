package org.example.authservice.repository;

import org.example.authservice.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends CrudRepository<Account, Integer> {
    public Account findByUsername(String username);
}
