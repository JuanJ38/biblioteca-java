package dao;

import java.sql.*;
import java.util.ArrayList;
import model.Prestamo;
import util.ConexionBD;

public class PrestamoDAO {

    // Registrar un nuevo préstamo
    public void registrarPrestamo(int idLibro, int idUsuario) {
        String sql = "INSERT INTO prestamos (id_libro, id_usuario, fecha_prestamo) VALUES (?, ?, NOW())";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idLibro);
            ps.setInt(2, idUsuario);
            ps.executeUpdate();

            System.out.println("✅ Préstamo registrado correctamente.");

        } catch (SQLException e) {
            System.out.println("❌ Error al registrar préstamo:");
            e.printStackTrace();
        }
    }

    // Listar todos los préstamos
    public ArrayList<Prestamo> listarPrestamos() {
        ArrayList<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT * FROM prestamos";

        try (Connection conn = ConexionBD.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Prestamo p = new Prestamo(
                        rs.getInt("id"),
                        rs.getInt("id_libro"),
                        rs.getInt("id_usuario"),
                        rs.getTimestamp("fecha_prestamo"),
                        rs.getTimestamp("fecha_devolucion")
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al listar préstamos:");
            e.printStackTrace();
        }

        return lista;
    }
}