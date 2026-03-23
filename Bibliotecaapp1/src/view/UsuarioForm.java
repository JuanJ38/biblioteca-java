package view;

import javax.swing.*;
import controller.BibliotecaController;

public class UsuarioForm extends JPanel { // 🔥 CAMBIO CLAVE

    private BibliotecaController controller;

    public UsuarioForm(BibliotecaController controller) {
        this.controller = controller;

        setLayout(null); // 🔥 ahora va aquí

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 20, 80, 25);
        add(lblNombre);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(100, 20, 150, 25);
        add(txtNombre);

        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setBounds(20, 60, 80, 25);
        add(lblCorreo);

        JTextField txtCorreo = new JTextField();
        txtCorreo.setBounds(100, 60, 150, 25);
        add(txtCorreo);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(100, 100, 100, 30);
        add(btnGuardar);

        // EVENTO 🔥
        btnGuardar.addActionListener(e -> {

            String nombre = txtNombre.getText();
            String correo = txtCorreo.getText();

            if (nombre.isEmpty() || correo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Complete todos los campos");
                return;
            }

            controller.agregarUsuario(nombre, correo);

            JOptionPane.showMessageDialog(null, "Usuario agregado");
        });
    }
}