package com.dama.backend.dama.auth;

import com.dama.backend.dama.user.User;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dama.backend.dama.model.Role;
import com.dama.backend.dama.repository.UserRepository;
import com.dama.backend.dama.service.JwtService;
import com.dama.backend.dama.repository.RoleRepository;
import com.dama.backend.dama.repository.TokenRepository;
import com.dama.backend.dama.model.Token;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    
    public AuthenticationResponse register(RegisterRequest request)
    {
        Role defaultRole = roleRepository.findById(2)
                                        .orElseThrow(() -> new RuntimeException("Role with ID 2 not found"));
        var user = User.builder()
        .name(request.getName())
        .surname(request.getSurname())
        .email(request.getEmail())
        .username(request.getUsername())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(defaultRole)
        .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
        .accessToken(jwtToken)
        .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request)
    {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = repository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken= jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder().accessToken(jwtToken).build();
    }
    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
            .user(user)
            .token(jwtToken)
            .expired(false)
            .revoked(false)
            .build();
        tokenRepository.save(token);
    }
      private void revokeAllUserTokens(User user) {
            var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
            if (validUserTokens.isEmpty())
            return;
            validUserTokens.forEach(token -> {
                token.setExpired(true);
                token.setRevoked(true);
                });
            tokenRepository.saveAll(validUserTokens);
        }
    
}
