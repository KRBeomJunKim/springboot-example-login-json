package com.example.springbootsecurityjsonexample.security.config;

import com.example.springbootsecurityjsonexample.security.entrypoint.JsonAuthenticationEntryPoint;
import com.example.springbootsecurityjsonexample.security.filter.JsonAuthenticationFilter;
import com.example.springbootsecurityjsonexample.security.handler.JsonAccessDeniedHandler;
import com.example.springbootsecurityjsonexample.security.handler.JsonAuthenticationFailureHandler;
import com.example.springbootsecurityjsonexample.security.handler.JsonAuthenticationSuccessHandler;
import com.example.springbootsecurityjsonexample.security.handler.JsonLogoutSuccessHandler;
import com.example.springbootsecurityjsonexample.security.provider.JsonAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JsonAuthenticationProvider jsonAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(jsonAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/sign-up").permitAll()
                .antMatchers("/no").hasRole("MANAGER")
                .anyRequest().authenticated();

        http
                .addFilterBefore(jsonAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http
                .csrf().disable();

        http
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(new JsonLogoutSuccessHandler())
                .permitAll();

        http
                .exceptionHandling()
                .authenticationEntryPoint(new JsonAuthenticationEntryPoint())
                .accessDeniedHandler(new JsonAccessDeniedHandler());
    }

    @Bean
    public JsonAuthenticationFilter jsonAuthenticationFilter() throws Exception {
        JsonAuthenticationFilter jsonAuthenticationFilter = new JsonAuthenticationFilter();
        jsonAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        jsonAuthenticationFilter.setAuthenticationSuccessHandler(new JsonAuthenticationSuccessHandler());
        jsonAuthenticationFilter.setAuthenticationFailureHandler(new JsonAuthenticationFailureHandler());
        return jsonAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
