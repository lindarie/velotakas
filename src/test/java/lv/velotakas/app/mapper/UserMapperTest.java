package lv.velotakas.app.mapper;

import static org.junit.jupiter.api.Assertions.*;

import lv.velotakas.app.dto.request.user.UpdateUserRequest;
import lv.velotakas.app.dto.response.UserDAO;
import lv.velotakas.app.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapperImpl();
    }

    @Test
    void testUserToUserDAONullUser() {
        assertNull(userMapper.userToUserDAO(null));
    }

    @Test
    void testUserToUserDAO() {
        User user = new User();
        user.setId(1);
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john.doe@example.com");
        user.setDescription("A sample user.");
        user.setBirthDate(LocalDate.of(1990, 1, 1));

        UserDAO userDAO = userMapper.userToUserDAO(user);
        assertNotNull(userDAO);
        assertEquals(user.getId(), userDAO.getId());
        assertEquals(user.getName(), userDAO.getName());
        assertEquals(user.getSurname(), userDAO.getSurname());
        assertEquals(user.getEmail(), userDAO.getEmail());
        assertEquals(user.getDescription(), userDAO.getDescription());
        assertEquals("01-01-1990", userDAO.getBirthDate());
    }

    @Test
    void testUpdateUserFromUserDAONullUpdateRequest() {
        User user = new User();
        userMapper.updateUserFromUserDAO(null, user);
        assertNull(user.getName());
        assertNull(user.getSurname());
        assertNull(user.getEmail());
        assertNull(user.getDescription());
        assertNull(user.getBirthDate());
    }

    @Test
    void testUpdateUserFromUserDAO() {
        User user = new User();
        UpdateUserRequest updateRequest = new UpdateUserRequest();
        updateRequest.setName("Jane");
        updateRequest.setSurname("Smith");
        updateRequest.setEmail("jane.smith@example.com");
        updateRequest.setDescription("A new description.");
        updateRequest.setBirthDate(LocalDate.of(1985, 5, 15));

        userMapper.updateUserFromUserDAO(updateRequest, user);
        assertEquals("Jane", user.getName());
        assertEquals("Smith", user.getSurname());
        assertEquals("jane.smith@example.com", user.getEmail());
        assertEquals("A new description.", user.getDescription());
        assertEquals(LocalDate.of(1985, 5, 15), user.getBirthDate());
    }
}

