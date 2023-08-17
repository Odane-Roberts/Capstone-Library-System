package dev.odane.capstoneproject.controller;

import dev.odane.capstoneproject.DTOs.AuthenticationRequest;
import dev.odane.capstoneproject.DTOs.AuthenticationResponse;
import dev.odane.capstoneproject.DTOs.RegisterRequest;
import dev.odane.capstoneproject.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor

public class AuthController {

    private final AuthService authService;

    @PostMapping("/admin/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
       return ResponseEntity.ok(authService.register(request));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerMember(@RequestBody RegisterRequest member) {
        return ResponseEntity.ok(authService.memberRegistration(member));
    }

    @PostMapping("/admin/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> memberLogin(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authService.memberLogin(request));
    }

}
