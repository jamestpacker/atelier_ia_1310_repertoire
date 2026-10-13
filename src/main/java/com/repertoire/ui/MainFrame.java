package com.repertoire.ui;

import com.repertoire.dao.ContactDao;
import com.repertoire.model.Contact;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

public class MainFrame extends JFrame {
    private final ContactDao contactDao;
    private final DefaultTableModel tableModel;

    public MainFrame() {
        super("Répertoire téléphonique");
        this.contactDao = new ContactDao();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchField = new JTextField(30);
        JButton refreshButton = new JButton("Actualiser");
        JButton addButton = new JButton("Ajouter");
        JButton deleteButton = new JButton("Supprimer");

        topPanel.add(new JLabel("Recherche : "));
        topPanel.add(searchField);
        topPanel.add(refreshButton);
        topPanel.add(addButton);
        topPanel.add(deleteButton);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nom", "Prénom", "Téléphone", "Email"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        refreshButton.addActionListener(event -> loadContacts(searchField.getText()));
        addButton.addActionListener(event -> {
            String filter = searchField.getText();
            if (filter == null || filter.isBlank()) {
                loadContacts();
            } else {
                loadContacts(filter);
            }
        });
        deleteButton.addActionListener(event -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (int) table.getValueAt(selectedRow, 0);
                contactDao.deleteById(id);
                loadContacts();
            }
        });

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        loadContacts();
    }

    private void loadContacts() {
        loadContacts("");
    }

    private void loadContacts(String filter) {
        tableModel.setRowCount(0);
        List<Contact> contacts = filter == null || filter.isBlank() ? contactDao.findAll() : contactDao.search(filter);
        for (Contact contact : contacts) {
            tableModel.addRow(new Object[]{
                    contact.getId(),
                    contact.getLastName(),
                    contact.getFirstName(),
                    contact.getPhone(),
                    contact.getEmail()
            });
        }
    }
}
