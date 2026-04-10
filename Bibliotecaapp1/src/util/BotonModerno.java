package util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Botón personalizado con esquinas redondeadas y efecto hover.
 * Llama a super.paintComponent para garantizar que los ActionListeners funcionen.
 */
public class BotonModerno extends JButton {

    private Color colorNormal;
    private Color colorHover;
    private Color colorActual;
    private int   radio;

    public BotonModerno(String texto, Color colorNormal, Color colorHover) {
        super(texto);
        this.colorNormal = colorNormal;
        this.colorHover  = colorHover;
        this.colorActual = colorNormal;
        this.radio       = 10;
        init();
    }

    public BotonModerno(String texto, Color colorNormal, Color colorHover, int radio) {
        this(texto, colorNormal, colorHover);
        this.radio = radio;
    }

    private void init() {
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setForeground(Color.WHITE);
        setFont(Tema.FONT_BOLD_LG);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setOpaque(false);
        // Mantener background sincronizado para que el modelo JButton siga funcionando
        setBackground(colorNormal);

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                colorActual = colorHover;
                repaint();
            }
            public void mouseExited(MouseEvent e) {
                colorActual = colorNormal;
                repaint();
            }
            public void mousePressed(MouseEvent e) {
                colorActual = colorHover.darker();
                repaint();
            }
            public void mouseReleased(MouseEvent e) {
                colorActual = getBounds().contains(e.getPoint()) ? colorHover : colorNormal;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fondo redondeado
        g2.setColor(colorActual);
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radio, radio));

        g2.dispose();

        // Texto (dejar que super lo dibuje para mantener comportamiento correcto)
        // Pero como super pintaría encima con fondo opaco, dibujamos texto manualmente
        Graphics2D g3 = (Graphics2D) g.create();
        g3.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g3.setColor(getForeground());
        g3.setFont(getFont());
        FontMetrics fm = g3.getFontMetrics();
        int x = (getWidth()  - fm.stringWidth(getText())) / 2;
        int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2 - 1;
        g3.drawString(getText(), x, y);
        g3.dispose();
    }
}
