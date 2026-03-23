package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import model.Usuario;
import controller.BibliotecaController;
import java.awt.*;

public class ListaUsuariosFrame extends JPanel { // 🔥 CAMBIO

    private BibliotecaController controller;

    public ListaUsuariosFrame(BibliotecaController controller) {
        this.controller = controller;

        setLayout(new BorderLayout()); // 🔥 IMPORTANTE

        String[] columnas = {"ID", "Nombre", "Correo"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        add(scroll, BorderLayout.CENTER);

        cargarUsuarios(modelo);
    }

    private void cargarUsuarios(DefaultTableModel modelo) {
        ArrayList<Usuario> usuarios = controller.obtenerUsuarios();

        for (Usuario u : usuarios) {
            modelo.addRow(new Object[]{
                u.getId(),
                u.getNombre(),
                u.getCorreo()
            });
        }
    }
}