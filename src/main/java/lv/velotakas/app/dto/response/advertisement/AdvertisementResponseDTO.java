package lv.velotakas.app.dto.response.advertisement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementResponseDTO {
    private int id;
    private String category;
    private String description;
    private double price;
    private String timeLimit;
    private String filePath;
    private int userId;
}
