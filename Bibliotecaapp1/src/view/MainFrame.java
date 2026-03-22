package view;

import javax.swing.*;
import controller.BibliotecaController;
import util.ExportarExcel;
import java.util.ArrayList;
import model.Libro;
import java.awt.Dimension;
public class MainFrame extends JFrame {

    private BibliotecaController controller = new BibliotecaController();
    private String rol;

    public MainFrame(String rol) {
    	this.rol = rol;
        setTitle("Sistema de Biblioteca");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centrar

        // Panel
        JPanel panel = new JPanel();
        //ordena los botones
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
     // BOTONES
        JButton btnAgregarLibro = new JButton("Agregar Libro");
        JButton btnVerLibros = new JButton("Ver Libros");
        JButton btnVerUsuario = new JButton("Ver Usuario");
        JButton btnAgregarUsuario = new JButton("Agregar Usuario");
        JButton btnPrestar = new JButton("Prestar Libro");
        JButton btnSalir = new JButton("Salir");
        JButton btnExcel = new JButton("Exportar Libros a Excel");

        // 🔧 TAMAÑOS PERSONALIZADOS
        btnAgregarLibro.setMaximumSize(new Dimension(280, 50));
        btnVerLibros.setMaximumSize(new Dimension(260, 45));
        btnVerUsuario.setMaximumSize(new Dimension(260, 45));
        btnAgregarUsuario.setMaximumSize(new Dimension(260, 45));
        btnPrestar.setMaximumSize(new Dimension(240, 40));
        btnSalir.setMaximumSize(new Dimension(200, 35));
        btnExcel.setMaximumSize(new Dimension(300, 45));

        // 🔧 CENTRAR BOTONES HORIZONTAL
        JButton[] botones = {
            btnAgregarLibro, btnVerLibros, btnVerUsuario,
            btnAgregarUsuario, btnPrestar, btnSalir, btnExcel
        };

        for (JButton b : botones) {
            b.setAlignmentX(JButton.CENTER_ALIGNMENT);
        }

        // 📌 AGREGAR ESPACIO  VETICAL Y BOTONES (CON ROLES)
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnAgregarLibro);

        panel.add(Box.createVerticalStrut(15));
        panel.add(btnVerLibros);

        if (!rol.equals("USER")) {
            panel.add(Box.createVerticalStrut(15));
            panel.add(btnVerUsuario);

            panel.add(Box.createVerticalStrut(15));
            panel.add(btnAgregarUsuario);
        }

        panel.add(Box.createVerticalStrut(15));
        panel.add(btnPrestar);

        panel.add(Box.createVerticalStrut(15));
        panel.add(btnSalir);

        panel.add(Box.createVerticalStrut(15));
        panel.add(btnExcel);
        
        
       
        add(panel);

        // EVENTOS 🔥
        btnAgregarLibro.addActionListener(e -> {
            new LibroForm(controller).setVisible(true);
        });

        btnVerLibros.addActionListener(e -> {
            new ListaLibrosFrame(controller).setVisible(true);
        });
        
        
        btnVerUsuario.addActionListener(e -> {
            new ListaUsuariosFrame(controller).setVisible(true);
        });

        btnAgregarUsuario.addActionListener(e -> {
            new UsuarioForm(controller).setVisible(true);
        });

        btnPrestar.addActionListener(e -> {
            new PrestamoForm(controller).setVisible(true);
        });

        btnSalir.addActionListener(e -> {
            System.exit(0);
        });
        
        btnExcel.addActionListener(e -> {
            ArrayList<Libro> libros = controller.obtenerLibros();
            ExportarExcel.exportarLibros(libros);

            JOptionPane.showMessageDialog(null, "Excel generado correctamente");
        });
        
        
    }
    
}
