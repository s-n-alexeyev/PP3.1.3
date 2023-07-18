package ru.itmentor.spring.boot_security.demo.controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmentor.spring.boot_security.demo.configs.UserDetailsConfig;
import ru.itmentor.spring.boot_security.demo.dtos.JwtRequest;
import ru.itmentor.spring.boot_security.demo.dtos.JwtResponse;
import ru.itmentor.spring.boot_security.demo.dtos.AppMessage;
import ru.itmentor.spring.boot_security.demo.utils.JwtTokenUtils;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class Auth {
    private final UserDetailsConfig userDetailService;
    private final JwtTokenUtils jwtTokenUtils;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate((new UsernamePasswordAuthenticationToken
                    (authRequest.getUsername(), authRequest.getPassword())));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AppMessage(HttpStatus.UNAUTHORIZED.value(), "Неверный пользователь или пароль"));
        }

        UserDetails userDetails = userDetailService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

}