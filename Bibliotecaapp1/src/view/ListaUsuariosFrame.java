package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import model.Usuario;
import controller.BibliotecaController;

public class ListaUsuariosFrame extends JFrame {

    private BibliotecaController controller;

    public ListaUsuariosFrame(BibliotecaController controller) {
        this.controller = controller;

        setTitle("Lista de Usuarios");
        setSize(500, 300);
        setLocationRelativeTo(null);

        String[] columnas = {"ID", "Nombre", "Correo"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        add(scroll);

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