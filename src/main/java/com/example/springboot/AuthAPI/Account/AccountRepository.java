package com.example.springboot.AuthAPI.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, String> {
    Account findByUsername(String username);
}
