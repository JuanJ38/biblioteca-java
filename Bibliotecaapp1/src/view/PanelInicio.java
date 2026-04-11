package view;

import controller.BibliotecaController;
import util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Panel de inicio / Dashboard con tarjetas de estadísticas y reloj en vivo.
 */
public class PanelInicio extends JPanel {

    private final BibliotecaController controller;
    private final String rol;

    // Labels de estadísticas (para refrescarlas)
    private JLabel lblNumLibros;
    private JLabel lblNumUsuarios;
    private JLabel lblNumPrestamos;

    public PanelInicio(BibliotecaController controller, String rol) {
        this.controller = controller;
        this.rol        = rol;
        construir();
    }

    private void construir() {
        setLayout(new BorderLayout(0, 0));
        setBackground(new Color(248, 250, 252));

        // ── BANNER SUPERIOR ──────────────────────────────────────────
        JPanel banner = new BannerPanel();
        banner.setPreferredSize(new Dimension(0, 180));
        add(banner, BorderLayout.NORTH);

        // ── CONTENIDO CENTRAL ──────────────────────────────────────
        JPanel centro = new JPanel(new BorderLayout(0, 20));
        centro.setOpaque(false);
        centro.setBorder(BorderFactory.createEmptyBorder(28, 32, 28, 32));

        // Sección label
        JLabel secLabel = new JLabel("Resumen del Sistema");
        secLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        secLabel.setForeground(new Color(30, 41, 59));
        centro.add(secLabel, BorderLayout.NORTH);

        // Tarjetas de estadísticas
        JPanel tarjetas = new JPanel(new GridLayout(1, 3, 20, 0));
        tarjetas.setOpaque(false);
        tarjetas.setBorder(BorderFactory.createEmptyBorder(14, 0, 0, 0));

        // Libros
        JPanel cardLibros = crearTarjeta("📚", "Libros en Catálogo",
            String.valueOf(controller.contarLibros()),
            new Color(16, 185, 129), new Color(6, 78, 59));
        lblNumLibros = (JLabel) ((JPanel) cardLibros.getComponent(1)).getComponent(0);

        // Usuarios
        JPanel cardUsuarios = crearTarjeta("👥", "Usuarios Registrados",
            String.valueOf(controller.contarUsuarios()),
            new Color(59, 130, 246), new Color(29, 78, 216));
        lblNumUsuarios = (JLabel) ((JPanel) cardUsuarios.getComponent(1)).getComponent(0);

        // Préstamos
        JPanel cardPrestamos = crearTarjeta("🔄", "Préstamos Activos",
            String.valueOf(controller.contarPrestamos()),
            new Color(245, 158, 11), new Color(180, 83, 9));
        lblNumPrestamos = (JLabel) ((JPanel) cardPrestamos.getComponent(1)).getComponent(0);

        tarjetas.add(cardLibros);
        tarjetas.add(cardUsuarios);
        tarjetas.add(cardPrestamos);
        centro.add(tarjetas, BorderLayout.CENTER);

        // Fila inferior: tips y acceso rápido
        JPanel bottom = crearPanelInfo();
        centro.add(bottom, BorderLayout.SOUTH);

        add(centro, BorderLayout.CENTER);
    }

    /** Refresca los contadores de estadísticas */
    public void actualizar() {
        if (lblNumLibros    != null) lblNumLibros.setText(String.valueOf(controller.contarLibros()));
        if (lblNumUsuarios  != null) lblNumUsuarios.setText(String.valueOf(controller.contarUsuarios()));
        if (lblNumPrestamos != null) lblNumPrestamos.setText(String.valueOf(controller.contarPrestamos()));
    }

