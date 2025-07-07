package com.dama.backend.dama.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.dama.backend.dama.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService(){
        return (String username) -> userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("user not found"));
    }
}
