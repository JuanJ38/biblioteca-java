package view;

import controller.BibliotecaController;
import util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import model.Libro;

public class MainFrame extends JFrame {

    private final BibliotecaController controller = new BibliotecaController();
    private final String rol;

    private CardLayout      cardLayout;
    private JPanel          panelContenedor;
    private ListaLibrosFrame   listaLibrosFrame;
    private ListaUsuariosFrame listaUsuariosFrame;
    private PrestamoForm       prestamoForm;
    private PanelInicio        panelInicio;

    private JButton btnActivo = null;

    public MainFrame(String rol) {
        this.rol = rol;
        setTitle("Sistema de Biblioteca — " + rol);
        setSize(1150, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(true);
        setMinimumSize(new Dimension(900, 560));

        add(construirHeader(),  BorderLayout.NORTH);
        add(construirSidebar(), BorderLayout.WEST);

        cardLayout      = new CardLayout();
        panelContenedor = new JPanel(cardLayout);
        panelContenedor.setBackground(new Color(248, 250, 252));

        panelInicio        = new PanelInicio(controller, rol);
        listaLibrosFrame   = new ListaLibrosFrame(controller);
        listaUsuariosFrame = new ListaUsuariosFrame(controller);
        prestamoForm       = new PrestamoForm(controller);

        prestamoForm.setListaLibrosRef(listaLibrosFrame);

        panelContenedor.add(panelInicio,                  "INICIO");
        panelContenedor.add(new LibroForm(controller),    "LIBRO");
        panelContenedor.add(listaLibrosFrame,             "LISTA_LIBROS");
        panelContenedor.add(listaUsuariosFrame,           "LISTA_USUARIOS");
        panelContenedor.add(new UsuarioForm(controller),  "USUARIOS");
        panelContenedor.add(prestamoForm,                 "PRESTAMOS");

        add(panelContenedor, BorderLayout.CENTER);
    }

    // ── HEADER ──────────────────────────────────────────────────────
    private JPanel construirHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(15, 23, 42));
        header.setPreferredSize(new Dimension(0, 56));
        header.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);
        JLabel logoIcon = new JLabel("📚");
        logoIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));
        JLabel tituloLbl = new JLabel("BIBLIOTECA CENTRAL");
        tituloLbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tituloLbl.setForeground(Color.WHITE);
        JLabel sepLbl = new JLabel("|");
        sepLbl.setForeground(new Color(51, 65, 85));
        sepLbl.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        JLabel subLbl = new JLabel("Sistema de Gestión");
        subLbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subLbl.setForeground(new Color(100, 116, 139));
        left.add(logoIcon); left.add(tituloLbl); left.add(sepLbl); left.add(subLbl);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 0));
        right.setOpaque(false);

        JLabel reloj = new JLabel();
        reloj.setFont(new Font("Consolas", Font.BOLD, 15));
        reloj.setForeground(Tema.PRIMARY_LIGHT);
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
        new Timer(1000, e -> reloj.setText(fmt.format(new Date()))).start();
        reloj.setText(fmt.format(new Date()));

        JLabel badgeRol = crearBadge(rol.equals("ADMIN") ? "ADMIN" : "USUARIO",
                rol.equals("ADMIN") ? Tema.PRIMARY : Tema.INFO);

        BotonModerno btnSalir = new BotonModerno("⏻  Salir",
                new Color(220, 38, 38), new Color(185, 28, 28), 8);
        btnSalir.setPreferredSize(new Dimension(100, 32));
        btnSalir.addActionListener(e -> {
            int r = JOptionPane.showConfirmDialog(this,
                    "¿Deseas cerrar sesión y salir?", "Cerrar sesión",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (r == JOptionPane.YES_OPTION) System.exit(0);
        });

        right.add(reloj); right.add(badgeRol); right.add(btnSalir);
        header.add(left,  BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);
        return header;
    }

    private JLabel crearBadge(String texto, Color color) {
        JLabel b = new JLabel("  " + texto + "  ") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 30));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(color);
                g2.setStroke(new java.awt.BasicStroke(1));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        b.setFont(new Font("Segoe UI", Font.BOLD, 11));
        b.setForeground(color);
        b.setOpaque(false);
        b.setPreferredSize(new Dimension(90, 26));
        b.setHorizontalAlignment(SwingConstants.CENTER);
        return b;
    }

    // ── SIDEBAR ─────────────────────────────────────────────────────
    private JPanel construirSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(22, 31, 48));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(230, 0));

        // Avatar usuario
        JPanel avatarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 14));
        avatarPanel.setOpaque(false);
        avatarPanel.setMaximumSize(new Dimension(230, 70));
        JLabel avatar = crearAvatar(rol);
        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        JLabel nombreRol = new JLabel(rol);
        nombreRol.setFont(new Font("Segoe UI", Font.BOLD, 13));
        nombreRol.setForeground(Color.WHITE);
        JLabel tipoAcceso = new JLabel(rol.equals("ADMIN") ? "Administrador" : "Lector");
        tipoAcceso.setFont(Tema.FONT_SMALL);
        tipoAcceso.setForeground(new Color(100, 116, 139));
        infoPanel.add(nombreRol); infoPanel.add(tipoAcceso);
        avatarPanel.add(avatar); avatarPanel.add(infoPanel);

        JLabel sec1 = crearSeccionLabel("MENÚ PRINCIPAL");
        JButton btnInicio       = crearItemMenu("🏠  Inicio");
        JButton btnAgregarLibro = crearItemMenu("＋  Agregar Libro");
        JButton btnVerLibros    = crearItemMenu("📖  Catálogo de Libros");
        JButton btnPrestar      = crearItemMenu("🔄  Préstamos");

        sidebar.add(avatarPanel);
        sidebar.add(sepLine());
        sidebar.add(Box.createVerticalStrut(6));
        sidebar.add(sec1);
        sidebar.add(btnInicio);       sidebar.add(Box.createVerticalStrut(2));
        sidebar.add(btnAgregarLibro); sidebar.add(Box.createVerticalStrut(2));
        sidebar.add(btnVerLibros);    sidebar.add(Box.createVerticalStrut(2));
        sidebar.add(btnPrestar);

        if (!rol.equals("USER")) {
            sidebar.add(Box.createVerticalStrut(6));
            sidebar.add(sepLine());
            sidebar.add(Box.createVerticalStrut(6));
            sidebar.add(crearSeccionLabel("ADMINISTRACIÓN"));

            JButton btnVerUsuarios    = crearItemMenu("👥  Ver Usuarios");
            JButton btnAgregarUsuario = crearItemMenu("➕  Agregar Usuario");

            sidebar.add(btnVerUsuarios);    sidebar.add(Box.createVerticalStrut(2));
            sidebar.add(btnAgregarUsuario);

            sidebar.add(Box.createVerticalStrut(6));
            sidebar.add(sepLine());
            sidebar.add(Box.createVerticalStrut(6));
            sidebar.add(crearSeccionLabel("EXPORTAR CSV"));

            JButton btnExLibros    = crearItemMenu("📊  Exportar Libros");
            JButton btnExPrestamos = crearItemMenu("📋  Exportar Préstamos");
            JButton btnExUsuarios  = crearItemMenu("👤  Exportar Usuarios");

            sidebar.add(btnExLibros);    sidebar.add(Box.createVerticalStrut(2));
            sidebar.add(btnExPrestamos); sidebar.add(Box.createVerticalStrut(2));
            sidebar.add(btnExUsuarios);

            btnVerUsuarios.addActionListener(e -> {
                listaUsuariosFrame.refrescar();
                mostrarPanel("LISTA_USUARIOS", btnVerUsuarios);
            });
            btnAgregarUsuario.addActionListener(e -> mostrarPanel("USUARIOS", btnAgregarUsuario));

            btnExLibros.addActionListener(e -> {
                ArrayList<Libro> libros = controller.obtenerLibros();
                util.ExportarExcel.exportarLibros(libros);
                JOptionPane.showMessageDialog(this,
                        "✅ Archivo 'libros.csv' generado correctamente.\n" + libros.size() + " libro(s) exportado(s).",
                        "Exportación exitosa", JOptionPane.INFORMATION_MESSAGE);
            });
            btnExPrestamos.addActionListener(e -> {
                ArrayList<model.Prestamo> prest = controller.obtenerPrestamos();
                util.ExportarExcel.exportarPrestamos(prest);
                JOptionPane.showMessageDialog(this,
                        "✅ Archivo 'prestamos.csv' generado correctamente.\n" + prest.size() + " préstamo(s) exportado(s).",
                        "Exportación exitosa", JOptionPane.INFORMATION_MESSAGE);
            });
            btnExUsuarios.addActionListener(e -> {
                ArrayList<model.Usuario> usuarios = controller.obtenerUsuarios();
                util.ExportarExcel.exportarUsuarios(usuarios);
                JOptionPane.showMessageDialog(this,
                        "✅ Archivo 'usuarios.csv' generado correctamente.\n" + usuarios.size() + " usuario(s) exportado(s).",
                        "Exportación exitosa", JOptionPane.INFORMATION_MESSAGE);
            });
        }

        sidebar.add(Box.createVerticalGlue());
        JLabel version = new JLabel("v3.0 — 9no Ciclo");
        version.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        version.setForeground(new Color(51, 65, 85));
        version.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(version);
        sidebar.add(Box.createVerticalStrut(10));

        // Acciones botones base
        btnInicio.addActionListener(e -> {
            panelInicio.actualizar();
            mostrarPanel("INICIO", btnInicio);
        });
        btnAgregarLibro.addActionListener(e -> mostrarPanel("LIBRO", btnAgregarLibro));
        btnVerLibros.addActionListener(e -> {
            listaLibrosFrame.refrescar();
            mostrarPanel("LISTA_LIBROS", btnVerLibros);
        });
        btnPrestar.addActionListener(e -> {
            prestamoForm.refrescarTabla();
            mostrarPanel("PRESTAMOS", btnPrestar);
        });

        SwingUtilities.invokeLater(() -> activarBoton(btnInicio));
        return sidebar;
    }

    private void mostrarPanel(String nombre, JButton btn) {
        cardLayout.show(panelContenedor, nombre);
        activarBoton(btn);
    }

    private void activarBoton(JButton btn) {
        if (btnActivo != null) {
            btnActivo.setBackground(new Color(22, 31, 48));
            btnActivo.setForeground(new Color(148, 163, 184));
            btnActivo.setBorder(BorderFactory.createEmptyBorder(11, 18, 11, 18));
        }
        btnActivo = btn;
        btn.setBackground(new Color(6, 78, 59));
        btn.setForeground(Tema.PRIMARY_LIGHT);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 3, 0, 0, Tema.PRIMARY),
                BorderFactory.createEmptyBorder(11, 15, 11, 18)));
    }

    private JButton crearItemMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBackground(new Color(22, 31, 48));
        btn.setForeground(new Color(148, 163, 184));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createEmptyBorder(11, 18, 11, 18));
        btn.setMaximumSize(new Dimension(230, 44));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (btn != btnActivo) { btn.setBackground(new Color(30,41,59)); btn.setForeground(Color.WHITE); }
            }
            public void mouseExited(MouseEvent e) {
                if (btn != btnActivo) { btn.setBackground(new Color(22,31,48)); btn.setForeground(new Color(148,163,184)); }
            }
        });
        return btn;
    }

    private JLabel crearSeccionLabel(String texto) {
        JLabel lbl = new JLabel("  " + texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lbl.setForeground(new Color(51, 65, 85));
        lbl.setMaximumSize(new Dimension(230, 28));
        return lbl;
    }

    private JPanel sepLine() {
        JPanel s = new JPanel();
        s.setBackground(new Color(30, 41, 59));
        s.setMaximumSize(new Dimension(230, 1));
        s.setPreferredSize(new Dimension(230, 1));
        return s;
    }

    private JLabel crearAvatar(String rol) {
        String inicial = rol.isEmpty() ? "?" : String.valueOf(rol.charAt(0)).toUpperCase();
        JLabel lbl = new JLabel(inicial) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Tema.PRIMARY);
                g2.fillOval(0, 0, getWidth()-1, getHeight()-1);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 16));
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth()  - fm.stringWidth(inicial)) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(inicial, x, y);
                g2.dispose();
            }
        };
        lbl.setPreferredSize(new Dimension(38, 38));
        lbl.setOpaque(false);
        return lbl;
    }
}
