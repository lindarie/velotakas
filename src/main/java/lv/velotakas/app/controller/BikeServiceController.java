package lv.velotakas.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lv.velotakas.app.dto.request.service.ServiceRequest;
import lv.velotakas.app.dto.request.service.ServiceUpdateRequest;
import lv.velotakas.app.dto.response.ServiceDTO;
import lv.velotakas.app.service.BikeServiceService;
import lv.velotakas.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("api/service")
public class BikeServiceController {
    @Autowired
    BikeServiceService service;
    @Autowired
    UserService userService;
    @PostMapping("/create")
    @Operation(summary = "Create a bike service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created a service"),
            @ApiResponse(responseCode = "400", description = "Missed required parameters, parameters are not valid"),
            @ApiResponse(responseCode = "500", description = "Server error")})
    public ResponseEntity<ServiceDTO> createBikeService(@RequestBody @Valid ServiceRequest request){
        if (!userService.doesUserExistByEmail(request.getUserEmail())) {
            log.warn("Bike service controller: User does not exist wit email " + request.getUserEmail());
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.createBikeService(request));
    }

    @GetMapping
    @Operation(summary = "Retrieve all bike services")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all bike services"),
            @ApiResponse(responseCode = "500", description = "Server error")})
    ResponseEntity<List<ServiceDTO>> getAllBikeServices() {
        return ResponseEntity.ok(service.findAllBikeServices());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a bike service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved a bike service"),
            @ApiResponse(responseCode = "400", description = "Missed required parameters, parameters are not valid"),
            @ApiResponse(responseCode = "404", description = "Service not found"),
            @ApiResponse(responseCode = "500", description = "Server error")})
    ResponseEntity<ServiceDTO> getBikeService(@PathVariable Integer id) {
        if (service.serviceExistsById(id)) {
            ServiceDTO serviceDTO = service.findServiceById(id);
            return ResponseEntity.ok(serviceDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a bike service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully updated a bike service"),
            @ApiResponse(responseCode = "400", description = "Missed required parameters, parameters are not valid"),
            @ApiResponse(responseCode = "404", description = "Bike service not found"),
            @ApiResponse(responseCode = "500", description = "Server error")})
    ResponseEntity editBikeService(@RequestBody ServiceUpdateRequest updateRequest,
                            @PathVariable Integer id) {
        if (!service.serviceExistsById(id)) {
            log.warn("Bike service controller: Not found bike service with ID " + id);

            return ResponseEntity.notFound().build();
        }
        service.updateBikeService(updateRequest, id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a bike service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted a user"),
            @ApiResponse(responseCode = "400", description = "Missed required parameters, parameters are not valid"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Server error")})
    ResponseEntity<Void> deleteBikeService(@PathVariable Integer id) {
        if (service.serviceExistsById(id)) {
            service.deleteServiceById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


}