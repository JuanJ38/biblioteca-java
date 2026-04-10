package service;

import dao.LibroDAO;
import dao.PrestamoDAO;
import model.Prestamo;
import java.util.ArrayList;

public class PrestamoService {

    private PrestamoDAO prestamoDAO = new PrestamoDAO();
    private LibroDAO    libroDAO    = new LibroDAO();

    /** Registra un préstamo y marca el libro como no disponible (atómico) */
    public boolean prestarLibro(int idLibro, int idUsuario) {
        try {
            // Verificar disponibilidad antes de registrar
            ArrayList<model.Libro> libros = libroDAO.listarLibros();
            for (model.Libro l : libros) {
                if (l.getId() == idLibro) {
                    if (!l.isDisponible()) return false;
                    break;
                }
            }
            prestamoDAO.registrarPrestamo(idLibro, idUsuario);
            libroDAO.actualizarDisponibilidad(idLibro, false);
            return true;
        } catch (Exception e) {
            System.out.println("Error PrestamoService (prestar): " + e.getMessage());
            return false;
        }
    }

    /** Registra la devolución y libera el libro */
    public boolean devolverLibro(int idPrestamo, int idLibro) {
        try {
            boolean ok = prestamoDAO.devolverLibro(idPrestamo);
            if (ok) libroDAO.actualizarDisponibilidad(idLibro, true);
            return ok;
        } catch (Exception e) {
            System.out.println("Error PrestamoService (devolver): " + e.getMessage());
            return false;
        }
    }

    /** Historial completo de préstamos */
    public ArrayList<Prestamo> obtenerTodos() {
        return prestamoDAO.listarPrestamos();
    }

    /** Solo préstamos activos (no devueltos) */
    public ArrayList<Prestamo> obtenerActivos() {
        return prestamoDAO.listarActivos();
    }

    /** Préstamos de un usuario específico */
    public ArrayList<Prestamo> obtenerPorUsuario(int idUsuario) {
        return prestamoDAO.listarPorUsuario(idUsuario);
    }

    /** Cuenta préstamos activos para el dashboard */
    public int contarActivos() {
        return prestamoDAO.contarActivos();
    }
}
