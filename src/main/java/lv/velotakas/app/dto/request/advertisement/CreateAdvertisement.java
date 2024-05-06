package lv.velotakas.app.dto.request.advertisement;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdvertisement {
    @NotNull
    @NotBlank
    @Size(max = 300)
    private String category;
    @NotNull
    @NotBlank
    @Size(max = 500)
    private String description;
    @NotNull
    @NotBlank
    private BigDecimal price;
    @NotNull
    @NotBlank
    private Integer userId;
}
