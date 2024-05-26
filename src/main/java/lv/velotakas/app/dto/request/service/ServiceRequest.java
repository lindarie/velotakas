package lv.velotakas.app.dto.request.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequest {

    private String name;

    private String comment;

    private String filePath;

    private String userEmail;
}
