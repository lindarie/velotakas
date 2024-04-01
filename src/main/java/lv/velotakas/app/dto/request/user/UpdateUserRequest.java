package lv.velotakas.app.dto.request.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    @NotNull
    @NotBlank
    @Size(max = 30)
    private String name;
    @NotNull
    @NotBlank
    @Size(max = 30)
    private String surname;
    @NotNull
    @NotBlank
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;
    @Size(max = 500)
    private String description;
    @NotNull
    @Email
    @Size(max = 30)
    private String email;
    @NotNull
    @NotBlank
    @Size(min = 8, max = 50)
    private String password;
}
