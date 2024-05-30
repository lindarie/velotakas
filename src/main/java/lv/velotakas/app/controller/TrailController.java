package lv.velotakas.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lv.velotakas.app.dto.request.object.ObjectDTO;
import lv.velotakas.app.dto.request.trail.TrailDTO;
import lv.velotakas.app.dto.request.trail.UpdateTrailRequest;
import lv.velotakas.app.service.TrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trails")

public class TrailController {

    @Autowired
    private final TrailService trailService;

    public TrailController(TrailService trailService) { this.trailService = trailService; }

    @GetMapping
    @Operation(summary = "Rerieves all trails")
    ResponseEntity<List<TrailDTO>> getAllTrails(){
        return ResponseEntity.ok(trailService.findAllTrails());
    }

    @PostMapping("/createTrail")
    @Operation(summary = "Create a trail")
    public ResponseEntity<TrailDTO> createTrail(@RequestBody @Valid TrailDTO trailDTO) {
        return ResponseEntity.ok(trailService.createTrail(trailDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get information about the trail")
    public ResponseEntity<TrailDTO> getTrail(@PathVariable Integer id){
        if(trailService.trailExistsById(id)){
            return ResponseEntity.ok(trailService.getTrailById(id));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/edit/{id}")
    @Operation(summary = "Update the trail")
    public ResponseEntity<ObjectDTO> editObject(@RequestBody UpdateTrailRequest updateTrailRequest, @PathVariable Integer id) {
        if(trailService.trailExistsById(id)){
            trailService.updateTrail(updateTrailRequest, id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete the trail")
    public ResponseEntity<Void> deleteTrail(@PathVariable Integer id) {
        if(trailService.trailExistsById(id)){
            trailService.deleteTrailById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
