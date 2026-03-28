package dao;

import java.sql.*;
import java.util.ArrayList;
import model.Usuario;
import util.ConexionBD;

/**
 * Data Access Object (DAO) para la entidad Usuario.
 * Gestiona el registro, listado y validación de accesos.
 */
public class UsuarioDAO {

    // 1. Guardar usuario en la BD
    public void guardarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, correo) VALUES (?, ?)";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (conn == null) return;

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            ps.executeUpdate();

            System.out.println("✅ Usuario guardado correctamente: " + usuario.getNombre());

        } catch (SQLException e) {
            System.err.println("❌ Error al guardar usuario: " + e.getMessage());
        }
    }

    // 2. Listar todos los usuarios
    public ArrayList<Usuario> listarUsuarios() {
        ArrayList<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, correo FROM usuarios";

        try (Connection conn = ConexionBD.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (conn == null) return lista;

            while (rs.next()) {
                lista.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo")
                ));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar usuarios: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Valida las credenciales de acceso al sistema.
     * @param usuario Nombre de usuario ingresado.
     * @param password Contraseña ingresada.
     * @return El rol del usuario ("ADMIN" o "USER") o null si las credenciales son incorrectas.
     */
    public String validar(String usuario, String password) {
        
        // Simulación de login para pruebas rápidas
        if (usuario.equals("admin") && password.equals("1234")) {
            return "ADMIN";
        }

        if (usuario.equals("user") && password.equals("1234")) {
            return "USER";
        }

        return null;
    }
}