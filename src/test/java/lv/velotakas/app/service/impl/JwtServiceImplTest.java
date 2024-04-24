package lv.velotakas.app.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import lv.velotakas.app.models.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

class JwtServiceImplTest {

    private JwtServiceImpl jwtService;

    private static final String USERNAME = "user@example.com";
    private static final String INVALID_USERNAME = "invalid@example.com";
    private static final String ROLE_AUTHENTICATED = "ROLE_AUTHENTICATED";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    @BeforeEach
    void setUp() {
        jwtService = new JwtServiceImpl();
    }

    @Test
    void testGenerateTokenWithRoleAuthenticated() {
        UserDetails userDetails = new User(USERNAME, "password", Collections.singletonList(new SimpleGrantedAuthority(Role.AUTHENTICATED.name())));
        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);

        String role = jwtService.extractClaim(token, claims -> claims.get("role", String.class));
        assertEquals("authenticated", role);
    }

    @Test
    void testGenerateTokenWithRoleAdmin() {
        UserDetails userDetails = new User(USERNAME, "password", Collections.singletonList(new SimpleGrantedAuthority(ROLE_ADMIN)));
        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);

        String role = jwtService.extractClaim(token, claims -> claims.get("role", String.class));
        assertEquals("admin", role);
    }

    @Test
    void testTokenValidityForValidUser() {
        UserDetails userDetails = new User(USERNAME, "password", Collections.singletonList(new SimpleGrantedAuthority(ROLE_AUTHENTICATED)));
        String token = jwtService.generateToken(userDetails);

        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void testTokenValidityForInvalidUser() {
        UserDetails validUserDetails = new User(USERNAME, "password", Collections.singletonList(new SimpleGrantedAuthority(ROLE_AUTHENTICATED)));
        UserDetails invalidUserDetails = new User(INVALID_USERNAME, "password", Collections.singletonList(new SimpleGrantedAuthority(ROLE_AUTHENTICATED)));
        String token = jwtService.generateToken(validUserDetails);

        assertFalse(jwtService.isTokenValid(token, invalidUserDetails));
    }

    @Test
    void testTokenExpiration() {
        UserDetails userDetails = new User(USERNAME, "password", Collections.singletonList(new SimpleGrantedAuthority(ROLE_AUTHENTICATED)));
        String token = jwtService.generateToken(userDetails);

        try {
            Thread.sleep(1001);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertFalse(jwtService.isTokenExpired(token));
    }

    @Test
    void testExtractEmail() {
        UserDetails userDetails = new User(USERNAME, "password", Collections.singletonList(new SimpleGrantedAuthority(ROLE_AUTHENTICATED)));
        String token = jwtService.generateToken(userDetails);

        assertEquals(USERNAME, jwtService.extractEmail(token));
    }
}
