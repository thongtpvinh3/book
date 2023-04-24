package com.thong.book.service.impl;

import com.thong.book.common.JwtUtil;
import com.thong.book.dto.AuthenticationRequest;
import com.thong.book.dto.AuthenticationResponse;
import com.thong.book.entities.Role;
import com.thong.book.entities.User;
import com.thong.book.repository.UserRepository;
import com.thong.book.service.AuthenticationService;
import com.thong.book.service.MailService;
import com.thong.book.service.UserServiceSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final MailService mailService;
    private final UserServiceSecurity userServiceSecurity;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            var user = userServiceSecurity.loadUserByUsername(request.getUsername());
            var jwtToken = jwtUtil.generateToken(user);
            return AuthenticationResponse.builder()
                    .username(request.getUsername())
                    .token(jwtToken)
                    .build();
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid username or password!", e);
        }
    }

    @Override
    public User register(User user) {
        user.setRole(Role.EMPLOYEE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        mailService.sendActiveUserMail(user.getEmail());
        return userRepository.save(user);
    }
}