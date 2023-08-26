package dev.odane.capstoneproject.config;

import dev.odane.capstoneproject.config.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.mockito.Mockito.mock;

 class JwtAuthenticationFilterTest {

    private JwtService jwtService;
    private UserDetailsService userDetailsService;
    private JwtAuthenticationFilter filter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        jwtService = mock(JwtService.class);
        userDetailsService = mock(UserDetailsService.class);
        filter = new JwtAuthenticationFilter(jwtService, userDetailsService);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
    }

//    @Test
//    void testDoFilterInternal_ValidToken() throws ServletException, IOException {
//        // Arrange
//        String jwtToken = "validJwtToken";
//        String userEmail = "user@example.com";
//
//        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
//        when(jwtService.extractUserEmail(jwtToken)).thenReturn(userEmail);
//
//        UserDetails userDetails = new User(
//                userEmail,
//                "password",
//                Collections.emptyList()
//        );
//        when(userDetailsService.loadUserByUsername(userEmail)).thenReturn(userDetails);
//
//        when(jwtService.isTokenValid(jwtToken, userDetails)).thenReturn(true);
//
//        // Act
//        filter.doFilterInternal(request, response, filterChain);
//
//        // Assert
//        verify(userDetailsService, times(1)).loadUserByUsername(userEmail);
//        verify(jwtService, times(1)).isTokenValid(jwtToken, userDetails);
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        assertNotNull(authentication);
//        assertTrue(authentication instanceof UsernamePasswordAuthenticationToken);
//        assertEquals(authentication.getPrincipal(), userDetails);
//
//        verify(filterChain, times(1)).doFilter(request, response);
//    }

    // Add more test methods for other scenarios, such as invalid tokens, missing headers, etc.
}
