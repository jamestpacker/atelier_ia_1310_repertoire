package com.repertoire.service;

import com.repertoire.dao.UserDao;

public class AuthService {
    private final UserDao userDao;

    public AuthService() {
        this.userDao = new UserDao();
    }

    public boolean login(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        return userDao.authenticate(username.trim(), password);
    }
}
