package lv.velotakas.app.dto.request.object;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Size;

import java.awt.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateObjectRequest {
    @NotNull
    @NotBlank
    @Size(max = 300)
    private String name;

    @NotNull
    @NotBlank
    @Size(max = 300)
    private String type;

    @NotNull
    @NotBlank
    private Point location;

    @Size(max = 300)
    private String description;
}
