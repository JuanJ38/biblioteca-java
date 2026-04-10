package util;

import java.awt.*;

/**
 * Paleta de colores y fuentes unificada para toda la aplicación.
 * Estilo: moderno, oscuro/verde biblioteca.
 */
public class Tema {

    // === COLORES PRIMARIOS ===
    public static final Color PRIMARY        = new Color(16, 185, 129);   // verde esmeralda
    public static final Color PRIMARY_DARK   = new Color(5,  150, 105);
    public static final Color PRIMARY_LIGHT  = new Color(52, 211, 153);

    // === FONDO ===
    public static final Color BG_DARK        = new Color(15,  23,  42);   // sidebar oscuro
    public static final Color BG_MEDIUM      = new Color(30,  41,  59);   // panel medio
    public static final Color BG_CARD        = new Color(248, 250, 252);  // tarjeta clara
    public static final Color BG_WHITE       = Color.WHITE;

    // === TEXTO ===
    public static final Color TEXT_WHITE     = Color.WHITE;
    public static final Color TEXT_MUTED     = new Color(148, 163, 184);
    public static final Color TEXT_DARK      = new Color(15,  23,  42);
    public static final Color TEXT_GRAY      = new Color(100, 116, 139);

    // === ESTADO ===
    public static final Color SUCCESS        = new Color(34,  197, 94);
    public static final Color DANGER         = new Color(239, 68,  68);
    public static final Color WARNING        = new Color(245, 158, 11);
    public static final Color INFO           = new Color(59,  130, 246);

    // === BORDES ===
    public static final Color BORDER_LIGHT   = new Color(226, 232, 240);
    public static final Color BORDER_FOCUS   = new Color(16,  185, 129);

    // === FUENTES ===
    public static Font FONT_TITLE   = new Font("Segoe UI", Font.BOLD,   22);
    public static Font FONT_HEADER  = new Font("Segoe UI", Font.BOLD,   16);
    public static Font FONT_BODY    = new Font("Segoe UI", Font.PLAIN,  14);
    public static Font FONT_SMALL   = new Font("Segoe UI", Font.PLAIN,  12);
    public static Font FONT_BOLD_SM = new Font("Segoe UI", Font.BOLD,   12);
    public static Font FONT_BOLD_LG = new Font("Segoe UI", Font.BOLD,   14);
    public static Font FONT_MONO    = new Font("Consolas",  Font.PLAIN,  13);
}
