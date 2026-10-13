package com.repertoire.service;

import com.repertoire.dao.ContactDao;
import com.repertoire.model.Contact;

import java.util.List;

public class ContactService {
    private final ContactDao contactDao;

    public ContactService() {
        this.contactDao = new ContactDao();
    }

    public Contact createContact(String firstName, String lastName, String phone, String email) {
        String cleanFirstName = normalize(firstName);
        String cleanLastName = normalize(lastName);
        String cleanPhone = normalize(phone);
        String cleanEmail = normalize(email);

        if (cleanFirstName.isEmpty() || cleanLastName.isEmpty()) {
            throw new IllegalArgumentException("Le prénom et le nom sont obligatoires.");
        }

        Contact contact = new Contact(cleanFirstName, cleanLastName, cleanPhone, cleanEmail);
        contactDao.save(contact);
        return contact;
    }

    public List<Contact> findAll() {
        return contactDao.findAll();
    }

    public List<Contact> search(String filter) {
        if (filter == null || filter.trim().isEmpty()) {
            return findAll();
        }
        return contactDao.search(filter.trim());
    }

    public void updateContact(Contact contact) {
        if (contact == null || contact.getId() == null) {
            throw new IllegalArgumentException("Le contact à modifier est invalide.");
        }
        contactDao.update(contact);
    }

    public void deleteContact(int id) {
        contactDao.deleteById(id);
    }

    private String normalize(String value) {
        if (value == null) {
            return "";
        }
        return value.trim();
    }
}
