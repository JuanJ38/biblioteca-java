package view;

import javax.swing.*;
import java.awt.*;
import controller.BibliotecaController;

public class PanelInicio extends JPanel {

    public PanelInicio(BibliotecaController controller, String rol) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // === PARTE SUPERIOR: imagen + bienvenida ===
        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.setBackground(new Color(0, 150, 150));
        panelTop.setPreferredSize(new Dimension(0, 160));

        // Imagen desde resources
        ImageIcon icon = new ImageIcon(
            getClass().getResource("/resources/imgLog.jpeg"));
        Image img = icon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
        JLabel lblImg = new JLabel(new ImageIcon(img));
        lblImg.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel panelTexto = new JPanel();
        panelTexto.setOpaque(false);
        panelTexto.setLayout(new BoxLayout(panelTexto, BoxLayout.Y_AXIS));
        panelTexto.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        JLabel lblBienvenida = new JLabel("Bienvenido, " + rol);
        lblBienvenida.setForeground(Color.WHITE);
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JLabel lblSub = new JLabel("Sistema de Gestión de Biblioteca");
        lblSub.setForeground(new Color(200, 240, 240));
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        panelTexto.add(lblBienvenida);
        panelTexto.add(Box.createVerticalStrut(8));
        panelTexto.add(lblSub);

        panelTop.add(lblImg, BorderLayout.WEST);
        panelTop.add(panelTexto, BorderLayout.CENTER);

        // === ESTADÍSTICAS ===
        JPanel panelStats = new JPanel(new GridLayout(1, 3, 20, 0));
        panelStats.setBackground(Color.WHITE);
        panelStats.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        panelStats.add(crearCard("📚 Libros",
            String.valueOf(controller.contarLibros()),
            new Color(0, 150, 150)));
        panelStats.add(crearCard("👤 Usuarios",
            String.valueOf(controller.contarUsuarios()),
            new Color(52, 73, 94)));
        panelStats.add(crearCard("📖 Préstamos",
            String.valueOf(controller.contarPrestamos()),
            new Color(39, 174, 96)));

        add(panelTop, BorderLayout.NORTH);
        add(panelStats, BorderLayout.CENTER);
    }

    private JPanel crearCard(String titulo, String valor, Color color) {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JPanel inner = new JPanel();
        inner.setOpaque(false);
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));

        JLabel lblNum = new JLabel(valor);
        lblNum.setForeground(Color.WHITE);
        lblNum.setFont(new Font("Segoe UI", Font.BOLD, 40));
        lblNum.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTit = new JLabel(titulo);
        lblTit.setForeground(new Color(220, 240, 240));
        lblTit.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTit.setAlignmentX(Component.CENTER_ALIGNMENT);

        inner.add(lblNum);
        inner.add(Box.createVerticalStrut(6));
        inner.add(lblTit);
        card.add(inner);
        return card;
    }
}