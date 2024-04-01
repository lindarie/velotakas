package lv.velotakas.app.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lv.velotakas.app.dto.request.AuthenticationRequest;
import lv.velotakas.app.dto.request.RegisterRequest;
import lv.velotakas.app.dto.response.AuthenticationResponse;
import lv.velotakas.app.dto.response.UserDAO;
import lv.velotakas.app.mapper.UserMapper;
import lv.velotakas.app.models.User;
import lv.velotakas.app.models.enums.Role;
import lv.velotakas.app.repositories.UserRepository;
import lv.velotakas.app.service.JwtService;
import lv.velotakas.app.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {
    private UserMapper mapper;
    private final UserRepository repository;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .email(request.getEmail())
                .surname(request.getSurname())
                .encryptedPassword(passwordEncoder.encode(request.getPassword()))
                .birthDate(request.getBirthDate())
                .description(request.getDescription())
                .role(Role.AUTHENTICATED)
                .twoFactorAuth(false)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.info(String.valueOf(request));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        log.info("User Service: JWT token " + jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    @Override
    public Boolean isTokenValid(String token) {
        String userEmail = jwtService.extractEmail(token);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
        return jwtService.isTokenValid(token,userDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<UserDAO>> findAllUsers() {
        List<User> users = repository.findAll();
        List<UserDAO> usersDAOs = users.stream()
                .map(mapper::userToUserDAO)
                .collect(Collectors.toList());
        return Optional.of(usersDAOs);
    }
    @Override
    public boolean userExistsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public UserDAO findUserById(Integer id) {
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException("User with ID "+ id + " not found"));
        return mapper.userToUserDAO(user);
    }

    @Override
    public boolean doesUserExist(RegisterRequest request) {
        String email = request.getEmail();
        return repository.existsByEmail(email);
    }

    @Override
    public boolean doesUserExist(UserDAO user) {
        String email = user.getEmail();
        return repository.existsByEmail(email);
    }

    @Override
    public void updateUser(UserDAO userDao) {
        User user = repository.findById(userDao.getId()).orElseThrow();
        mapper.updateUserFromUserDAO(userDao, user);
    }

    @Override
    public void deleteUserById(Integer id) {
    repository.deleteById(id);
    }

}
