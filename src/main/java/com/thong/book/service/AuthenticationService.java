package com.thong.book.service;

import com.thong.book.dto.AuthenticationRequest;
import com.thong.book.dto.AuthenticationResponse;
import com.thong.book.entities.User;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception;
    User register(User user);
}

