package dev.odane.capstoneproject.service;

import dev.odane.capstoneproject.DTOs.AuthenticationRequest;
import dev.odane.capstoneproject.DTOs.AuthenticationResponse;
import dev.odane.capstoneproject.DTOs.RegisterRequest;



public interface AuthService {

    AuthenticationResponse login(AuthenticationRequest request);
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse memberRegistration(RegisterRequest request);
    AuthenticationResponse memberLogin(AuthenticationRequest request);


}
