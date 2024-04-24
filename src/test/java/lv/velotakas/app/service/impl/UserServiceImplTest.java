package lv.velotakas.app.service.impl;

import lv.velotakas.app.dto.request.user.AuthenticationRequest;
import lv.velotakas.app.dto.request.user.RegisterRequest;
import lv.velotakas.app.dto.request.user.UpdateUserRequest;
import lv.velotakas.app.dto.response.AuthenticationResponse;
import lv.velotakas.app.dto.response.UserDAO;
import lv.velotakas.app.mapper.UserMapper;
import lv.velotakas.app.models.User;
import lv.velotakas.app.models.enums.Role;
import lv.velotakas.app.repositories.UserRepository;
import lv.velotakas.app.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        RegisterRequest registerRequest = new RegisterRequest("Ralfs", "Laksa", null, "description", "email", "password");
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setSurname(registerRequest.getSurname());
        user.setEncryptedPassword("encodedPassword");
        user.setRole(Role.AUTHENTICATED);
        user.setTwoFactorAuth(false);

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(user);
        when(jwtService.generateToken(any())).thenReturn("jwtToken");

        AuthenticationResponse authenticationResponse = userService.register(registerRequest);

        assertNotNull(authenticationResponse);
        assertNotNull(authenticationResponse.getToken());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void testAuthenticate() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("test@test.com", "password");
        User user = new User();
        user.setEmail(authenticationRequest.getEmail());
        user.setEncryptedPassword("encodedPassword");

        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userRepository.findByEmail(authenticationRequest.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any())).thenReturn("jwtToken");

        AuthenticationResponse authenticationResponse = userService.authenticate(authenticationRequest);

        assertNotNull(authenticationResponse);
        assertNotNull(authenticationResponse.getToken());
        verify(userRepository, times(1)).findByEmail(authenticationRequest.getEmail());
    }

    @Test
    void testIsTokenValid() {
        String token = "token";
        String userEmail = "test@test.com";
        UserDetails userDetails = mock(UserDetails.class);

        when(jwtService.extractEmail(token)).thenReturn(userEmail);
        when(userDetailsService.loadUserByUsername(userEmail)).thenReturn(userDetails);
        when(jwtService.isTokenValid(token, userDetails)).thenReturn(true);

        assertTrue(userService.isTokenValid(token));
    }

    @Test
    void testFindAllUsers() {
        List<User> userList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userList);
        when(userMapper.userToUserDAO(any())).thenReturn(new UserDAO());

        List<UserDAO> userDAOList = userService.findAllUsers();

        assertNotNull(userDAOList);
        assertEquals(userList.size(), userDAOList.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testUserExistsById() {
        Integer userId = 1;
        when(userRepository.existsById(userId)).thenReturn(true);

        assertTrue(userService.userExistsById(userId));
        verify(userRepository, times(1)).existsById(userId);
    }

    @Test
    void testFindUserById() {
        Integer userId = 1;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.userToUserDAO(user)).thenReturn(new UserDAO());

        UserDAO userDAO = userService.findUserById(userId);

        assertNotNull(userDAO);
        assertEquals(userId, userDAO.getId());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testDoesUserExistByEmail() {
        String email = "test@test.com";
        when(userRepository.existsByEmail(email)).thenReturn(true);

        assertTrue(userService.doesUserExistByEmail(email));
        verify(userRepository, times(1)).existsByEmail(email);
    }

    @Test
    void testUpdateUser() {
        Integer userId = 1;
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setName("changedName");
        User user = new User();
        user.setId(1);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        userService.updateUser(updateUserRequest, userId);

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testDeleteUserById() {
        Integer userId = 1;

        userService.deleteUserById(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }
}
