package lv.velotakas.app.service;

import lv.velotakas.app.dto.request.user.AuthenticationRequest;
import lv.velotakas.app.dto.request.user.RegisterRequest;
import lv.velotakas.app.dto.request.user.UpdateUserRequest;
import lv.velotakas.app.dto.response.AuthenticationResponse;
import lv.velotakas.app.dto.response.UserDAO;

import java.util.List;

public interface UserService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    Boolean isTokenValid(String token);
    List<UserDAO> findAllUsers();

    boolean userExistsById(Integer id);

    UserDAO findUserById(Integer id);
    boolean doesUserExistByEmail(String email);

    void updateUser(UpdateUserRequest updateUserRequest, Integer id);

    void deleteUserById(Integer id);


}
