package app;

import view.Login;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Activar antialiasing y renderizado de fuentes mejorado
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new Login().setVisible(true);
        });
    }
}
