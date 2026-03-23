package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import model.Libro;
import controller.BibliotecaController;
import java.awt.*;

public class ListaLibrosFrame extends JPanel {

    private BibliotecaController controller;

    // 🔥 AHORA SON ATRIBUTOS (CLAVE)
    private DefaultTableModel modelo;
    private JTable tabla;

    public ListaLibrosFrame(BibliotecaController controller) {
        this.controller = controller;

        setLayout(new BorderLayout());

        String[] columnas = {"ID", "Título", "Autor", "Disponible"};

        // 🔥 Inicializar como atributo
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        JButton btnEliminar = new JButton("Eliminar Libro");
        add(btnEliminar, BorderLayout.SOUTH);

        JButton btnEditar = new JButton("Editar Libro");
        add(btnEditar, BorderLayout.NORTH);

        // 🔥 Cargar datos iniciales
        cargarLibros();

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

            refrescar(); // 🔥 IMPORTANTE
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

            refrescar(); // 🔥 IMPORTANTE
        });
    }

    // 🔥 MÉTODO PARA RECARGAR LA TABLA
    public void refrescar() {
        cargarLibros();
    }

    private void cargarLibros() {
        ArrayList<Libro> libros = controller.obtenerLibros();

        modelo.setRowCount(0); // limpiar

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