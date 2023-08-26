package dev.odane.capstoneproject.service;

import dev.odane.capstoneproject.DTOs.AuthenticationRequest;
import dev.odane.capstoneproject.DTOs.AuthenticationResponse;
import dev.odane.capstoneproject.DTOs.RegisterRequest;
import dev.odane.capstoneproject.config.service.JwtService;
import dev.odane.capstoneproject.model.Admin;
import dev.odane.capstoneproject.model.Gender;
import dev.odane.capstoneproject.model.Member;
import dev.odane.capstoneproject.repository.AdminRepository;
import dev.odane.capstoneproject.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        // Given
        RegisterRequest request = new RegisterRequest();
        request.setFirstname("John");
        request.setLastname("Doe");
        request.setEmail("john@example.com");
        request.setPhone("1234567890");
        request.setPassword("password");
        request.setGender(Gender.MALE);
        request.setDob(LocalDate.parse("2000-01-01"));

        Admin admin = new Admin();
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);
        when(jwtService.generateToken(admin)).thenReturn("jwtToken");

        String jwtToken = jwtService.generateToken(admin);
        // When
        AuthenticationResponse response = authService.register(request);

        // Then
        assertNotNull(response);
        jwtToken = response.getToken();
        assertEquals(jwtToken, response.getToken());

        verify(passwordEncoder, times(1)).encode("password");
        verify(adminRepository, times(1)).save(any(Admin.class));
        verify(jwtService, times(1)).generateToken(admin);
    }

    @Test
    void testMemberRegistration() {
        // Given
        RegisterRequest request = new RegisterRequest();
        request.setFirstname("Jane");
        request.setLastname("Doe");
        request.setEmail("jane@example.com");
        request.setPhone("9876543210");
        request.setPassword("password");
        request.setGender(Gender.FEMALE);
        request.setDob(LocalDate.parse("2001-02-03"));

        Member member = new Member();
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        when(jwtService.generateToken(member)).thenReturn("jwtToken");

        String jwtToken = jwtService.generateToken(member);

        // When
        AuthenticationResponse response = authService.memberRegistration(request);

        // Then
        assertNotNull(response);
        jwtToken = response.getToken();
        assertEquals(jwtToken, response.getToken());

        verify(passwordEncoder, times(1)).encode("password");
        verify(memberRepository, times(1)).save(any(Member.class));
        verify(jwtService, times(1)).generateToken(member);
    }

    @Test
    void testMemberLogin() {
        // Given
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("jane@example.com");
        request.setPassword("password");

        Member member = new Member();
        when(memberRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(member));
        when(jwtService.generateToken(member)).thenReturn("jwtToken");

        // When
        AuthenticationResponse response = authService.memberLogin(request);

        // Then
        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());

        verify(authenticationManager, times(1)).authenticate(any());
        verify(memberRepository, times(1)).findByEmail(request.getEmail());
        verify(jwtService, times(1)).generateToken(member);
    }

    @Test
    void testMemberLogin_UserNotFound() {
        // Given
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("jane@example.com");
        request.setPassword("password");

        when(memberRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        // When, Then
        assertThrows(UsernameNotFoundException.class, () -> authService.memberLogin(request));

        verify(authenticationManager, times(1)).authenticate(any());
        verify(memberRepository, times(1)).findByEmail(request.getEmail());
        verify(jwtService, never()).generateToken(any());
    }

    // You can similarly write tests for the 'login' method and other scenarios.
}
