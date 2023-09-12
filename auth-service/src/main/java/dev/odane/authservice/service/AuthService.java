package dev.odane.authservice.service;


import dev.odane.authservice.dtos.AuthenticationRequest;
import dev.odane.authservice.dtos.AuthenticationResponse;
import dev.odane.authservice.dtos.RegisterRequest;

public interface AuthService {

    AuthenticationResponse login(AuthenticationRequest request);
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse memberRegistration(RegisterRequest request);
    AuthenticationResponse memberLogin(AuthenticationRequest request);


}
