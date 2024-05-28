package lv.velotakas.app.controller;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lv.velotakas.app.dto.request.object.ObjectDTO;
import lv.velotakas.app.service.ObjectService;

@RestController
@RequestMapping("/api/object")
public class ObjectController {

    @Autowired
    private final ObjectService objectService;

    public ObjectController(ObjectService objectService) { this.objectService = objectService; }

    @PostMapping("/createObject")
    @Operation(summary = "Create an object for trail")
    public ResponseEntity<ObjectDTO> createObject(@RequestBody @Valid ObjectDTO objectDTO) {
        return ResponseEntity.ok(objectService.createObject(objectDTO));
    }
}
