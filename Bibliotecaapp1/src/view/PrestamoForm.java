package view;

import javax.swing.*;
import controller.BibliotecaController;

public class PrestamoForm extends JFrame {

    private BibliotecaController controller;

    public PrestamoForm(BibliotecaController controller) {
        this.controller = controller;

        setTitle("Prestar Libro");
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblLibro = new JLabel("ID Libro:");
        lblLibro.setBounds(20, 20, 80, 25);
        panel.add(lblLibro);

        JTextField txtLibro = new JTextField();
        txtLibro.setBounds(100, 20, 150, 25);
        panel.add(txtLibro);

        JLabel lblUsuario = new JLabel("ID Usuario:");
        lblUsuario.setBounds(20, 60, 100, 25);
        panel.add(lblUsuario);

        JTextField txtUsuario = new JTextField();
        txtUsuario.setBounds(100, 60, 150, 25);
        panel.add(txtUsuario);

        JButton btnPrestar = new JButton("Prestar");
        btnPrestar.setBounds(100, 100, 100, 30);
        panel.add(btnPrestar);

        add(panel);

        // EVENTO 🔥
        btnPrestar.addActionListener(e -> {
            int idLibro = Integer.parseInt(txtLibro.getText());
            int idUsuario = Integer.parseInt(txtUsuario.getText());

            boolean exito = controller.prestarLibro(idLibro, idUsuario);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Préstamo realizado");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Libro no disponible");
            }
        });
    }
}