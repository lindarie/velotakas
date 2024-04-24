package lv.velotakas.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDAO {

    private Integer id;
    private String name;
    private String surname;
    private String birthDate;
    private String email;
    private String description;
}
