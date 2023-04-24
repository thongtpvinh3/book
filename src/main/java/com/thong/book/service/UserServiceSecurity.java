package com.thong.book.service;

import com.thong.book.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserServiceSecurity implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.thong.book.entities.User user = userRepository.findByUsername(username);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pass = encoder.encode(user.getPassword());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), pass, user.getAuthorities());
    }
}
