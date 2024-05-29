package lv.velotakas.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lv.velotakas.app.dto.request.trail.TrailDTO;
import lv.velotakas.app.service.TrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/route")

public class TrailController {

    @Autowired
    private final TrailService trailService;

    public TrailController(TrailService trailService) { this.trailService = trailService; }

    @PostMapping("/createRoute")
    @Operation(summary = "Create a trail")
    public ResponseEntity<TrailDTO> createTrail(@RequestBody @Valid TrailDTO trailDTO) {
        return ResponseEntity.ok(trailService.createTrail(trailDTO));
    }
}
