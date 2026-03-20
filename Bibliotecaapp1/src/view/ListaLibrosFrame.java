package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import model.Libro;
import controller.BibliotecaController;

public class ListaLibrosFrame extends JFrame {

    private BibliotecaController controller;

    public ListaLibrosFrame(BibliotecaController controller) {
        this.controller = controller;

        setTitle("Lista de Libros");
        setSize(500, 300);
        setLocationRelativeTo(null);

        String[] columnas = {"ID", "Título", "Autor", "Disponible"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        setLayout(new java.awt.BorderLayout());
        add(scroll, java.awt.BorderLayout.CENTER);
        
        JButton btnEliminar = new JButton("Eliminar Libro"); add(btnEliminar, java.awt.BorderLayout.SOUTH);
         
        cargarLibros(modelo);
        
        //actualizar
        
        JButton btnEditar = new JButton("Editar Libro");
        add(btnEditar, java.awt.BorderLayout.NORTH);
        
        
        //Evento actualizar
        
        btnEditar.addActionListener(e -> {

            int fila = tabla.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un libro");
                return;
            }

            int id = (int) modelo.getValueAt(fila, 0);
            String titulo = (String) modelo.getValueAt(fila, 1);
            String autor = (String) modelo.getValueAt(fila, 2);
            boolean disponible = modelo.getValueAt(fila, 3).equals("Sí");

            Libro libro = new Libro(id, titulo, autor, disponible);

            // 🔥 AQUÍ EDITAS
            String nuevoTitulo = JOptionPane.showInputDialog(this, "Nuevo título:", titulo);
            String nuevoAutor = JOptionPane.showInputDialog(this, "Nuevo autor:", autor);

            if (nuevoTitulo == null || nuevoAutor == null) return;

            libro.setTitulo(nuevoTitulo);
            libro.setAutor(nuevoAutor);

            controller.actualizarLibro(libro);

            JOptionPane.showMessageDialog(this, "Libro actualizado");

            // Recargar tabla
            modelo.setRowCount(0);
            cargarLibros(modelo);
        });
        
        
        
        //evento elimnar
        
        btnEliminar.addActionListener(e -> {

            int fila = tabla.getSelectedRow();

            // Validar selección
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un libro");
                return;
            }

            // Obtener ID
            int idLibro = (int) modelo.getValueAt(fila, 0);

            // Confirmar
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Seguro que deseas eliminar este libro?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) return;

            // Eliminar
            controller.eliminarLibro(idLibro);

            // Mensaje
            JOptionPane.showMessageDialog(this, "Libro eliminado");

            // Recargar tabla
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