package lv.velotakas.app.mapper;

import lv.velotakas.app.dto.response.UserDAO;
import lv.velotakas.app.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    @Mapping(target="id", source = "id")
    @Mapping(target="name", source = "name")
    @Mapping(target="surname", source = "surname")
    @Mapping(target="email", source = "email")
    @Mapping(target="description", source = "description")
    @Mapping(target="birthDate", source = "birthDate", dateFormat = "dd-MM-yyyy HH:mm:ss")
    UserDAO userToUserDAO(User user);

    @Mapping(target="id", source = "id")
    @Mapping(target="name", source = "name")
    @Mapping(target="surname", source = "surname")
    @Mapping(target="email", source = "email")
    @Mapping(target="description", source = "description")
    @Mapping(target="birthDate", source = "birthDate", dateFormat = "dd-MM-yyyy HH:mm:ss")
    User userDAOToUser(UserDAO userDAO);

    @Mapping(target = "name", source = "userDAO.name")
    @Mapping(target = "surname", source = "userDAO.surname")
    @Mapping(target = "description", source = "userDAO.description")
    @Mapping(target = "birthDate", source = "userDAO.birthDate", dateFormat = "dd-MM-yyyy HH:mm:ss")
    void updateUserFromUserDAO(UserDAO userDAO, @MappingTarget User user);

}
