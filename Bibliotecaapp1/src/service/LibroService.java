package service;

import dao.LibroDAO;
import model.Libro;
import java.util.ArrayList;

public class LibroService {

    private LibroDAO libroDAO = new LibroDAO();

    // 🔹 Guardar un libro nuevo
    public boolean guardarLibro(String titulo, String autor) {
        try {
            // Crear objeto libro (disponible por defecto)
            Libro libro = new Libro(titulo, autor);
            libroDAO.guardarLibro(libro);
            return true;
        } catch (Exception e) {
            System.out.println("Error en LibroService (guardarLibro): " + e.getMessage());
            return false;
        }
    }

    // 🔹 Obtener lista de libros
    public ArrayList<Libro> obtenerLibros() {
        return libroDAO.listarLibros();
    }

    // 🔹 Actualizar un libro existente
    public boolean actualizarLibro(Libro libro) {
        try {
            libroDAO.actualizarLibro(libro);
            return true;
        } catch (Exception e) {
            System.out.println("Error en LibroService (actualizarLibro): " + e.getMessage());
            return false;
        }
    }

    // 🔹 Eliminar un libro por ID
    public boolean eliminarLibro(int idLibro) {
        try {
            libroDAO.eliminarLibro(idLibro);
            return true;
        } catch (Exception e) {
            System.out.println("Error en LibroService (eliminarLibro): " + e.getMessage());
            return false;
        }
    }
}