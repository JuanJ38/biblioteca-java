package dao;

import java.sql.*;
import java.util.ArrayList;
import model.Libro;
import util.ConexionBD;

/**
 * Data Access Object (DAO) para la entidad Libro.
 * Maneja todas las operaciones CRUD contra BibliotecaDB.
 */
public class LibroDAO {

    // 1. Guardar un libro en la BD
    public void guardarLibro(Libro libro) {
        String sql = "INSERT INTO libros (titulo, autor, disponible) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (conn == null) return;

            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setBoolean(3, libro.isDisponible());

            ps.executeUpdate();
            System.out.println("✅ Libro guardado correctamente: " + libro.getTitulo());

        } catch (SQLException e) {
            System.err.println("❌ Error al guardar libro: " + e.getMessage());
        }
    }

    // 2. Listar todos los libros
    public ArrayList<Libro> listarLibros() {
        ArrayList<Libro> lista = new ArrayList<>();
        String sql = "SELECT id, titulo, autor, disponible FROM libros";

        try (Connection conn = ConexionBD.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (conn == null) return lista;

            while (rs.next()) {
                lista.add(new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getBoolean("disponible")
                ));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar libros: " + e.getMessage());
        }

        return lista;
    }

    // 3. Actualizar disponibilidad (Prestar/Devolver)
    public void actualizarDisponibilidad(int idLibro, boolean disponible) {
        String sql = "UPDATE libros SET disponible = ? WHERE id = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (conn == null) return;

            ps.setBoolean(1, disponible);
            ps.setInt(2, idLibro);
            ps.executeUpdate();
            System.out.println("✅ Disponibilidad actualizada para ID: " + idLibro);

        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar disponibilidad: " + e.getMessage());
        }
    }

    // 4. Eliminar un libro
    public void eliminarLibro(int idLibro) {
        String sql = "DELETE FROM libros WHERE id = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (conn == null) return;

            ps.setInt(1, idLibro);
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✅ Libro con ID " + idLibro + " eliminado.");
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar libro: " + e.getMessage());
        }
    }

    // 5. Actualizar todos los datos de un libro
    public void actualizarLibro(Libro libro) {
        String sql = "UPDATE libros SET titulo = ?, autor = ?, disponible = ? WHERE id = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (conn == null) return;

            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setBoolean(3, libro.isDisponible());
            ps.setInt(4, libro.getId());

            ps.executeUpdate();
            System.out.println("✅ Libro con ID " + libro.getId() + " actualizado correctamente.");

        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar libro: " + e.getMessage());
        }
    }
}