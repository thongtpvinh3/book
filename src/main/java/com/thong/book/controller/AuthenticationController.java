package com.thong.book.controller;

import com.thong.book.common.ResponseObject;
import com.thong.book.dto.AuthenticationRequest;
import com.thong.book.entities.User;
import com.thong.book.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> authentication(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return ResponseEntity.ok(new ResponseObject("Log in success!", HttpStatus.OK, authenticationService.authenticate(authenticationRequest)));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return ResponseEntity.ok(new ResponseObject("Register success", HttpStatus.OK, authenticationService.register(user)));
    }
}
