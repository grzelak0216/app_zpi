package org.example.schroniskodlapsow.auth.controller;

import lombok.AllArgsConstructor;
import org.example.schroniskodlapsow.auth.request.RegisterRequest;
import org.example.schroniskodlapsow.auth.request.AuthenticationRequest;
import org.example.schroniskodlapsow.auth.response.AuthenticationResponse;
import org.example.schroniskodlapsow.auth.service.AuthenticationService;
import org.example.schroniskodlapsow.exception.UsernameTakenException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        try {
            return ResponseEntity.ok(service.register(request));
        } catch (UsernameTakenException e){
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/authenticate2")
    public ResponseEntity<AuthenticationResponse> authenticate2(
    ) {
        return ResponseEntity.ok(new AuthenticationResponse("com/example/katalogpsow/token"));
    }
}
