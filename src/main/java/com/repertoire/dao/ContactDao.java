package com.repertoire.dao;

import com.repertoire.model.Contact;
import com.repertoire.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContactDao {

    public void save(Contact contact) {
        String sql = "INSERT INTO contacts (first_name, last_name, phone, email) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, contact.getFirstName());
            statement.setString(2, contact.getLastName());
            statement.setString(3, contact.getPhone());
            statement.setString(4, contact.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to save contact", e);
        }
    }

    public List<Contact> findAll() {
        String sql = "SELECT id, first_name, last_name, phone, email FROM contacts ORDER BY last_name, first_name";
        List<Contact> contacts = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                contacts.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to list contacts", e);
        }
        return contacts;
    }

    public Optional<Contact> findById(int id) {
        String sql = "SELECT id, first_name, last_name, phone, email FROM contacts WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(map(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to find contact by id", e);
        }
        return Optional.empty();
    }

    public void update(Contact contact) {
        String sql = "UPDATE contacts SET first_name = ?, last_name = ?, phone = ?, email = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, contact.getFirstName());
            statement.setString(2, contact.getLastName());
            statement.setString(3, contact.getPhone());
            statement.setString(4, contact.getEmail());
            statement.setInt(5, contact.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to update contact", e);
        }
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM contacts WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to delete contact", e);
        }
    }

    public List<Contact> search(String term) {
        String sql = "SELECT id, first_name, last_name, phone, email FROM contacts " +
                "WHERE lower(first_name) LIKE ? OR lower(last_name) LIKE ? OR phone LIKE ? " +
                "ORDER BY last_name, first_name";

        List<Contact> contacts = new ArrayList<>();
        String pattern = "%" + term.toLowerCase() + "%";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, pattern);
            statement.setString(2, pattern);
            statement.setString(3, pattern);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    contacts.add(map(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to search contacts", e);
        }
        return contacts;
    }

    private Contact map(ResultSet resultSet) throws SQLException {
        return new Contact(
                resultSet.getInt("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("phone"),
                resultSet.getString("email")
        );
    }
}
