package com.example.springbootsecurityjsonexample.domain.service;

import com.example.springbootsecurityjsonexample.domain.entity.Account;
import com.example.springbootsecurityjsonexample.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public Account save(Account account) {
        return accountRepository.save(account);
    }
}
