package lv.velotakas.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDTO {
    private long id;
    private String name;
    private String comment;
    private String filePath;
    private UserDAO user;
}
