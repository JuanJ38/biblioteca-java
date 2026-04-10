package dao;

import java.sql.*;
import java.util.ArrayList;
import model.Prestamo;
import util.ConexionBD;

public class PrestamoDAO {

    public boolean registrarPrestamo(int idLibro, int idUsuario) {
        String sql = "INSERT INTO prestamos (id_libro, id_usuario, fecha_prestamo) VALUES (?, ?, NOW())";
        try (Connection conn = ConexionBD.conectar()) {
            if (conn == null) return false;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idLibro);
                ps.setInt(2, idUsuario);
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al registrar préstamo: " + e.getMessage());
            return false;
        }
    }

    public boolean devolverLibro(int idPrestamo) {
        String sql = "UPDATE prestamos SET fecha_devolucion = NOW() WHERE id = ? AND fecha_devolucion IS NULL";
        try (Connection conn = ConexionBD.conectar()) {
            if (conn == null) return false;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idPrestamo);
                int filas = ps.executeUpdate();
                return filas > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al devolver libro: " + e.getMessage());
            return false;
        }
    }

    /** Todos los préstamos (historial completo) */
    public ArrayList<Prestamo> listarPrestamos() {
        return consultarPrestamos("", -1);
    }

    /** Solo los préstamos activos (sin fecha de devolución) */
    public ArrayList<Prestamo> listarActivos() {
        return consultarPrestamos(" WHERE p.fecha_devolucion IS NULL", -1);
    }

    /** Préstamos de un usuario específico */
    public ArrayList<Prestamo> listarPorUsuario(int idUsuario) {
        return consultarPrestamos(" WHERE p.id_usuario = " + idUsuario, -1);
    }

    private ArrayList<Prestamo> consultarPrestamos(String filtro, int limite) {
        ArrayList<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.id_libro, p.id_usuario, p.fecha_prestamo, p.fecha_devolucion, " +
                     "COALESCE(l.titulo, CONCAT('Libro #', p.id_libro)) AS titulo_libro, " +
                     "COALESCE(u.nombre, CONCAT('Usuario #', p.id_usuario)) AS nombre_usuario " +
                     "FROM prestamos p " +
                     "LEFT JOIN libros   l ON l.id = p.id_libro " +
                     "LEFT JOIN usuarios u ON u.id = p.id_usuario" +
                     filtro +
                     " ORDER BY p.id DESC";
        if (limite > 0) sql += " LIMIT " + limite;

        try (Connection conn = ConexionBD.conectar()) {
            if (conn == null) return lista;
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    Prestamo p = new Prestamo(
                        rs.getInt("id"),
                        rs.getInt("id_libro"),
                        rs.getInt("id_usuario"),
                        rs.getTimestamp("fecha_prestamo"),
                        rs.getTimestamp("fecha_devolucion")
                    );
                    p.setTituloLibro(rs.getString("titulo_libro"));
                    p.setNombreUsuario(rs.getString("nombre_usuario"));
                    lista.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar préstamos: " + e.getMessage());
        }
        return lista;
    }

    /** Cuenta préstamos activos */
    public int contarActivos() {
        String sql = "SELECT COUNT(*) FROM prestamos WHERE fecha_devolucion IS NULL";
        try (Connection conn = ConexionBD.conectar()) {
            if (conn == null) return 0;
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error al contar activos: " + e.getMessage());
        }
        return 0;
    }
}

