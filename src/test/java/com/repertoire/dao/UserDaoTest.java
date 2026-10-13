package com.repertoire.dao;

import com.repertoire.model.User;
import com.repertoire.util.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    private final UserDao userDao = new UserDao();

    @BeforeEach
    void setUp() {
        DatabaseConnection.initializeDatabase();
        userDao.deleteAll();
    }

    @Test
    void shouldAuthenticateUserWithValidCredentials() {
        User user = new User("admin", "secret");
        userDao.save(user);

        assertTrue(userDao.authenticate("admin", "secret"));
        assertFalse(userDao.authenticate("admin", "wrong-password"));
    }
}
