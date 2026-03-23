package view;

import javax.swing.*;
import controller.BibliotecaController;
import model.Libro;
import model.Usuario;
import java.awt.*;

public class PrestamoForm extends JPanel {

    private BibliotecaController controller; // 🔹 controlador para acceder a lógica

    public PrestamoForm(BibliotecaController controller) {
        this.controller = controller;

        setLayout(null); // 🔹 usamos posiciones manuales

        // =========================
        // 🔹 LABEL LIBRO
        // =========================
        JLabel lblLibro = new JLabel("Libro:");
        lblLibro.setBounds(20, 20, 80, 25);
        add(lblLibro);

        // =========================
        // 🔹 LABEL USUARIO
        // =========================
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(20, 60, 80, 25);
        add(lblUsuario);

        // =========================
        // 🔹 COMBO LIBROS
        // =========================
        JComboBox<Libro> comboLibros = new JComboBox<>();
        comboLibros.setBounds(100, 20, 180, 25);
        add(comboLibros);

        // =========================
        // 🔹 COMBO USUARIOS
        // =========================
        JComboBox<Usuario> comboUsuarios = new JComboBox<>();
        comboUsuarios.setBounds(100, 60, 180, 25);
        add(comboUsuarios);

        // =========================
        // 🔥 CARGAR LIBROS DESDE BD
        // =========================
        
        comboLibros.addItem(new Libro(0, "-- Seleccione libro --", "", true)); // opción vacía
        for (Libro l : controller.obtenerLibros()) {
            comboLibros.addItem(l); // 🔹 se agrega cada libro al combo
        }

        // =========================
        // 🔥 CARGAR USUARIOS DESDE BD
        // =========================
        
        comboUsuarios.addItem(new Usuario(0, "-- Seleccione Usuario --", "")); // opción vacía
        for (Usuario u : controller.obtenerUsuarios()) {
            comboUsuarios.addItem(u); // 🔹 se agrega cada usuario al combo
        }

        // =========================
        // 🔹 BOTÓN PRESTAR
        // =========================
        JButton btnPrestar = new JButton("Prestar");
        btnPrestar.setBounds(100, 100, 100, 30);
        add(btnPrestar);

        // =========================
        // 🔥 EVENTO DEL BOTÓN
        // =========================
        btnPrestar.addActionListener(e -> {

            // 🔹 Obtener libro seleccionado
            Libro libro = (Libro) comboLibros.getSelectedItem();

            // 🔹 Obtener usuario seleccionado
            Usuario usuario = (Usuario) comboUsuarios.getSelectedItem();

            // 🔹 Validación
            if (libro == null || usuario == null) {
                JOptionPane.showMessageDialog(null, "Seleccione datos");
                return;
            }

            // 🔹 Ejecutar préstamo (usa IDs)
            boolean exito = controller.prestarLibro(libro.getId(), usuario.getId());

            // 🔹 Resultado
            if (exito) {
                JOptionPane.showMessageDialog(null, "Préstamo realizado");
            } else {
                JOptionPane.showMessageDialog(null, "Libro no disponible");
            }
        });
    }
}