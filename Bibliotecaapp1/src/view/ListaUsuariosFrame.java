package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import model.Usuario;
import controller.BibliotecaController;
import java.awt.*;

public class ListaUsuariosFrame extends JPanel { // 🔥 CAMBIO

    private BibliotecaController controller;
    private DefaultTableModel modelo;

    public ListaUsuariosFrame(BibliotecaController controller) {
        this.controller = controller;

        setLayout(new BorderLayout()); // 🔥 IMPORTANTE

        String[] columnas = {"ID", "Nombre", "Correo"};
         modelo = new DefaultTableModel(columnas, 0);

        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        add(scroll, BorderLayout.CENTER);

        cargarUsuarios();
    }
    
    public void refrescar() {
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        ArrayList<Usuario> usuarios = controller.obtenerUsuarios();

        modelo.setRowCount(0);

        for (Usuario u : usuarios) {
            modelo.addRow(new Object[]{
                u.getId(),
                u.getNombre(),
                u.getCorreo()
            });
        }
    }
}