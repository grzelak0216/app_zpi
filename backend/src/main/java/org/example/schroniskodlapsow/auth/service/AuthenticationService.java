package org.example.schroniskodlapsow.auth.service;

import lombok.AllArgsConstructor;
import org.example.schroniskodlapsow.auth.request.RegisterRequest;
import org.example.schroniskodlapsow.auth.request.AuthenticationRequest;
import org.example.schroniskodlapsow.auth.response.AuthenticationResponse;
import org.example.schroniskodlapsow.entity.token.Token;
import org.example.schroniskodlapsow.entity.user.MyUser;
import org.example.schroniskodlapsow.exception.UsernameTakenException;
import org.example.schroniskodlapsow.repository.token.TokenRepository;
import org.example.schroniskodlapsow.repository.user.MyUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final MyUserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) throws UsernameTakenException {
        if(repository.findByEmail(request.getEmail()).isPresent())
            throw new UsernameTakenException("Email is already being used by another account");
        var user = MyUser.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(new HashMap<>(), user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        MyUser myUser = repository.findByEmail(request.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(new HashMap<>(), myUser);
        saveUserToken(myUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    private void saveUserToken(MyUser myUser, String jwtToken) {
        var token = Token.builder()
                .token(jwtToken)
                .myUser(myUser)
                .build();
        tokenRepository.save(token);
    }
}

