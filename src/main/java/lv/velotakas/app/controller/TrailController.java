package lv.velotakas.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lv.velotakas.app.dto.request.object.ObjectDTO;
import lv.velotakas.app.dto.request.trail.TrailDTO;
import lv.velotakas.app.dto.request.trail.TrailObjectDTO;
import lv.velotakas.app.dto.request.trail.UpdateTrailRequest;
import lv.velotakas.app.service.ObjectService;
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

    @Autowired
    ObjectService objectService;

    public TrailController(TrailService trailService) { this.trailService = trailService; }

//    public ObjectController(ObjectService objectService) { this.objectService = objectService; }

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

    @PostMapping("/{id}/createObject")
    @Operation(summary = "Create an object for trail")

    public ResponseEntity<ObjectDTO> createObject(@RequestBody @Valid ObjectDTO objectDTO, @PathVariable Integer id) {
        ObjectDTO object = objectService.createObject(objectDTO);
        Integer objectId = object.getId();
        trailService.addObject(objectId, id);
        return ResponseEntity.ok(object);

    }


    @PutMapping("/edit/{id}")
    @Operation(summary = "Update the trail")
    public ResponseEntity<ObjectDTO> updateTrail(@RequestBody UpdateTrailRequest updateTrailRequest, @PathVariable Integer id) {
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

    @GetMapping("/category/{surface}")
    @Operation(summary = "Get trails by surface")
    public ResponseEntity<List<TrailDTO>> findTrailsBySurface(@PathVariable String surface) {
        if (surface == null || surface.isEmpty()) return ResponseEntity.notFound().build();
        List<TrailDTO> trails = trailService.getTrailsBySurface(surface);
        return ResponseEntity.ok(trails);
    }
}
