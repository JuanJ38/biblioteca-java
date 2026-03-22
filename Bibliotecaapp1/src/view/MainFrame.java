package view;

import javax.swing.*;
import controller.BibliotecaController;
import util.ExportarExcel;
import java.util.ArrayList;
import model.Libro;
import java.awt.GridLayout;
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
        panel.setLayout(new GridLayout(0, 1, 10, 10)); 

        // Botón agregar libro
        JButton btnAgregarLibro = new JButton("Agregar Libro");
       // btnAgregarLibro.setBounds(100, 50, 200, 30);
        panel.add(btnAgregarLibro);

        // Botón ver libros
        JButton btnVerLibros = new JButton("Ver Libros");
        //btnVerLibros.setBounds(100, 100, 200, 30);
        panel.add(btnVerLibros);
        
     // Botón ver Usuario
        JButton btnVerUsuario = new JButton("Ver Usuario");
        //btnVerUsuario.setBounds(100, 150, 200, 30);
        if (!rol.equals("USER")) {
            panel.add(btnVerUsuario);
        }
        
    // Boton para agregar Usuario   
        JButton btnAgregarUsuario = new JButton("Agregar Usuario");
        //btnAgregarUsuario.setBounds(100, 200, 200, 30);
        panel.add(btnAgregarUsuario);
        
    //Boton para prestar     
        JButton btnPrestar = new JButton("Prestar Libro");
        //btnPrestar.setBounds(100, 250, 200, 30);
        panel.add(btnPrestar);
        
    // Boton salir
        JButton btnSalir = new JButton("Salir");
        //btnSalir.setBounds(100, 300, 200, 30);
        panel.add(btnSalir);
        
        JButton btnExcel = new JButton("Exportar Libros a Excel");
        //btnExcel.setBounds(100, 350, 200, 30);
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
