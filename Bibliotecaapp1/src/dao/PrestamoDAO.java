package dao;

import java.sql.*;
import java.util.ArrayList;
import model.Prestamo;
import util.ConexionBD;

/**
 * Data Access Object (DAO) para la entidad Prestamo.
 * Maneja la lógica de salida y registro de libros en la Biblioteca.
 */
public class PrestamoDAO {

    // 1. Registrar un nuevo préstamo
    public void registrarPrestamo(int idLibro, int idUsuario) {
        // Usamos GETDATE() de SQL Server para la fecha automática
        String sql = "INSERT INTO prestamos (id_libro, id_usuario, fecha_prestamo) VALUES (?, ?, GETDATE())";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (conn == null) return;

            ps.setInt(1, idLibro);
            ps.setInt(2, idUsuario);
            ps.executeUpdate();

            System.out.println("✅ Préstamo registrado correctamente en la BD.");

        } catch (SQLException e) {
            System.err.println("❌ Error al registrar préstamo: " + e.getMessage());
        }
    }

    // 2. Listar todos los préstamos registrados
    public ArrayList<Prestamo> listarPrestamos() {
        ArrayList<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT id, id_libro, id_usuario, fecha_prestamo, fecha_devolucion FROM prestamos";

        try (Connection conn = ConexionBD.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (conn == null) return lista;

            while (rs.next()) {
                Prestamo p = new Prestamo(
                        rs.getInt("id"),
                        rs.getInt("id_libro"),
                        rs.getInt("id_usuario"),
                        rs.getTimestamp("fecha_prestamo"),   // Cambiado a Timestamp para mayor precisión
                        rs.getTimestamp("fecha_devolucion") // Puede ser null si no se ha devuelto
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar préstamos: " + e.getMessage());
        }

        return lista;
    }
}