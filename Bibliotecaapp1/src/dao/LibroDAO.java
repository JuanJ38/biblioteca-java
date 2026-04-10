package dao;

import java.sql.*;
import java.util.ArrayList;
import model.Libro;
import util.ConexionBD;

public class LibroDAO {

    public boolean guardarLibro(Libro libro) {
        String sql = "INSERT INTO libros (titulo, autor, disponible, imagen, resena) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.conectar()) {
            if (conn == null) return false;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, libro.getTitulo());
                ps.setString(2, libro.getAutor());
                ps.setBoolean(3, libro.isDisponible());
                ps.setString(4, libro.getImagen());
                ps.setString(5, libro.getResena());
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar libro: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Libro> listarLibros() {
        ArrayList<Libro> lista = new ArrayList<>();
        String sql = "SELECT id, titulo, autor, disponible, " +
                     "COALESCE(imagen,'') AS imagen, COALESCE(resena,'') AS resena FROM libros ORDER BY id";
        try (Connection conn = ConexionBD.conectar()) {
            if (conn == null) return lista;
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    lista.add(new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getBoolean("disponible"),
                        rs.getString("imagen"),
                        rs.getString("resena")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar libros: " + e.getMessage());
        }
        return lista;
    }

    /** Busca libros cuyo título contiene el texto (ignora mayúsculas/minúsculas) */
    public ArrayList<Libro> buscarPorTitulo(String titulo) {
        ArrayList<Libro> lista = new ArrayList<>();
        String sql = "SELECT id, titulo, autor, disponible, " +
                     "COALESCE(imagen,'') AS imagen, COALESCE(resena,'') AS resena " +
                     "FROM libros WHERE LOWER(titulo) LIKE ? ORDER BY id";
        try (Connection conn = ConexionBD.conectar()) {
            if (conn == null) return lista;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, "%" + titulo.toLowerCase() + "%");
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        lista.add(new Libro(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getBoolean("disponible"),
                            rs.getString("imagen"),
                            rs.getString("resena")
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar libro por título: " + e.getMessage());
        }
        return lista;
    }

    /** Retorna solo los libros disponibles */
    public ArrayList<Libro> listarDisponibles() {
        ArrayList<Libro> lista = new ArrayList<>();
        String sql = "SELECT id, titulo, autor, disponible, " +
                     "COALESCE(imagen,'') AS imagen, COALESCE(resena,'') AS resena " +
                     "FROM libros WHERE disponible = true ORDER BY titulo";
        try (Connection conn = ConexionBD.conectar()) {
            if (conn == null) return lista;
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    lista.add(new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getBoolean("disponible"),
                        rs.getString("imagen"),
                        rs.getString("resena")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar disponibles: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizarDisponibilidad(int idLibro, boolean disponible) {
        String sql = "UPDATE libros SET disponible = ? WHERE id = ?";
        try (Connection conn = ConexionBD.conectar()) {
            if (conn == null) return false;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setBoolean(1, disponible);
                ps.setInt(2, idLibro);
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar disponibilidad: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarLibro(int idLibro) {
        String sql = "DELETE FROM libros WHERE id = ?";
        try (Connection conn = ConexionBD.conectar()) {
            if (conn == null) return false;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idLibro);
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar libro: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarLibro(Libro libro) {
        String sql = "UPDATE libros SET titulo=?, autor=?, disponible=?, imagen=?, resena=? WHERE id=?";
        try (Connection conn = ConexionBD.conectar()) {
            if (conn == null) return false;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, libro.getTitulo());
                ps.setString(2, libro.getAutor());
                ps.setBoolean(3, libro.isDisponible());
                ps.setString(4, libro.getImagen());
                ps.setString(5, libro.getResena());
                ps.setInt(6, libro.getId());
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar libro: " + e.getMessage());
            return false;
        }
    }
}
