package dao;

import java.sql.*;
import java.util.ArrayList;
import model.Usuario;
import util.ConexionBD;

public class UsuarioDAO {

    public boolean guardarUsuario(Usuario usuario) {
        // Verificar si el correo ya existe
        if (correoExiste(usuario.getCorreo())) {
            System.err.println("El correo ya está registrado: " + usuario.getCorreo());
            return false;
        }
        String sql = "INSERT INTO usuarios (nombre, correo) VALUES (?, ?)";
        try (Connection conn = ConexionBD.conectar()) {
            if (conn == null) return false;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, usuario.getNombre());
                ps.setString(2, usuario.getCorreo());
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar usuario: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Usuario> listarUsuarios() {
        ArrayList<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, correo FROM usuarios ORDER BY nombre";
        try (Connection conn = ConexionBD.conectar()) {
            if (conn == null) return lista;
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    lista.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
        }
        return lista;
    }

    /** Busca usuario por correo electrónico (como findByCorreo en Spring) */
    public Usuario buscarPorCorreo(String correo) {
        String sql = "SELECT id, nombre, correo FROM usuarios WHERE correo = ?";
        try (Connection conn = ConexionBD.conectar()) {
            if (conn == null) return null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, correo);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("correo"));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar por correo: " + e.getMessage());
        }
        return null;
    }

    private boolean correoExiste(String correo) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE correo = ?";
        try (Connection conn = ConexionBD.conectar()) {
            if (conn == null) return false;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, correo);
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next() && rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public String validar(String usuario, String password) {
        if (usuario.equals("admin") && password.equals("1234")) return "ADMIN";
        if (usuario.equals("user")  && password.equals("1234")) return "USER";
        return null;
    }

    public boolean eliminarUsuario(int idUsuario) {
        // Verificar que no tenga préstamos activos
        if (tienePrestamosActivos(idUsuario)) {
            System.err.println("No se puede eliminar: usuario tiene préstamos activos.");
            return false;
        }
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = ConexionBD.conectar()) {
            if (conn == null) return false;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idUsuario);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

    private boolean tienePrestamosActivos(int idUsuario) {
        String sql = "SELECT COUNT(*) FROM prestamos WHERE id_usuario = ? AND fecha_devolucion IS NULL";
        try (Connection conn = ConexionBD.conectar()) {
            if (conn == null) return false;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idUsuario);
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next() && rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            return false;
        }
    }
}
