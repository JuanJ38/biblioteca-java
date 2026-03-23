package view;

import javax.swing.*;
import controller.BibliotecaController;
import util.ExportarExcel;
import java.util.ArrayList;
import model.Libro;
import java.awt.*;

public class MainFrame extends JFrame {

    private BibliotecaController controller = new BibliotecaController();
    private String rol;

    private CardLayout cardLayout;
    private JPanel panelContenedor;

    // 🔥 REFERENCIA AL PANEL (IMPORTANTE)
    private ListaLibrosFrame listaLibrosFrame;

    public MainFrame(String rol) {
        this.rol = rol;

        setTitle("Sistema de Biblioteca");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // =========================
        // 🔵 PANEL IZQUIERDO (MENÚ)
        // =========================
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        panelMenu.setPreferredSize(new Dimension(200, 0));

        JButton btnAgregarLibro = new JButton("Agregar Libro");
        JButton btnVerLibros = new JButton("Ver Libros");
        JButton btnVerUsuario = new JButton("Ver Usuario");
        JButton btnAgregarUsuario = new JButton("Agregar Usuario");
        JButton btnPrestar = new JButton("Prestar Libro");
        JButton btnSalir = new JButton("Salir");
        JButton btnExcel = new JButton("Exportar Libros a Excel");

        JButton[] botones = {
            btnAgregarLibro, btnVerLibros, btnVerUsuario,
            btnAgregarUsuario, btnPrestar, btnSalir, btnExcel
        };

        for (JButton b : botones) {
            b.setAlignmentX(JButton.CENTER_ALIGNMENT);
            b.setMaximumSize(new Dimension(200, 40));
        }

        panelMenu.add(Box.createVerticalStrut(20));
        panelMenu.add(btnAgregarLibro);

        panelMenu.add(Box.createVerticalStrut(15));
        panelMenu.add(btnVerLibros);

        if (!rol.equals("USER")) {
            panelMenu.add(Box.createVerticalStrut(15));
            panelMenu.add(btnVerUsuario);

            panelMenu.add(Box.createVerticalStrut(15));
            panelMenu.add(btnAgregarUsuario);
        }

        panelMenu.add(Box.createVerticalStrut(15));
        panelMenu.add(btnPrestar);

        panelMenu.add(Box.createVerticalStrut(15));
        panelMenu.add(btnExcel);

        panelMenu.add(Box.createVerticalStrut(15));
        panelMenu.add(btnSalir);

        add(panelMenu, BorderLayout.WEST);

        // =========================
        // 🟢 PANEL DERECHO (DINÁMICO)
        // =========================
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);

        // Panel inicial
        JPanel panelInicio = new JPanel();
        panelInicio.add(new JLabel("BIENVENIDO AL SISTEMA"));

        panelContenedor.add(panelInicio, "INICIO");

        // 🔥 GUARDAMOS REFERENCIA
        panelContenedor.add(new LibroForm(controller), "LIBRO");

        listaLibrosFrame = new ListaLibrosFrame(controller);
        panelContenedor.add(listaLibrosFrame, "LISTA_LIBROS");

        panelContenedor.add(new ListaUsuariosFrame(controller), "LISTA_USUARIOS");
        panelContenedor.add(new UsuarioForm(controller), "USUARIOS");
        panelContenedor.add(new PrestamoForm(controller), "PRESTAMOS");

        add(panelContenedor, BorderLayout.CENTER);

        // =========================
        // 🔥 EVENTOS
        // =========================
        btnAgregarLibro.addActionListener(e -> {
            cardLayout.show(panelContenedor, "LIBRO");
        });

        btnVerLibros.addActionListener(e -> {
            listaLibrosFrame.refrescar(); // 🔥 ACTUALIZA LA TABLA
            cardLayout.show(panelContenedor, "LISTA_LIBROS");
        });

        btnVerUsuario.addActionListener(e -> {
            cardLayout.show(panelContenedor, "LISTA_USUARIOS");
        });

        btnAgregarUsuario.addActionListener(e -> {
            cardLayout.show(panelContenedor, "USUARIOS");
        });

        btnPrestar.addActionListener(e -> {
            cardLayout.show(panelContenedor, "PRESTAMOS");
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