package com.repertoire.service;

import com.repertoire.dao.UserDao;
import com.repertoire.util.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthServiceTest {

    private final AuthService authService = new AuthService();
    private final UserDao userDao = new UserDao();

    @BeforeEach
    void setUp() {
        DatabaseConnection.initializeDatabase();
        userDao.deleteAll();
    }

    @Test
    void shouldLoginWithValidCredentials() {
        userDao.save(new com.repertoire.model.User("alice", "secret"));

        assertTrue(authService.login("alice", "secret"));
    }

    @Test
    void shouldRejectWrongCredentials() {
        userDao.save(new com.repertoire.model.User("bob", "pass123"));

        assertFalse(authService.login("bob", "wrong"));
        assertFalse(authService.login("unknown", "pass123"));
    }
}
