package view;

import controller.BibliotecaController;
import util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Pantalla de Login — diseño moderno split-panel con reloj en vivo.
 */
public class Login extends JFrame {

    private static final long serialVersionUID = 1L;

    public Login() {
        setTitle("Biblioteca Central — Acceso");
        setSize(920, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        // ── PANEL IZQUIERDO (branding) ──────────────────────────────────
        JPanel left = new PanelIzquierdo();
        left.setPreferredSize(new Dimension(400, 0));
        add(left, BorderLayout.WEST);

        // ── PANEL DERECHO (formulario) ──────────────────────────────────
        JPanel right = new JPanel(new GridBagLayout());
        right.setBackground(new Color(248, 250, 252));

        JPanel card = construirFormulario();
        right.add(card);
        add(right, BorderLayout.CENTER);
    }

    // ────────────────────────────────────────────────────────────────────
    //  FORMULARIO DE LOGIN
    // ────────────────────────────────────────────────────────────────────
    private JPanel construirFormulario() {
        PanelRedondeado card = new PanelRedondeado(Color.WHITE, 18);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(40, 48, 40, 48));
        card.setPreferredSize(new Dimension(380, 420));

        // Ícono
        JLabel icono = new JLabel("🔐");
        icono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        icono.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Título
        JLabel titulo = new JLabel("Iniciar Sesión");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(Tema.TEXT_DARK);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtítulo
        JLabel sub = new JLabel("Ingresa tus credenciales para continuar");
        sub.setFont(Tema.FONT_SMALL);
        sub.setForeground(Tema.TEXT_GRAY);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Separador decorativo
        JSeparator sep = new JSeparator();
        sep.setForeground(Tema.BORDER_LIGHT);
        sep.setMaximumSize(new Dimension(290, 1));

        // Campos
        CampoTextoModerno txtUsuario = new CampoTextoModerno(" Usuario");
        txtUsuario.setMaximumSize(new Dimension(290, 46));
        txtUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        CampoPasswordModerno txtPass = new CampoPasswordModerno("  Contraseña");
        txtPass.setMaximumSize(new Dimension(290, 46));
        txtPass.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Label de error
        JLabel lblError = new JLabel(" ");
        lblError.setFont(Tema.FONT_SMALL);
        lblError.setForeground(Tema.DANGER);
        lblError.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botón
        BotonModerno btnLogin = new BotonModerno(
            "Acceder al Sistema", Tema.PRIMARY, Tema.PRIMARY_DARK, 10);
        btnLogin.setPreferredSize(new Dimension(290, 46));
        btnLogin.setMaximumSize(new Dimension(290, 46));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Nota de credenciales demo
        JLabel nota = new JLabel("Demo: admin/1234  ·  user/1234");
        nota.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        nota.setForeground(new Color(180, 180, 180));
        nota.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Armar layout
        card.add(icono);
        card.add(Box.createVerticalStrut(8));
        card.add(titulo);
        card.add(Box.createVerticalStrut(4));
        card.add(sub);
        card.add(Box.createVerticalStrut(20));
        card.add(sep);
        card.add(Box.createVerticalStrut(20));
        card.add(crearLabel("Usuario"));
        card.add(Box.createVerticalStrut(6));
        card.add(txtUsuario);
        card.add(Box.createVerticalStrut(14));
        card.add(crearLabel("Contraseña"));
        card.add(Box.createVerticalStrut(6));
        card.add(txtPass);
        card.add(Box.createVerticalStrut(6));
        card.add(lblError);
        card.add(Box.createVerticalStrut(18));
        card.add(btnLogin);
        card.add(Box.createVerticalStrut(14));
        card.add(nota);

        // ── ACCIÓN LOGIN ──
        ActionListener loginAction = e -> {
            String user = txtUsuario.getValor();
            String pass = txtPass.getValor();

            if (user.isEmpty() || pass.isEmpty()) {
                lblError.setText("⚠  Completa todos los campos");
                return;
            }

            BibliotecaController controller = new BibliotecaController();
            String rol = controller.login(user, pass);

            if (rol != null) {
                lblError.setText(" ");
                new MainFrame(rol).setVisible(true);
                dispose();
            } else {
                lblError.setText("✗  Usuario o contraseña incorrectos");
                txtPass.limpiar();
                txtPass.requestFocus();
            }
        };

        btnLogin.addActionListener(loginAction);
        txtPass.addActionListener(loginAction);   // Enter en password
        txtUsuario.addActionListener(e -> txtPass.requestFocus());

        return card;
    }

