package dev.odane.capstoneproject.service;

import dev.odane.capstoneproject.DTOs.AuthenticationRequest;
import dev.odane.capstoneproject.DTOs.AuthenticationResponse;
import dev.odane.capstoneproject.DTOs.RegisterRequest;
import dev.odane.capstoneproject.config.service.JwtService;
import dev.odane.capstoneproject.model.Admin;
import dev.odane.capstoneproject.model.Member;
import dev.odane.capstoneproject.model.MemberStatus;
import dev.odane.capstoneproject.model.Role;
import dev.odane.capstoneproject.repository.AdminRepository;
import dev.odane.capstoneproject.repository.MemberRepository;
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
