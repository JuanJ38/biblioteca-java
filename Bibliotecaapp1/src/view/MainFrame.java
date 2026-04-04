package view;

import javax.swing.*;
import controller.BibliotecaController;
import util.ExportarExcel;
import java.util.ArrayList;
import model.Libro;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {

    private BibliotecaController controller = new BibliotecaController();
    private String rol;

    private CardLayout cardLayout;
    private JPanel panelContenedor;

    private ListaLibrosFrame listaLibrosFrame;
    private ListaUsuariosFrame listaUsuariosFrame;

    public MainFrame(String rol) {
        this.rol = rol;

        setTitle("Sistema de Biblioteca");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

       
        // HEADER
        
        JPanel header = new JPanel();
        header.setBackground(new Color(0, 150, 150));
        header.setPreferredSize(new Dimension(0, 60));
        header.setLayout(new BorderLayout());

        JLabel titulo = new JLabel("  SISTEMA DE BIBLIOTECA");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));

        header.add(titulo, BorderLayout.WEST);

        add(header, BorderLayout.NORTH);

        
        //  SIDEBAR
        
        JPanel panelMenu = new JPanel();
        panelMenu.setBackground(new Color(30, 30, 30));
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        panelMenu.setPreferredSize(new Dimension(220, 0));

        JLabel lblUser = new JLabel("   Usuario: " + rol);
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));

        panelMenu.add(Box.createVerticalStrut(20));
        panelMenu.add(lblUser);
        panelMenu.add(Box.createVerticalStrut(20));

        
        //  BOTONES PRO
        
        JButton btnAgregarLibro = crearBoton("Agregar Libro");
        JButton btnVerLibros = crearBoton("Ver Libros");
        JButton btnVerUsuario = crearBoton("Ver Usuario");
        JButton btnAgregarUsuario = crearBoton("Agregar Usuario");
        JButton btnPrestar = crearBoton("Prestar Libro");
        JButton btnExcel = crearBoton("Exportar Excel");
        JButton btnSalir = crearBoton("Salir");

        panelMenu.add(btnAgregarLibro);
        panelMenu.add(Box.createVerticalStrut(10));
        panelMenu.add(btnVerLibros);
        panelMenu.add(Box.createVerticalStrut(10));

        if (!rol.equals("USER")) {
        	panelMenu.add(btnVerUsuario);
            panelMenu.add(Box.createVerticalStrut(10));

            panelMenu.add(btnAgregarUsuario);
            panelMenu.add(Box.createVerticalStrut(10));
        }

        panelMenu.add(btnPrestar);
        panelMenu.add(Box.createVerticalStrut(10));
        panelMenu.add(btnExcel);
        panelMenu.add(Box.createVerticalGlue());
        panelMenu.add(btnSalir);

        add(panelMenu, BorderLayout.WEST);

        
        // 🟢 CONTENIDO
        
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);
        panelContenedor.setBackground(Color.WHITE);

        PanelInicio panelInicio = new PanelInicio(controller, rol);
        panelContenedor.add(panelInicio, "INICIO");

        panelContenedor.add(new LibroForm(controller), "LIBRO");

        listaLibrosFrame = new ListaLibrosFrame(controller);
        panelContenedor.add(listaLibrosFrame, "LISTA_LIBROS");

        listaUsuariosFrame = new ListaUsuariosFrame(controller);
        panelContenedor.add(listaUsuariosFrame, "LISTA_USUARIOS");

        panelContenedor.add(new UsuarioForm(controller), "USUARIOS");
        panelContenedor.add(new PrestamoForm(controller), "PRESTAMOS");

        add(panelContenedor, BorderLayout.CENTER);

        
        //  EVENTOS
        
        btnAgregarLibro.addActionListener(e -> cardLayout.show(panelContenedor, "LIBRO"));

        btnVerLibros.addActionListener(e -> {
            listaLibrosFrame.refrescar();
            cardLayout.show(panelContenedor, "LISTA_LIBROS");
        });

        btnVerUsuario.addActionListener(e -> {
            listaUsuariosFrame.refrescar();
            cardLayout.show(panelContenedor, "LISTA_USUARIOS");
        });

        btnAgregarUsuario.addActionListener(e -> cardLayout.show(panelContenedor, "USUARIOS"));

        btnPrestar.addActionListener(e -> cardLayout.show(panelContenedor, "PRESTAMOS"));

        btnSalir.addActionListener(e -> System.exit(0));

        btnExcel.addActionListener(e -> {
            ArrayList<Libro> libros = controller.obtenerLibros();
            ExportarExcel.exportarLibros(libros);
            JOptionPane.showMessageDialog(null, "Excel generado correctamente");
        });
    }

    
    //  BOTÓN ESTILO PRO
    
    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);

        btn.setMaximumSize(new Dimension(180, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        btn.setFocusPainted(false);
        btn.setBackground(new Color(60, 63, 65)); //  gris diferente al fondo
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        //  HOVER EFECTO
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(0, 180, 180)); // turqueza
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(60, 63, 65));
            }
        });

        return btn;
    }
}