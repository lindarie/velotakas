package lv.velotakas.app.dto.request.trail;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTrailRequest {

    @NotNull
    @NotBlank
    @Size(max = 300)
    private String name;

    @NotNull
    @NotBlank
    @Size(max = 40)
    private String surface;

    @NotNull
    @NotBlank
    private int complexity;

    @NotNull
    @NotBlank
    @Size(max = 300)
    private String comment;
}
