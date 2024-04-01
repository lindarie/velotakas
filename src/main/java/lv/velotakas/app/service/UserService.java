package lv.velotakas.app.service;

import lv.velotakas.app.dto.request.AuthenticationRequest;
import lv.velotakas.app.dto.request.RegisterRequest;
import lv.velotakas.app.dto.response.AuthenticationResponse;
import lv.velotakas.app.dto.response.UserDAO;
import lv.velotakas.app.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    Boolean isTokenValid(String token);
    Optional<List<UserDAO>> findAllUsers();

    boolean userExistsById(Integer id);

    UserDAO findUserById(Integer id);

    boolean doesUserExist(RegisterRequest request);
    boolean doesUserExist(UserDAO userDAO);

    void updateUser(UserDAO userDAO);

    void deleteUserById(Integer id);


}
