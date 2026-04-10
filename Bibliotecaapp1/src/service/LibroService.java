package service;

import dao.LibroDAO;
import model.Libro;
import java.util.ArrayList;

public class LibroService {

    private LibroDAO libroDAO = new LibroDAO();

    public boolean guardarLibro(String titulo, String autor, String imagen, String resena) {
        try {
            if (titulo == null || titulo.trim().isEmpty()) return false;
            if (autor  == null || autor.trim().isEmpty())  return false;
            Libro libro = new Libro(titulo.trim(), autor.trim());
            libro.setImagen(imagen != null ? imagen.trim() : "");
            libro.setResena(resena != null ? resena.trim() : "");
            libroDAO.guardarLibro(libro);
            return true;
        } catch (Exception e) {
            System.out.println("Error LibroService (guardar): " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Libro> obtenerLibros() {
        return libroDAO.listarLibros();
    }

    /** Solo libros disponibles para préstamo */
    public ArrayList<Libro> obtenerDisponibles() {
        return libroDAO.listarDisponibles();
    }

    /** Busca libros por título (ignora mayúsculas) */
    public ArrayList<Libro> buscarPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) return obtenerLibros();
        return libroDAO.buscarPorTitulo(titulo.trim());
    }

    public boolean actualizarLibro(Libro libro) {
        try {
            if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()) return false;
            if (libro.getAutor()  == null || libro.getAutor().trim().isEmpty())  return false;
            libroDAO.actualizarLibro(libro);
            return true;
        } catch (Exception e) {
            System.out.println("Error LibroService (actualizar): " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarLibro(int id) {
        try { libroDAO.eliminarLibro(id); return true; }
        catch (Exception e) { System.out.println("Error LibroService (eliminar): " + e.getMessage()); return false; }
    }

    public void actualizarDisponibilidad(int idLibro, boolean disponible) {
        libroDAO.actualizarDisponibilidad(idLibro, disponible);
    }
}
