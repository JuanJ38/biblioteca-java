package view;

import controller.BibliotecaController;
import util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UsuarioForm extends JPanel {

    private final BibliotecaController controller;
    private PrestamoForm prestamoFormRef = null;

    public void setPrestamoFormRef(PrestamoForm ref) {
        this.prestamoFormRef = ref;
    }

    public UsuarioForm(BibliotecaController controller) {
        this.controller = controller;
        setBackground(new Color(248, 250, 252));
        setLayout(new GridBagLayout());
        add(construirCard());
    }

    private JPanel construirCard() {
        PanelRedondeado card = new PanelRedondeado(Color.WHITE, 16);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(40, 50, 40, 50));
        card.setPreferredSize(new Dimension(480, 470));

        JLabel icono = new JLabel("👤");
        icono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        icono.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titulo = new JLabel("Agregar Nuevo Usuario");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(Tema.TEXT_DARK);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sub = new JLabel("Registra un nuevo usuario — el correo debe ser único y válido");
        sub.setFont(Tema.FONT_SMALL);
        sub.setForeground(Tema.TEXT_GRAY);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setForeground(Tema.BORDER_LIGHT);
        sep.setMaximumSize(new Dimension(380, 1));

        CampoTextoModerno txtNombre = new CampoTextoModerno("Ej: Juan Pérez");
        txtNombre.setMaximumSize(new Dimension(380, 46));
        txtNombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        CampoTextoModerno txtCorreo = new CampoTextoModerno("Ej: juan@correo.com");
        txtCorreo.setMaximumSize(new Dimension(380, 46));
        txtCorreo.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Nota sobre correo único
        JLabel notaCorreo = new JLabel("ℹ  El correo debe ser único en el sistema");
        notaCorreo.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        notaCorreo.setForeground(new Color(100, 116, 139));
        notaCorreo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblError = new JLabel(" ");
        lblError.setFont(Tema.FONT_SMALL);
        lblError.setForeground(Tema.DANGER);
        lblError.setAlignmentX(Component.LEFT_ALIGNMENT);

        BotonModerno btnGuardar = new BotonModerno(
            "Registrar Usuario", new Color(59, 130, 246), new Color(37, 99, 235), 10);
        btnGuardar.setPreferredSize(new Dimension(380, 46));
        btnGuardar.setMaximumSize(new Dimension(380, 46));
        btnGuardar.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(icono);
        card.add(Box.createVerticalStrut(8));
        card.add(titulo);
        card.add(Box.createVerticalStrut(4));
        card.add(sub);
        card.add(Box.createVerticalStrut(20));
        card.add(sep);
        card.add(Box.createVerticalStrut(22));
        card.add(crearLabel("Nombre completo *"));
        card.add(Box.createVerticalStrut(6));
        card.add(txtNombre);
        card.add(Box.createVerticalStrut(16));
        card.add(crearLabel("Correo electrónico *"));
        card.add(Box.createVerticalStrut(6));
        card.add(txtCorreo);
        card.add(Box.createVerticalStrut(4));
        card.add(notaCorreo);
        card.add(Box.createVerticalStrut(8));
        card.add(lblError);
        card.add(Box.createVerticalStrut(14));
        card.add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            String nombre = txtNombre.getValor();
            String correo = txtCorreo.getValor();

            // Validaciones (igual que @NotBlank y @Email en Spring)
            if (nombre.isEmpty()) {
                lblError.setText("⚠  El nombre es obligatorio");
                txtNombre.requestFocus(); return;
            }
            if (correo.isEmpty()) {
                lblError.setText("⚠  El correo es obligatorio");
                txtCorreo.requestFocus(); return;
            }
            if (!correo.contains("@") || !correo.contains(".") || correo.indexOf("@") > correo.lastIndexOf(".")) {
                lblError.setText("⚠  Correo no válido (ej: nombre@dominio.com)");
                txtCorreo.requestFocus(); return;
            }
            lblError.setText(" ");

            boolean ok = controller.agregarUsuario(nombre, correo);
            if (ok) {
                JOptionPane.showMessageDialog(this,
                    "✅ Usuario \"" + nombre + "\" registrado correctamente.",
                    "Usuario Registrado", JOptionPane.INFORMATION_MESSAGE);
                txtNombre.limpiar(); txtCorreo.limpiar(); txtNombre.requestFocus();
                if (prestamoFormRef != null) prestamoFormRef.refrescarCombos();
            } else {
                JOptionPane.showMessageDialog(this,
                    "<html>No se pudo registrar.<br>El correo <b>" + correo + "</b> ya existe o hay un error de conexión.</html>",
                    "Error al registrar", JOptionPane.ERROR_MESSAGE);
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
