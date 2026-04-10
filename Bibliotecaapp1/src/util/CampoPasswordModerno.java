package util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Campo de contraseña con diseño moderno y placeholder.
 */
public class CampoPasswordModerno extends JPasswordField {

    private String placeholder;
    private boolean conPlaceholder;
    private Color colorBorde      = Tema.BORDER_LIGHT;
    private Color colorBordeFocus = Tema.BORDER_FOCUS;

    public CampoPasswordModerno(String placeholder) {
        this.placeholder    = placeholder;
        this.conPlaceholder = true;

        setOpaque(false);
        setBackground(Color.WHITE);
        setForeground(Tema.TEXT_GRAY);
        setFont(Tema.FONT_BODY);
        setCaretColor(Tema.PRIMARY);
        setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        setEchoChar((char) 0);
        setText(placeholder);

        addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                colorBorde = colorBordeFocus;
                if (conPlaceholder) {
                    setText("");
                    setForeground(Tema.TEXT_DARK);
                    setEchoChar('●');
                    conPlaceholder = false;
                }
                repaint();
            }
            public void focusLost(FocusEvent e) {
                colorBorde = Tema.BORDER_LIGHT;
                if (new String(getPassword()).isEmpty()) {
                    setEchoChar((char) 0);
                    setText(placeholder);
                    setForeground(Tema.TEXT_GRAY);
                    conPlaceholder = true;
                }
                repaint();
            }
        });
    }

    public String getValor() {
        return conPlaceholder ? "" : new String(getPassword()).trim();
    }

    public void limpiar() {
        setEchoChar((char) 0);
        setText(placeholder);
        setForeground(Tema.TEXT_GRAY);
        conPlaceholder = true;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(colorBorde);
        g2.setStroke(new BasicStroke(1.5f));
        g2.draw(new RoundRectangle2D.Float(0.75f, 0.75f, getWidth() - 1.5f, getHeight() - 1.5f, 10, 10));
        g2.dispose();
    }
}
