package com.study.springstudycrud.service;

import com.study.springstudycrud.dto.CustomUserDetails;
import com.study.springstudycrud.entity.User;
import com.study.springstudycrud.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userData = userRepository.findByUsername(username);

        if(userData != null) {
            return new CustomUserDetails(userData);
        }
        return null;
    }
}
