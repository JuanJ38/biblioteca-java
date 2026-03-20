package view;

import javax.swing.*;
import controller.BibliotecaController;

public class LibroForm extends JFrame {

    private BibliotecaController controller;

    public LibroForm(BibliotecaController controller) {
        this.controller = controller;

        setTitle("Agregar Libro");
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setBounds(20, 20, 80, 25);
        panel.add(lblTitulo);

        JTextField txtTitulo = new JTextField();
        txtTitulo.setBounds(100, 20, 150, 25);
        panel.add(txtTitulo);

        JLabel lblAutor = new JLabel("Autor:");
        lblAutor.setBounds(20, 60, 80, 25);
        panel.add(lblAutor);

        JTextField txtAutor = new JTextField();
        txtAutor.setBounds(100, 60, 150, 25);
        panel.add(txtAutor);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(100, 100, 100, 30);
        panel.add(btnGuardar);

        add(panel);

        // EVENTO 🔥
        btnGuardar.addActionListener(e -> {
            String titulo = txtTitulo.getText();
            String autor = txtAutor.getText();

            controller.agregarLibro(titulo, autor);

            JOptionPane.showMessageDialog(this, "Libro agregado");
            dispose(); // cerrar ventana
        });
    }
}