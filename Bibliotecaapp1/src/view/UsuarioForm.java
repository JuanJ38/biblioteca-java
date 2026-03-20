package view;

import javax.swing.*;
import controller.BibliotecaController;

public class UsuarioForm extends JFrame {

    private BibliotecaController controller;

    public UsuarioForm(BibliotecaController controller) {
        this.controller = controller;

        setTitle("Agregar Usuario");
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 20, 80, 25);
        panel.add(lblNombre);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(100, 20, 150, 25);
        panel.add(txtNombre);

        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setBounds(20, 60, 80, 25);
        panel.add(lblCorreo);

        JTextField txtCorreo = new JTextField();
        txtCorreo.setBounds(100, 60, 150, 25);
        panel.add(txtCorreo);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(100, 100, 100, 30);
        panel.add(btnGuardar);

        add(panel);

        // EVENTO 🔥
        btnGuardar.addActionListener(e -> {
            controller.agregarUsuario(
                txtNombre.getText(),
                txtCorreo.getText()
            );

            JOptionPane.showMessageDialog(this, "Usuario agregado");
            dispose();
        });
    }
}