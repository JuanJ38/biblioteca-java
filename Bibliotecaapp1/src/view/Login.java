package view;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;

    public Login() {
        setTitle("Login");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // =========================
        // 🔵 PANEL IZQUIERDO
        // =========================
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(new Color(40, 40, 40));
        panelIzquierdo.setPreferredSize(new Dimension(300, 0));
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("BIBLIOTECA CENTRAL");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descripcion = new JLabel("BIENVENIDOS");
        descripcion.setForeground(Color.WHITE);
        descripcion.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/login.jpeg"));
        Image img = icon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH); // tamaño deseado
        JLabel lblImagen = new JLabel(new ImageIcon(img));
        lblImagen.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelIzquierdo.add(Box.createVerticalStrut(50));
        panelIzquierdo.add(titulo);
        panelIzquierdo.add(Box.createVerticalStrut(20));
        panelIzquierdo.add(descripcion);
        
        panelIzquierdo.add(Box.createVerticalStrut(20));
        panelIzquierdo.add(lblImagen);
        panelIzquierdo.add(Box.createVerticalGlue()); // empuja contenido hacia arriba

        add(panelIzquierdo, BorderLayout.WEST);

        // =========================
        //  PANEL DERECHO
        // =========================
        JPanel panelDerecho = new JPanel(new GridBagLayout());
        panelDerecho.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 30, 10, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Usuario
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblUsuario = new JLabel("Usuario:");
        panelDerecho.add(lblUsuario, gbc);

        JTextField txtUsuario = new JTextField(15);
        txtUsuario.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLUE));
        gbc.gridy = 1;
        panelDerecho.add(txtUsuario, gbc);

        // Password
        gbc.gridy = 2;
        JLabel lblPassword = new JLabel("Contraseña:");
        panelDerecho.add(lblPassword, gbc);

        JPasswordField txtPassword = new JPasswordField(15);
        txtPassword.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLUE));
        gbc.gridy = 3;
        panelDerecho.add(txtPassword, gbc);

        // Botón Login
        JButton btnLogin = new JButton("Login");
        btnLogin.setBackground(new Color(0, 102, 255));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));

        gbc.gridy = 4;
        panelDerecho.add(btnLogin, gbc);

        add(panelDerecho, BorderLayout.CENTER);

        // =========================
        // 🔥 EVENTO LOGIN
        // =========================
        btnLogin.addActionListener(e -> {
            String usuario = txtUsuario.getText();
            String password = new String(txtPassword.getPassword());

            controller.BibliotecaController controller = new controller.BibliotecaController();
            String rol = controller.login(usuario, password);

            if (rol != null) {
                new MainFrame(rol).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
            }
        });
    }
}