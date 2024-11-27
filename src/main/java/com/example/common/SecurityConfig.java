package com.example.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz -> authz
                .requestMatchers("/css/**", "/img/**", "/js/**").permitAll()
                .requestMatchers("/", "/toInsert", "/insert")
                .permitAll()
                .anyRequest().authenticated());

        http.formLogin(login -> login
                .loginProcessingUrl("/login")
                .loginPage("/")
                .defaultSuccessUrl("/employee/showList", true)
                .failureUrl("/?result=error")
                .usernameParameter("mailAddress")
                .passwordParameter("password")
                .permitAll());

        http.exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint((request, response, authException) -> {
                    response.sendRedirect("/?error=unauthorized");
                }));

        return http.build();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
