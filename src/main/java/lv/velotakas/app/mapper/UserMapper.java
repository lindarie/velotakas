package lv.velotakas.app.mapper;

import lv.velotakas.app.dto.request.user.UpdateUserRequest;
import lv.velotakas.app.dto.response.UserDAO;
import lv.velotakas.app.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target="id", source = "id")
    @Mapping(target="name", source = "name")
    @Mapping(target="surname", source = "surname")
    @Mapping(target="email", source = "email")
    @Mapping(target="description", source = "description")
    @Mapping(target="birthDate", source = "birthDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target="filePath", source = "filePath")
    UserDAO userToUserDAO(User user);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "surname", source = "surname")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "birthDate", source = "birthDate", dateFormat = "dd-MM-yyyy")
    void updateUserFromUserDAO(UpdateUserRequest updateRequest, @MappingTarget User user);

}
