package com.example.springbootsecurityjsonexample.domain.repository;

import com.example.springbootsecurityjsonexample.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUsername(String username);
}
