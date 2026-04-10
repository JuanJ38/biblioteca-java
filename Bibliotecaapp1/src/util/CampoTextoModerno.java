package util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Campo de texto con diseño moderno, placeholder y borde redondeado.
 */
public class CampoTextoModerno extends JTextField {

    private String placeholder;
    private boolean conPlaceholder;
    private Color colorBorde     = Tema.BORDER_LIGHT;
    private Color colorBordeFocus = Tema.BORDER_FOCUS;

    public CampoTextoModerno(String placeholder) {
        this.placeholder    = placeholder;
        this.conPlaceholder = true;

        setOpaque(false);
        setBackground(Color.WHITE);
        setForeground(Tema.TEXT_GRAY);
        setFont(Tema.FONT_BODY);
        setCaretColor(Tema.PRIMARY);
        setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        setText(placeholder);

        addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                colorBorde = colorBordeFocus;
                if (conPlaceholder) {
                    setText("");
                    setForeground(Tema.TEXT_DARK);
                    conPlaceholder = false;
                }
                repaint();
            }
            public void focusLost(FocusEvent e) {
                colorBorde = Tema.BORDER_LIGHT;
                if (getText().isEmpty()) {
                    setText(placeholder);
                    setForeground(Tema.TEXT_GRAY);
                    conPlaceholder = true;
                }
                repaint();
            }
        });
    }

    /** Devuelve el texto real (sin placeholder) */
    public String getValor() {
        return conPlaceholder ? "" : getText().trim();
    }

    /** Limpia el campo y restaura el placeholder */
    public void limpiar() {
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
