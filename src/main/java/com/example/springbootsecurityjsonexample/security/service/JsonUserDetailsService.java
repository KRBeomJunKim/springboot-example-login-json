package com.example.springbootsecurityjsonexample.security.service;

import com.example.springbootsecurityjsonexample.domain.entity.Account;
import com.example.springbootsecurityjsonexample.security.exception.IllegalUsernamePassword;
import com.example.springbootsecurityjsonexample.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;

@Component
@RequiredArgsConstructor
@Transactional
public class JsonUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalUsernamePassword("Username or Password is not Correct"));

        LinkedList<GrantedAuthority> authorities = new LinkedList<>();
        authorities.add(new SimpleGrantedAuthority(account.getRole()));

        return new AccountDetails(account, authorities);
    }
}
