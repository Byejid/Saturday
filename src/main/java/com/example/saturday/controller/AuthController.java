package com.example.saturday.controller;

import com.example.saturday.dto.LoginRequestDto;
import com.example.saturday.dto.LoginResponseDto;
import com.example.saturday.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthController(JwtService jwtService,AuthenticationManager authenticationManager){
        this.jwtService=jwtService;
        this.authenticationManager=authenticationManager;
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto){

        Authentication authenticationRequest= UsernamePasswordAuthenticationToken
                .unauthenticated(
                        requestDto.getEmail(),
                        requestDto.getPassword()
                );
        Authentication authentication=authenticationManager.authenticate(authenticationRequest);
        String token=jwtService.generateToken(authentication);
        return   ResponseEntity.ok(new LoginResponseDto(token));
    }
}
