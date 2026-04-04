package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import controller.BibliotecaController;

public class LibroForm extends JPanel {

    private BibliotecaController controller;

    public LibroForm(BibliotecaController controller) {
        this.controller = controller;
        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());

        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            new EmptyBorder(30, 40, 30, 40)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(440, 320));

        // Título del formulario
        JLabel lblTitulo = new JLabel("📚 Agregar Nuevo Libro");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(0, 120, 120));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lblTitulo);
        card.add(Box.createVerticalStrut(24));

        // Campo Título
        card.add(crearLabel("Título del libro"));
        card.add(Box.createVerticalStrut(6));
        JTextField txtTitulo = crearCampo("Ingrese el título...");
        card.add(txtTitulo);
        card.add(Box.createVerticalStrut(18));

        // Campo Autor
        card.add(crearLabel("Autor"));
        card.add(Box.createVerticalStrut(6));
        JTextField txtAutor = crearCampo("Ingrese el autor...");
        card.add(txtAutor);
        card.add(Box.createVerticalStrut(28));

        // Botón guardar
        JButton btnGuardar = new JButton("Guardar Libro");
        btnGuardar.setBackground(new Color(0, 150, 150));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setPreferredSize(new Dimension(360, 44));
        btnGuardar.setMaximumSize(new Dimension(360, 44));
        btnGuardar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.add(btnGuardar);

        add(card);

        // Evento
        btnGuardar.addActionListener(e -> {
            String titulo = txtTitulo.getText().trim();
            String autor  = txtAutor.getText().trim();
            if (titulo.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "El título no puede estar vacío",
                    "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (autor.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "El autor no puede estar vacío",
                    "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            controller.agregarLibro(titulo, autor);
            JOptionPane.showMessageDialog(this, "✅ Libro agregado correctamente");
            txtTitulo.setText(""); txtAutor.setText("");
            txtTitulo.requestFocus();
        });
    }

    private JLabel crearLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(new Color(80, 80, 80));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JTextField crearCampo(String placeholder) {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(360, 40));
        field.setMaximumSize(new Dimension(360, 40));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 150, 150), 1),
            BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        return field;
    }
}