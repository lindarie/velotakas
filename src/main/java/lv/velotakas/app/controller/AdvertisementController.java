package lv.velotakas.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lv.velotakas.app.dto.request.advertisement.AdvertisementDTO;
import lv.velotakas.app.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ad")
public class AdvertisementController {

    @Autowired
    private final AdvertisementService adService;

    public AdvertisementController(AdvertisementService adService) {
        this.adService = adService;
    }

    @PostMapping
    @Operation(summary = "Create an advertisement")
    public ResponseEntity<AdvertisementDTO> createAdvertisement(@RequestBody @Valid AdvertisementDTO advertisementDTO) {
        return ResponseEntity.ok(adService.createAdvertisement(advertisementDTO));
    }
}
