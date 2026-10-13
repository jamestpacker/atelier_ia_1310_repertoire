package com.repertoire.app;

import com.repertoire.ui.LoginFrame;
import com.repertoire.util.DatabaseConnection;

import javax.swing.SwingUtilities;

public final class RepertoireApp {
    private RepertoireApp() {
    }

    public static void main(String[] args) {
        DatabaseConnection.initializeDatabase();

        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}
