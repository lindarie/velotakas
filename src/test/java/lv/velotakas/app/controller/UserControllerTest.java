package lv.velotakas.app.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import lv.velotakas.app.dto.request.user.AuthenticationRequest;
import lv.velotakas.app.dto.request.user.RegisterRequest;
import lv.velotakas.app.dto.request.user.UpdateUserRequest;
import lv.velotakas.app.dto.response.AuthenticationResponse;
import lv.velotakas.app.dto.response.UserDAO;
import lv.velotakas.app.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testRegisterUser() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("John", "Doe", LocalDate.now(), "Description", "john@example.com", "password", "filePath");
        AuthenticationResponse expectedResponse = new AuthenticationResponse("sampleToken", "john@example.com");

        when(userService.register(registerRequest)).thenReturn(expectedResponse);

        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(expectedResponse)));
    }

    @Test
    public void testAuthenticateUser() throws Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("john@example.com", "password");
        AuthenticationResponse expectedResponse = new AuthenticationResponse("sampleToken", "john@example.com");

        when(userService.authenticate(authenticationRequest)).thenReturn(expectedResponse);

        mockMvc.perform(post("/api/user/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(authenticationRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(expectedResponse)));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<UserDAO> userList = Arrays.asList(
                new UserDAO(1, "John", "Doe", "1990-01-01", "john@example.com", "Description", "filePath"),
                new UserDAO(2, "Jane", "Doe", "1992-03-15", "jane@example.com", "Description", "filePath")
        );

        when(userService.findAllUsers()).thenReturn(userList);

        mockMvc.perform(get("/api/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(userList)));
    }

    @Test
    public void testGetUserById() throws Exception {
        int userId = 1;
        UserDAO userDAO = new UserDAO(userId, "John", "Doe", "1990-01-01", "john@example.com", "Description", "filePath");

        when(userService.userExistsById(userId)).thenReturn(true);
        when(userService.findUserById(userId)).thenReturn(userDAO);

        mockMvc.perform(get("/api/user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(userDAO)));
    }

    @Test
    public void testEditUser_NotFound() throws Exception {
        int userId = 1;
        UpdateUserRequest updateUserRequest = new UpdateUserRequest("John", "Doe", LocalDate.of(1990, 1, 1), "Description", "john@example.com", "password", true, "filePath");

        mockMvc.perform(put("/api/user/edit/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updateUserRequest)))
                .andExpect(status().isNotFound());

    }

    @Test
    public void testEditUser_BadRequest() throws Exception {
        int userId = 1;
        UpdateUserRequest updateUserRequest = new UpdateUserRequest("John", "Doe", LocalDate.of(1990, 1, 1), "Description", "john@example.com", "password", true, "filePath");
        when(userService.userExistsById(1)).thenReturn(true);
        mockMvc.perform(put("/api/user/edit/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updateUserRequest)))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void testEditUser_Successful() throws Exception {
        int userId = 1;
        UpdateUserRequest updateUserRequest = new UpdateUserRequest("John", "Doe", LocalDate.of(1990, 1, 1), "Description", "john@example.com", "password", false, "filePath");
        when(userService.userExistsById(1)).thenReturn(true);
        mockMvc.perform(put("/api/user/edit/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updateUserRequest)))
                .andExpect(status().isCreated());

        verify(userService).updateUser(updateUserRequest, userId);

    }

    @Test
    public void testDeleteUser() throws Exception {
        int userId = 1;

        when(userService.userExistsById(userId)).thenReturn(true);

        mockMvc.perform(delete("/api/user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService).deleteUserById(userId);
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
