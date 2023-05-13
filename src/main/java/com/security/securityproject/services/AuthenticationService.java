package com.security.securityproject.services;

import com.security.securityproject.config.JwtService;
import com.security.securityproject.dao.UserRepository;
import com.security.securityproject.dto.AuthenticationRequest;
import com.security.securityproject.dto.AuthenticationResponse;
import com.security.securityproject.dto.RegisterRequest;
import com.security.securityproject.entities.Role;
import com.security.securityproject.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var newUser = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(newUser);
        var jwtToken = jwtService.generateToken(newUser);

        return response(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var currentUser = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(currentUser);

        return response(jwtToken);
    }

    private AuthenticationResponse response(String jwtToken){
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
