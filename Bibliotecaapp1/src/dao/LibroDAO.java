package dao;

import java.sql.*;
import java.util.ArrayList;
import model.Libro;
import util.ConexionBD;

public class LibroDAO {

    // Guardar un libro en la BD
    public void guardarLibro(Libro libro) {
        String sql = "INSERT INTO libros (titulo, autor, disponible) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setBoolean(3, libro.isDisponible());

            ps.executeUpdate();
            System.out.println("✅ Libro guardado correctamente.");

        } catch (SQLException e) {
            System.out.println("❌ Error al guardar libro:");
            e.printStackTrace();
        }
    }

    // Listar todos los libros
    public ArrayList<Libro> listarLibros() {
        ArrayList<Libro> lista = new ArrayList<>();
        String sql = "SELECT * FROM libros";

        try (Connection conn = ConexionBD.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Libro libro = new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getBoolean("disponible")
                );
                lista.add(libro);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al listar libros:");
            e.printStackTrace();
        }

        return lista;
    }

    // Actualizar disponibilidad del libro (prestar o devolver)
    public void actualizarDisponibilidad(int idLibro, boolean disponible) {
        String sql = "UPDATE libros SET disponible = ? WHERE id = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, disponible);
            ps.setInt(2, idLibro);

            ps.executeUpdate();
            System.out.println("✅ Disponibilidad actualizada.");

        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar disponibilidad:");
            e.printStackTrace();
        }
    }

    // Eliminar libro
    public void eliminarLibro(int idLibro) {
        String sql = "DELETE FROM libros WHERE id = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idLibro);
            ps.executeUpdate();
            System.out.println("✅ Libro eliminado.");

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar libro:");
            e.printStackTrace();
        }
    }

    // Actualizar libro completo
    public void actualizarLibro(Libro libro) {
        String sql = "UPDATE libros SET titulo = ?, autor = ?, disponible = ? WHERE id = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setBoolean(3, libro.isDisponible());
            ps.setInt(4, libro.getId());

            ps.executeUpdate();
            System.out.println("✅ Libro actualizado.");

        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar libro:");
            e.printStackTrace();
        }
    }
}