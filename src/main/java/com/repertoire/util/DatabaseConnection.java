package com.repertoire.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseConnection {

    private static final String DB_DIRECTORY = "data";
    private static final String DB_FILE = "repertoire.db";
    private static boolean initialized = false;

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        Path directory = Path.of(DB_DIRECTORY);
        try {
            Files.createDirectories(directory);
        } catch (Exception ignored) {
            // the directory already exists or is inaccessible
        }

        String url = "jdbc:sqlite:" + directory.resolve(DB_FILE).toAbsolutePath();
        return DriverManager.getConnection(url);
    }

    public static synchronized void initializeDatabase() {
        if (initialized) {
            return;
        }

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT NOT NULL UNIQUE, " +
                    "password TEXT NOT NULL)");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS contacts (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "first_name TEXT NOT NULL, " +
                    "last_name TEXT NOT NULL, " +
                    "phone TEXT, " +
                    "email TEXT)");

            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT COUNT(*) FROM users WHERE username = ?")) {
                preparedStatement.setString(1, "admin");
                var resultSet = preparedStatement.executeQuery();
                if (resultSet.next() && resultSet.getInt(1) == 0) {
                    try (PreparedStatement insertStatement = connection.prepareStatement(
                            "INSERT INTO users (username, password) VALUES (?, ?)")) {
                        insertStatement.setString(1, "admin");
                        insertStatement.setString(2, "admin");
                        insertStatement.executeUpdate();
                    }
                }
            }

            initialized = true;
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to initialize SQLite database", e);
        }
    }
}