    // ─────────────────────────────────────────────────────────────────
    //  Tarjeta de estadística
    // ─────────────────────────────────────────────────────────────────
    private JPanel crearTarjeta(String emoji, String titulo, String valor,
                                Color colorTop, Color colorBottom) {
        JPanel card = new JPanel(new BorderLayout(0, 0)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, colorTop, getWidth(), getHeight(), colorBottom);
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 16, 16);
                // Icono decorativo de fondo
                g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 70));
                g2.setColor(new Color(255, 255, 255, 18));
                g2.drawString(emoji, getWidth() - 85, getHeight() - 10);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(22, 24, 22, 24));

        // Emoji pequeño
        JLabel lblEmoji = new JLabel(emoji);
        lblEmoji.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));

        // Panel número + título
        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        JLabel lblNum = new JLabel(valor);
        lblNum.setFont(new Font("Segoe UI", Font.BOLD, 44));
        lblNum.setForeground(Color.WHITE);

        JLabel lblTit = new JLabel(titulo);
        lblTit.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTit.setForeground(new Color(255, 255, 255, 200));

        info.add(lblNum);
        info.add(lblTit);

        card.add(lblEmoji, BorderLayout.NORTH);
        card.add(info,     BorderLayout.CENTER);

        return card;
    }

    // ─────────────────────────────────────────────────────────────────
    //  Panel info inferior
    // ─────────────────────────────────────────────────────────────────
    private JPanel crearPanelInfo() {
        JPanel p = new JPanel(new GridLayout(1, 2, 20, 0));
        p.setOpaque(false);
        p.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        p.setPreferredSize(new Dimension(0, 90));

        // Tarjeta "Acceso rápido"
        p.add(crearMiniCard("  Acceso Rápido",
            "Usa el menú lateral para navegar\nentre los módulos del sistema.",
            new Color(30, 41, 59)));

        // Tarjeta "Credenciales de prueba"
        p.add(crearMiniCard("  Información de Sesión",
            "Sesión activa como: " + rol + "\nNivel: " + (rol.equals("ADMIN") ? "Administrador Total" : "Acceso de Lectura"),
            new Color(15, 23, 42)));

        return p;
    }

    private JPanel crearMiniCard(String titulo, String contenido, Color bg) {
        JPanel card = new JPanel(new BorderLayout(0, 6)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 12, 12);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(14, 18, 14, 18));

        JLabel lTitulo = new JLabel(titulo);
        lTitulo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lTitulo.setForeground(Tema.PRIMARY_LIGHT);

        JLabel lCont = new JLabel("<html>" + contenido.replace("\n","<br>") + "</html>");
        lCont.setFont(Tema.FONT_SMALL);
        lCont.setForeground(new Color(148, 163, 184));

        card.add(lTitulo, BorderLayout.NORTH);
        card.add(lCont,   BorderLayout.CENTER);
        return card;
    }

    // ─────────────────────────────────────────────────────────────────
    //  Banner con reloj y bienvenida
    // ─────────────────────────────────────────────────────────────────
    class BannerPanel extends JPanel {
        private final SimpleDateFormat fmtHora  = new SimpleDateFormat("HH:mm:ss");
        private final SimpleDateFormat fmtFecha = new SimpleDateFormat("EEEE dd 'de' MMMM, yyyy",
                                                     new java.util.Locale("es","PE"));
        private String hora  = "";
        private String fecha = "";

        BannerPanel() {
            setOpaque(false);
            hora  = fmtHora.format(new Date());
            fecha = fmtFecha.format(new Date());
            new Timer(1000, e -> {
                hora  = fmtHora.format(new Date());
                fecha = fmtFecha.format(new Date());
                repaint();
            }).start();
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int w = getWidth(), h = getHeight();

            // Gradiente
            g2.setPaint(new GradientPaint(0,0, new Color(15,23,42), w, h, new Color(6,78,59)));
            g2.fillRect(0, 0, w, h);

            // Patrón de puntos decorativo
            g2.setColor(new Color(255,255,255,8));
            for (int x = 0; x < w; x += 30)
                for (int y = 0; y < h; y += 30)
                    g2.fillOval(x, y, 3, 3);

            // Texto bienvenida
            g2.setFont(new Font("Segoe UI", Font.BOLD, 28));
            g2.setColor(Color.WHITE);
            g2.drawString("Bienvenido, " + rol, 32, 56);

            g2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            g2.setColor(new Color(148, 163, 184));
            g2.drawString("Sistema de Gestión de Biblioteca ", 32, 82);

            // Línea verde
            g2.setColor(Tema.PRIMARY);
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(32, 96, 300, 96);

            // Reloj en el banner
            int rx = w - 210, ry = 20;
            g2.setColor(new Color(255,255,255,10));
            g2.fillRoundRect(rx, ry, 185, 90, 14, 14);
            g2.setColor(Tema.PRIMARY);
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(rx, ry, 185, 90, 14, 14);

            g2.setFont(new Font("Consolas", Font.BOLD, 30));
            g2.setColor(Tema.PRIMARY_LIGHT);
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(hora, rx + (185 - fm.stringWidth(hora)) / 2, ry + 46);

            g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            g2.setColor(new Color(100, 116, 139));
            fm = g2.getFontMetrics();
            String f = fecha.isEmpty() ? "" : capitalize(fecha);
            g2.drawString(f, rx + (185 - fm.stringWidth(f)) / 2, ry + 68);

            g2.dispose();
        }

        private String capitalize(String s) {
            return s == null || s.isEmpty() ? s : Character.toUpperCase(s.charAt(0)) + s.substring(1);
        }
    }
}
