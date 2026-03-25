package service;

import dao.LibroDAO;
import dao.PrestamoDAO;

public class PrestamoService {

    private PrestamoDAO prestamoDAO = new PrestamoDAO();
    private LibroDAO libroDAO = new LibroDAO();

    public boolean prestarLibro(int idLibro, int idUsuario) {

        try {
            // 1. Registrar préstamo
            prestamoDAO.registrarPrestamo(idLibro, idUsuario);

            // 2. Actualizar disponibilidad
            libroDAO.actualizarDisponibilidad(idLibro, false);

            return true;

        } catch (Exception e) {
            System.out.println("Error en service: " + e.getMessage());
            return false;
        }
    }
}