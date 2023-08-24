package dev.odane.capstoneproject.config;

import dev.odane.capstoneproject.config.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService; // Service for JWT-related operations
    private final UserDetailsService userDetailsService; // Service to load user details

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization"); // Get the authorization header
        final String jwtToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // If no authorization header or does not start with "Bearer ", pass the request along the filter chain
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authHeader.substring(7); // Extract the JWT token (remove "Bearer " prefix.)
        userEmail = jwtService.extractUserEmail(jwtToken); // Extract user email from JWT token

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Check if user is authenticated and there is no existing authentication context

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail); // Load user details by email

            if (jwtService.isTokenValid(jwtToken, userDetails)) {
                // Check if the JWT token is valid for the user

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken); // Set authentication in the security context
            }
        }

        filterChain.doFilter(request, response); // Continue the filter chain
    }
}
