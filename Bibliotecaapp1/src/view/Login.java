package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.awt.event.ActionListener;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		// Usuario
	    JLabel lblUsuario = new JLabel("Usuario:");
	    lblUsuario.setBounds(50, 50, 80, 25);
	    contentPane.add(lblUsuario);

	    JTextField txtUsuario = new JTextField();
	    txtUsuario.setBounds(150, 50, 150, 25);
	    contentPane.add(txtUsuario);

	    // Password
	    JLabel lblPassword = new JLabel("Contraseña:");
	    lblPassword.setBounds(50, 100, 80, 25);
	    contentPane.add(lblPassword);

	    JPasswordField txtPassword = new JPasswordField();
	    txtPassword.setBounds(150, 100, 150, 25);
	    contentPane.add(txtPassword);

	    // Botón Login
	    JButton btnLogin = new JButton("Login");
	    btnLogin.setBounds(150, 150, 100, 30);
	    contentPane.add(btnLogin);
	    
	    
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
