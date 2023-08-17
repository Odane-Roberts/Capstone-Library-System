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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponse register(RegisterRequest request){
        var admin = Admin.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();

        adminRepository.save(admin);

        var jwtToken = jwtService.generateToken(admin);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse memberRegistration(RegisterRequest request) {
        var member = Member.builder()
                .name(request.getFirstname() +" " + request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .status(MemberStatus.ACTIVE)
                .gender(request.getGender())
                .phone(request.getPhone())
                .role(Role.MEMBER)
                .borrowedBooks(new ArrayList<>())
                .build();

        memberRepository.save(member);


        var jwtToken = jwtService.generateToken(member);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

    @Override
    public AuthenticationResponse memberLogin(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(" User not found ")); // probably used a custom exception
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(" User not found ")); // probably used a custom exception
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
