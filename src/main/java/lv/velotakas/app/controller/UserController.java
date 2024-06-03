package lv.velotakas.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lv.velotakas.app.dto.request.user.AuthenticationRequest;
import lv.velotakas.app.dto.request.user.RegisterRequest;
import lv.velotakas.app.dto.request.user.UpdateUserRequest;
import lv.velotakas.app.dto.response.AuthenticationResponse;
import lv.velotakas.app.dto.response.UserDAO;
import lv.velotakas.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {
    @Autowired
    UserService service;
    @PostMapping("/register")
    @Operation(summary = "Create a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created a user"),
            @ApiResponse(responseCode = "400", description = "Missed required parameters, parameters are not valid"),
            @ApiResponse(responseCode = "500", description = "Server error")})
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest request){
        if (service.doesUserExistByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    @Operation(summary = "Log in a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully logged in user"),
            @ApiResponse(responseCode = "400", description = "Missed required parameters, parameters are not valid"),
            @ApiResponse(responseCode = "500", description = "Server error")})
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }
    @GetMapping("/validateToken")
    public Boolean validateToken(@RequestParam("token") String token){
        return service.isTokenValid(token);
    }

    @GetMapping
    @Operation(summary = "Retrieve all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all users"),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    ResponseEntity<List<UserDAO>> getAllUsers() {
        return ResponseEntity.ok(service.findAllUsers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved a user"),
            @ApiResponse(responseCode = "400", description = "Missed required parameters, parameters are not valid"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    ResponseEntity<UserDAO> getUserById(@PathVariable Integer id) {
        if (service.userExistsById(id)) {
            UserDAO user = service.findUserById(id);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/edit/{id}")
    @Operation(summary = "Update a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully updated a user"),
            @ApiResponse(responseCode = "400", description = "Missed required parameters, parameters are not valid"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Server error" )})
    ResponseEntity editUser(@RequestBody UpdateUserRequest updateRequest,
                                  @PathVariable Integer id) {
        if (!service.userExistsById(id)) {
            log.warn("User controller: Not found user with ID " + id);

            return ResponseEntity.notFound().build();
        }
        if (updateRequest.isEmailChange() && !service.doesUserExistByEmail(updateRequest.getEmail()) ){
            log.info("User controller: Taken email " + updateRequest.getEmail());
            return ResponseEntity.badRequest().build();
        }
        service.updateUser(updateRequest, id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted a user"),
            @ApiResponse(responseCode = "400", description = "Missed required parameters, parameters are not valid"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Server error")})
    ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        if (service.userExistsById(id)) {
            service.deleteUserById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


}

