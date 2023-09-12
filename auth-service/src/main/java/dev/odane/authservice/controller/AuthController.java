package dev.odane.authservice.controller;


import dev.odane.authservice.dtos.AuthenticationRequest;
import dev.odane.authservice.dtos.AuthenticationResponse;
import dev.odane.authservice.dtos.RegisterRequest;
import dev.odane.authservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    @PostMapping("/admin/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest request) {
        logger.info("Admin registration request received");
        AuthenticationResponse response = authService.register(request);
        logger.info("Admin registration successful for email: {}", request.getEmail());
        return ResponseEntity.ok(response);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerMember(@RequestBody @Valid RegisterRequest member) {
        logger.info("Member registration request received");
        AuthenticationResponse response = authService.memberRegistration(member);
        logger.info("Member registration successful for email: {}", member.getEmail());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest request) {
        logger.info("Admin login request received for email: {}", request.getEmail());
        AuthenticationResponse response = authService.login(request);
        logger.info("Admin login successful for email: {}", request.getEmail());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> memberLogin(@RequestBody @Valid AuthenticationRequest request) {
        logger.info("Member login request received for email: {}", request.getEmail());
        AuthenticationResponse response = authService.memberLogin(request);
        logger.info("Member login successful for email: {}", request.getEmail());
        return ResponseEntity.ok(response);
    }
}
