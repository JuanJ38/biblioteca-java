package dao;

import java.sql.*;
import java.util.ArrayList;
import model.Usuario;
import util.ConexionBD;

public class UsuarioDAO {

    // Guardar usuario en BD
    public void guardarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, correo) VALUES (?, ?)";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            ps.executeUpdate();

            System.out.println("Usuario guardado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al guardar usuario: " + e.getMessage());
        }
    }

    // Listar todos los usuarios
    public ArrayList<Usuario> listarUsuarios() {
        ArrayList<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = ConexionBD.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }

        return lista;
    }
    
    public String validar(String usuario, String password) {

        if (usuario.equals("admin") && password.equals("1234")) {
            return "ADMIN";
        }

        if (usuario.equals("user") && password.equals("1234")) {
            return "USER";
        }

        return null;
    }
}
