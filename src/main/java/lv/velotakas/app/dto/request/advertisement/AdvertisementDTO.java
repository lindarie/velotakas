package lv.velotakas.app.dto.request.advertisement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementDTO {
    private String category;
    private String description;
    private BigDecimal price;
    private LocalDateTime timeLimit;
    private String filePath;
    private Integer userId;
}
