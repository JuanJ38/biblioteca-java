package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import controller.BibliotecaController;

public class UsuarioForm extends JPanel {

    private BibliotecaController controller;

    public UsuarioForm(BibliotecaController controller) {
        this.controller = controller;
        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());

        //  CARD CONTENEDOR 
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            new EmptyBorder(36, 44, 36, 44)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(460, 360));

        // Icono + Título
        JLabel lblIcono = new JLabel("👤");
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        lblIcono.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lblIcono);
        card.add(Box.createVerticalStrut(8));

        JLabel lblTitulo = new JLabel("Agregar nuevo usuario");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(40, 70, 100));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lblTitulo);

        JLabel lblSub = new JLabel("Registra un nuevo usuario en el sistema");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(130, 130, 130));
        lblSub.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lblSub);
        card.add(Box.createVerticalStrut(28));

        // Campo Nombre
        card.add(crearLabel("Nombre completo *"));
        card.add(Box.createVerticalStrut(6));
        JTextField txtNombre = crearCampo("Ej: Juan Pérez");
        card.add(txtNombre);
        card.add(Box.createVerticalStrut(20));

        // Campo Correo
        card.add(crearLabel("Correo electrónico *"));
        card.add(Box.createVerticalStrut(6));
        JTextField txtCorreo = crearCampo("Ej: juan@correo.com");
        card.add(txtCorreo);
        card.add(Box.createVerticalStrut(32));

        // Botón principal
        JButton btnGuardar = new JButton("Guardar usuario");
        btnGuardar.setBackground(new Color(40, 70, 100));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setOpaque(true);
        btnGuardar.setPreferredSize(new Dimension(372, 44));
        btnGuardar.setMaximumSize(new Dimension(372, 44));
        btnGuardar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btnGuardar.setBackground(new Color(30, 55, 80));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnGuardar.setBackground(new Color(40, 70, 100));
            }
        });

        card.add(btnGuardar);

        add(card);

        //  EVENTO 
        btnGuardar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();

            // Ignorar placeholders
            if (nombre.equals("Ej: Juan Pérez")) nombre = "";
            if (correo.equals("Ej: juan@correo.com")) correo = "";

            if (nombre.isEmpty()) {
                marcarError(txtNombre);
                JOptionPane.showMessageDialog(this,
                    "El nombre no puede estar vacío", "Dato requerido",
                    JOptionPane.WARNING_MESSAGE);
                txtNombre.requestFocus();
                return;
            }
            if (correo.isEmpty()) {
                marcarError(txtCorreo);
                JOptionPane.showMessageDialog(this,
                    "El correo no puede estar vacío", "Dato requerido",
                    JOptionPane.WARNING_MESSAGE);
                txtCorreo.requestFocus();
                return;
            }
            if (!correo.contains("@") || !correo.contains(".")) {
                marcarError(txtCorreo);
                JOptionPane.showMessageDialog(this,
                    "Ingresa un correo electrónico válido", "Formato incorrecto",
                    JOptionPane.WARNING_MESSAGE);
                txtCorreo.requestFocus();
                return;
            }

            boolean ok = controller.agregarUsuario(nombre, correo);
            if (ok) {
                JOptionPane.showMessageDialog(this,
                    "Usuario registrado correctamente.", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                txtNombre.setText("");
                txtCorreo.setText("");
                limpiarError(txtNombre);
                limpiarError(txtCorreo);
                txtNombre.requestFocus();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Ocurrió un error al guardar.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private JLabel crearLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(new Color(60, 60, 60));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JTextField crearCampo(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(372, 42));
        field.setMaximumSize(new Dimension(372, 42));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setForeground(new Color(180, 180, 180));
        field.setText(placeholder);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(40, 70, 100), 1),
            BorderFactory.createEmptyBorder(0, 12, 0, 12)
        ));
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(new Color(40, 40, 40));
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(40, 100, 160), 2),
                    BorderFactory.createEmptyBorder(0, 12, 0, 12)
                ));
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(new Color(180, 180, 180));
                    field.setText(placeholder);
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(40, 70, 100), 1),
                    BorderFactory.createEmptyBorder(0, 12, 0, 12)
                ));
            }
        });
        return field;
    }

    private void marcarError(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 53, 69), 2),
            BorderFactory.createEmptyBorder(0, 12, 0, 12)
        ));
    }

    private void limpiarError(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(40, 70, 100), 1),
            BorderFactory.createEmptyBorder(0, 12, 0, 12)
        ));
    }
}