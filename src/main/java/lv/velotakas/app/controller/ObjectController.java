package lv.velotakas.app.controller;
import io.swagger.v3.oas.annotations.Operation;
import lv.velotakas.app.dto.request.object.UpdateObjectRequest;
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

    @GetMapping("/{id}")
    @Operation(summary = "Get information about an object")
    public ResponseEntity<ObjectDTO> getObject(@PathVariable Integer id){
        if(objectService.objectExistsById(id)){
            return ResponseEntity.ok(objectService.getObjectById(id));
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping("/edit/{id}")
    @Operation(summary = "Update an object")
    public ResponseEntity<ObjectDTO> editObject(@RequestBody UpdateObjectRequest updateRequest, @PathVariable Integer id) {
        if(objectService.objectExistsById(id)){
            System.out.println(updateRequest);

            objectService.updateObject(updateRequest, id);
            System.out.println("object does exist");
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete an object")

    public ResponseEntity<Void> deleteObject(@PathVariable Integer id){
        if(objectService.objectExistsById(id)){
            objectService.deleteObjectById(id);
            System.out.println("object does exist");
            return ResponseEntity.ok().build();
        }
        System.out.println("object does not exist");
        return ResponseEntity.notFound().build();
    }
}
