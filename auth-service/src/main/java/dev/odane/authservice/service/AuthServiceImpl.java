package dev.odane.authservice.service;

import dev.odane.authservice.config.service.JwtService;
import dev.odane.authservice.dtos.AuthenticationRequest;
import dev.odane.authservice.dtos.AuthenticationResponse;
import dev.odane.authservice.dtos.RegisterRequest;
import dev.odane.authservice.model.Admin;
import dev.odane.authservice.model.Member;
import dev.odane.authservice.model.MemberStatus;
import dev.odane.authservice.model.Role;
import dev.odane.authservice.repository.AdminRepository;
import dev.odane.authservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        logger.info("Registering admin: {}", request.getEmail());

        var admin = Admin.builder()
                .firstname(request.getFirstname())
                .dob(request.getDob())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .phone(request.getPhone())
                .gender(request.getGender())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();

        adminRepository.save(admin);

        var jwtToken = jwtService.generateToken(admin);
        logger.info("Admin registered successfully: {}", request.getEmail());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse memberRegistration(RegisterRequest request) {
        logger.info("Registering member: {}", request.getEmail());
        var member = Member.builder()
                .name(request.getFirstname() + " " + request.getLastname())
                .email(request.getEmail())
                .dob(request.getDob())
                .password(passwordEncoder.encode(request.getPassword()))
                .status(MemberStatus.ACTIVE)
                .gender(request.getGender())
                .phone(request.getPhone())
                .role(Role.MEMBER)
                .borrowedBooks(new ArrayList<>())
                .build();

        memberRepository.save(member);

        var jwtToken = jwtService.generateToken(member);
        logger.info("Member registered successfully: {}", request.getEmail());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse memberLogin(AuthenticationRequest request) {
        logger.info("Member login attempt: {}", request.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    logger.error("Member not found: {}", request.getEmail());
                    return new UsernameNotFoundException("User not found");
                });

        var jwtToken = jwtService.generateToken(user);
        logger.info("Member login successful: {}", request.getEmail());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        logger.info("Admin login attempt: {}", request.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    logger.error("Admin not found: {}", request.getEmail());
                    return new UsernameNotFoundException("User not found");
                });

        var jwtToken = jwtService.generateToken(user);
        logger.info("Admin login successful: {}", request.getEmail());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