    private JLabel crearLabel(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(Tema.FONT_BOLD_SM);
        l.setForeground(Tema.TEXT_GRAY);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    // ────────────────────────────────────────────────────────────────────
    //  PANEL IZQUIERDO con diseño gráfico y reloj
    // ────────────────────────────────────────────────────────────────────
    static class PanelIzquierdo extends JPanel {

        private String horaActual  = "";
        private String fechaActual = "";
        private final SimpleDateFormat fmtHora  = new SimpleDateFormat("HH:mm:ss");
        private final SimpleDateFormat fmtFecha = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy",
                                                       new java.util.Locale("es","PE"));

        PanelIzquierdo() {
            setOpaque(false);
            setLayout(new GridBagLayout());

            // Timer que actualiza cada segundo
            new Timer(1000, e -> {
                Date now = new Date();
                horaActual  = fmtHora.format(now);
                fechaActual = fmtFecha.format(now);
                repaint();
            }).start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int w = getWidth(), h = getHeight();

            // Gradiente de fondo
            GradientPaint gp = new GradientPaint(
                0, 0, new Color(15, 23, 42),
                w, h, new Color(6, 78, 59)
            );
            g2.setPaint(gp);
            g2.fillRect(0, 0, w, h);

            // Círculos decorativos
            g2.setColor(new Color(16, 185, 129, 30));
            g2.fillOval(-80, -80, 300, 300);
            g2.setColor(new Color(16, 185, 129, 15));
            g2.fillOval(w - 120, h - 120, 250, 250);
            g2.setColor(new Color(255, 255, 255, 8));
            g2.fillOval(60, h / 2, 200, 200);

            // Línea decorativa izquierda
            g2.setColor(Tema.PRIMARY);
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(32, 80, 32, h - 80);

            int cx = w / 2;

            // Ícono libro
            g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 52));
            g2.setColor(Tema.PRIMARY_LIGHT);
            drawCentered(g2, "📚", cx, 110);

            // Título
            g2.setFont(new Font("Segoe UI", Font.BOLD, 22));
            g2.setColor(Color.WHITE);
            drawCentered(g2, "BIBLIOTECA CENTRAL", cx, 165);

            // Subtítulo
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            g2.setColor(new Color(148, 163, 184));
            drawCentered(g2, "Sistema de Gestión Académica", cx, 192);

            // ── RELOJ ──
            int rX = cx, rY = h / 2 + 20;
            // Fondo del reloj
            g2.setColor(new Color(255, 255, 255, 12));
            g2.fillRoundRect(cx - 115, rY - 55, 230, 100, 16, 16);
            g2.setColor(Tema.PRIMARY);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(cx - 115, rY - 55, 230, 100, 16, 16);

            // Hora grande
            g2.setFont(new Font("Consolas", Font.BOLD, 36));
            g2.setColor(Tema.PRIMARY_LIGHT);
            drawCentered(g2, horaActual.isEmpty() ? "00:00:00" : horaActual, rX, rY - 5);

            // Fecha
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            g2.setColor(new Color(148, 163, 184));
            String fd = fechaActual.isEmpty() ? "" : capitalize(fechaActual);
            drawCentered(g2, fd, rX, rY + 28);

            // Características del sistema
            int yF = rY + 90;
            String[] features = { "  Control de préstamos", "  Gestión de usuarios", "  Inventario de libros" };
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            g2.setColor(new Color(100, 116, 139));
            for (String f : features) {
                drawCentered(g2, f, cx, yF);
                yF += 22;
            }

            // Footer
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            g2.setColor(new Color(71, 85, 105));
            drawCentered(g2, "© 2025 ", cx, h - 20);

            g2.dispose();
        }

        private void drawCentered(Graphics2D g2, String text, int cx, int y) {
            FontMetrics fm = g2.getFontMetrics();
            int x = cx - fm.stringWidth(text) / 2;
            g2.drawString(text, x, y);
        }

        private String capitalize(String s) {
            if (s == null || s.isEmpty()) return s;
            return Character.toUpperCase(s.charAt(0)) + s.substring(1);
        }
    }
}
