package lv.velotakas.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lv.velotakas.app.dto.request.advertisement.AdvertisementDTO;
import lv.velotakas.app.dto.response.advertisement.AdvertisementResponseDTO;
import lv.velotakas.app.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
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
}
