package view;

import controller.BibliotecaController;
import util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LibroForm extends JPanel {

    private final BibliotecaController controller;
    private PrestamoForm prestamoFormRef = null;

    public void setPrestamoFormRef(PrestamoForm ref) {
        this.prestamoFormRef = ref;
    }

    public LibroForm(BibliotecaController controller) {
        this.controller = controller;
        setBackground(new Color(248, 250, 252));
        setLayout(new GridBagLayout());
        add(construirCard());
    }

    private JPanel construirCard() {
        PanelRedondeado card = new PanelRedondeado(Color.WHITE, 16);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(36, 48, 36, 48));
        card.setPreferredSize(new Dimension(500, 520));

        JLabel icono = new JLabel("📚");
        icono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        icono.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titulo = new JLabel("Agregar Nuevo Libro");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(Tema.TEXT_DARK);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sub = new JLabel("Completa los campos para registrar el libro en el catálogo");
        sub.setFont(Tema.FONT_SMALL);
        sub.setForeground(Tema.TEXT_GRAY);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setForeground(Tema.BORDER_LIGHT);
        sep.setMaximumSize(new Dimension(400, 1));

        CampoTextoModerno txtTitulo = new CampoTextoModerno("Ej: El Alquimista");
        txtTitulo.setMaximumSize(new Dimension(400, 46));
        txtTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        CampoTextoModerno txtAutor = new CampoTextoModerno("Ej: Paulo Coelho");
        txtAutor.setMaximumSize(new Dimension(400, 46));
        txtAutor.setAlignmentX(Component.LEFT_ALIGNMENT);

        CampoTextoModerno txtImagen = new CampoTextoModerno("https://... (URL de la portada)");
        txtImagen.setMaximumSize(new Dimension(400, 46));
        txtImagen.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Área de reseña
        JTextArea txtResena = new JTextArea(3, 1);
        txtResena.setFont(Tema.FONT_BODY);
        txtResena.setForeground(Tema.TEXT_GRAY);
        txtResena.setText("Escribe una breve reseña del libro...");
        txtResena.setLineWrap(true);
        txtResena.setWrapStyleWord(true);
        txtResena.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Tema.BORDER_LIGHT, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        txtResena.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (txtResena.getText().equals("Escribe una breve reseña del libro...")) {
                    txtResena.setText(""); txtResena.setForeground(Tema.TEXT_DARK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (txtResena.getText().trim().isEmpty()) {
                    txtResena.setText("Escribe una breve reseña del libro...");
                    txtResena.setForeground(Tema.TEXT_GRAY);
                }
            }
        });
        JScrollPane scrollResena = new JScrollPane(txtResena);
        scrollResena.setMaximumSize(new Dimension(400, 90));
        scrollResena.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollResena.setBorder(BorderFactory.createLineBorder(Tema.BORDER_LIGHT, 1));

        JLabel lblError = new JLabel(" ");
        lblError.setFont(Tema.FONT_SMALL);
        lblError.setForeground(Tema.DANGER);
        lblError.setAlignmentX(Component.LEFT_ALIGNMENT);

        BotonModerno btnGuardar = new BotonModerno("Guardar Libro", Tema.PRIMARY, Tema.PRIMARY_DARK, 10);
        btnGuardar.setPreferredSize(new Dimension(400, 46));
        btnGuardar.setMaximumSize(new Dimension(400, 46));
        btnGuardar.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(icono);
        card.add(Box.createVerticalStrut(6));
        card.add(titulo);
        card.add(Box.createVerticalStrut(4));
        card.add(sub);
        card.add(Box.createVerticalStrut(18));
        card.add(sep);
        card.add(Box.createVerticalStrut(18));
        card.add(crearLabel("Título del libro *"));
        card.add(Box.createVerticalStrut(6));
        card.add(txtTitulo);
        card.add(Box.createVerticalStrut(12));
        card.add(crearLabel("Autor *"));
        card.add(Box.createVerticalStrut(6));
        card.add(txtAutor);
        card.add(Box.createVerticalStrut(12));
        card.add(crearLabel("URL de imagen de portada"));
        card.add(Box.createVerticalStrut(6));
        card.add(txtImagen);
        card.add(Box.createVerticalStrut(12));
        card.add(crearLabel("Reseña"));
        card.add(Box.createVerticalStrut(6));
        card.add(scrollResena);
        card.add(Box.createVerticalStrut(6));
        card.add(lblError);
        card.add(Box.createVerticalStrut(12));
        card.add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            String t = txtTitulo.getValor();
            String a = txtAutor.getValor();
            String img = txtImagen.getValor();
            String res = txtResena.getText().trim();
            if (res.equals("Escribe una breve reseña del libro...")) res = "";
            if (t.isEmpty()) { lblError.setText("⚠  El título es obligatorio"); txtTitulo.requestFocus(); return; }
            if (a.isEmpty()) { lblError.setText("⚠  El autor es obligatorio");  txtAutor.requestFocus();  return; }
            lblError.setText(" ");
            boolean ok = controller.agregarLibro(t, a, img, res);
            if (ok) {
                JOptionPane.showMessageDialog(this,
                    "✅ Libro \"" + t + "\" agregado correctamente.", "Libro Registrado", JOptionPane.INFORMATION_MESSAGE);
                txtTitulo.limpiar(); txtAutor.limpiar(); txtImagen.limpiar();
                txtResena.setText("Escribe una breve reseña del libro...");
                txtResena.setForeground(Tema.TEXT_GRAY);
                txtTitulo.requestFocus();
                if (prestamoFormRef != null) prestamoFormRef.refrescarCombos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar. Verifica la conexión.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return card;
    }

    private JLabel crearLabel(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(Tema.FONT_BOLD_SM);
        l.setForeground(Tema.TEXT_GRAY);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }
}
