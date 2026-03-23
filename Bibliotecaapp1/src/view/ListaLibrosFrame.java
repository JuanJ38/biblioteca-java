package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import model.Libro;
import controller.BibliotecaController;
import java.awt.*;

public class ListaLibrosFrame extends JPanel { // 🔥 CAMBIO CLAVE

    private BibliotecaController controller;

    public ListaLibrosFrame(BibliotecaController controller) {
        this.controller = controller;

        setLayout(new BorderLayout()); // 🔥 importante

        String[] columnas = {"ID", "Título", "Autor", "Disponible"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        add(scroll, BorderLayout.CENTER);

        JButton btnEliminar = new JButton("Eliminar Libro");
        add(btnEliminar, BorderLayout.SOUTH);

        JButton btnEditar = new JButton("Editar Libro");
        add(btnEditar, BorderLayout.NORTH);

        cargarLibros(modelo);

        // ======================
        // 🔥 EVENTO EDITAR
        // ======================
        btnEditar.addActionListener(e -> {

            int fila = tabla.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione un libro");
                return;
            }

            int id = (int) modelo.getValueAt(fila, 0);
            String titulo = (String) modelo.getValueAt(fila, 1);
            String autor = (String) modelo.getValueAt(fila, 2);
            boolean disponible = modelo.getValueAt(fila, 3).equals("Sí");

            Libro libro = new Libro(id, titulo, autor, disponible);

            String nuevoTitulo = JOptionPane.showInputDialog(null, "Nuevo título:", titulo);
            String nuevoAutor = JOptionPane.showInputDialog(null, "Nuevo autor:", autor);

            if (nuevoTitulo == null || nuevoAutor == null) return;

            libro.setTitulo(nuevoTitulo);
            libro.setAutor(nuevoAutor);

            controller.actualizarLibro(libro);

            JOptionPane.showMessageDialog(null, "Libro actualizado");

            modelo.setRowCount(0);
            cargarLibros(modelo);
        });

        // ======================
        // 🔥 EVENTO ELIMINAR
        // ======================
        btnEliminar.addActionListener(e -> {

            int fila = tabla.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione un libro");
                return;
            }

            int idLibro = (int) modelo.getValueAt(fila, 0);

            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "¿Seguro que deseas eliminar este libro?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) return;

            controller.eliminarLibro(idLibro);

            JOptionPane.showMessageDialog(null, "Libro eliminado");

            modelo.setRowCount(0);
            cargarLibros(modelo);
        });
    }

    private void cargarLibros(DefaultTableModel modelo) {
        ArrayList<Libro> libros = controller.obtenerLibros();

        for (Libro l : libros) {
            modelo.addRow(new Object[]{
                l.getId(),
                l.getTitulo(),
                l.getAutor(),
                l.isDisponible() ? "Sí" : "No"
            });
        }
    }
}