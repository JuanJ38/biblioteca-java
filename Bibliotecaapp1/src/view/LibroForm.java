package view;

import javax.swing.*;
import controller.BibliotecaController;

public class LibroForm extends JPanel { // 🔥 CAMBIO CLAVE

    private BibliotecaController controller;

    public LibroForm(BibliotecaController controller) {
        this.controller = controller;

        setLayout(null); // 🔥 ahora el layout va aquí (no en otro panel)

        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setBounds(20, 20, 80, 25);
        add(lblTitulo);

        JTextField txtTitulo = new JTextField();
        txtTitulo.setBounds(100, 20, 150, 25);
        add(txtTitulo);

        JLabel lblAutor = new JLabel("Autor:");
        lblAutor.setBounds(20, 60, 80, 25);
        add(lblAutor);

        JTextField txtAutor = new JTextField();
        txtAutor.setBounds(100, 60, 150, 25);
        add(txtAutor);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(100, 100, 100, 30);
        add(btnGuardar);

        // EVENTO 🔥
        btnGuardar.addActionListener(e -> {
            String titulo = txtTitulo.getText();
            String autor = txtAutor.getText();

            controller.agregarLibro(titulo, autor);

            JOptionPane.showMessageDialog(null, "Libro agregado"); // 🔥 cambio aquí
        });
    }
}