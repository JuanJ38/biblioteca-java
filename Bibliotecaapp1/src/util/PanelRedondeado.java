package util;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Panel con esquinas redondeadas y sombra opcional.
 */
public class PanelRedondeado extends JPanel {

    private int radio;
    private Color colorFondo;
    private boolean conSombra;

    public PanelRedondeado(Color colorFondo, int radio, boolean conSombra) {
        this.colorFondo = colorFondo;
        this.radio       = radio;
        this.conSombra   = conSombra;
        setOpaque(false);
    }

    public PanelRedondeado(Color colorFondo, int radio) {
        this(colorFondo, radio, true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (conSombra) {
            // Sombra suave
            for (int i = 4; i >= 1; i--) {
                g2.setColor(new Color(0, 0, 0, 8 * i));
                g2.fill(new RoundRectangle2D.Float(i, i + 1, getWidth() - i * 2, getHeight() - i * 2, radio, radio));
            }
        }

        g2.setColor(colorFondo);
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 4, getHeight() - 4, radio, radio));

        g2.dispose();
        super.paintComponent(g);
    }
}
