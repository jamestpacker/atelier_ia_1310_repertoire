package com.repertoire.ui;

import com.repertoire.service.AuthService;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class LoginFrame extends JFrame {
    private final AuthService authService;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JLabel errorLabel;

    public LoginFrame() {
        super("Connexion - Répertoire téléphonique");
        this.authService = new AuthService();
        this.usernameField = new JTextField();
        this.passwordField = new JPasswordField();
        this.errorLabel = new JLabel("", SwingConstants.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 260);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Connexion", SwingConstants.CENTER);
        panel.add(title);

        JPanel usernamePanel = new JPanel(new BorderLayout());
        usernamePanel.add(new JLabel("Identifiant : "), BorderLayout.WEST);
        usernamePanel.add(usernameField, BorderLayout.CENTER);
        panel.add(usernamePanel);

        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.add(new JLabel("Mot de passe : "), BorderLayout.WEST);
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        panel.add(passwordPanel);

        JButton loginButton = new JButton("Se connecter");
        loginButton.addActionListener(event -> handleLogin());
        panel.add(loginButton);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(errorLabel, BorderLayout.SOUTH);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();

        boolean success = authService.login(username, new String(password));
        if (success) {
            errorLabel.setText("");
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
            dispose();
        } else {
            errorLabel.setText("Identifiant ou mot de passe incorrect.");
        }

        passwordField.setText("");
    }
}
