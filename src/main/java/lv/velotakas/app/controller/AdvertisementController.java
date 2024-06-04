package lv.velotakas.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lv.velotakas.app.dto.request.advertisement.AdvertisementDTO;
import lv.velotakas.app.dto.request.advertisement.AdvertisementUpdateDTO;
import lv.velotakas.app.dto.response.advertisement.AdvertisementResponseDTO;
import lv.velotakas.app.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/ad")
public class AdvertisementController {

    @Autowired
    private final AdvertisementService adService;

    public AdvertisementController(AdvertisementService adService) {
        this.adService = adService;
    }

    @PostMapping("/create")
    @Operation(summary = "Create an advertisement")
    public ResponseEntity<AdvertisementDTO> createAdvertisement(@RequestBody @Valid AdvertisementDTO advertisementDTO) {
        return ResponseEntity.ok(adService.createAdvertisement(advertisementDTO));
    }

    @GetMapping
    @Operation(summary = "Retrieve advertisements by category")
    public ResponseEntity<List<AdvertisementResponseDTO>> getAdvertisements(@RequestParam(required = false) String category) {
        List<AdvertisementResponseDTO> advertisements = adService.getAdvertisementsByCategory(category);
        return ResponseEntity.ok(advertisements);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve an advertisement")
    public ResponseEntity<AdvertisementResponseDTO> getAdvertisementById(@PathVariable Integer id) {
        AdvertisementResponseDTO advertisement = adService.getAdvertisementById(id);
        if (advertisement == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(advertisement);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Update a an advertisement")
    ResponseEntity editAdvertisementById(@RequestBody AdvertisementUpdateDTO updateRequest,
                                   @PathVariable Integer id) {
        if (!adService.advertisementExistsById(id)) {
            log.warn("Advertisement controller: Not found advertisement with ID " + id);

            return ResponseEntity.notFound().build();
        }
        adService.updateAdvertisement(updateRequest, id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a advertisement")
    ResponseEntity<Void> deleteAdvertisement(@PathVariable Integer id) {
        if (adService.advertisementExistsById(id)) {
            adService.deleteAdvertisementById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
