package controller;

import java.util.ArrayList;
import dao.LibroDAO;
import dao.PrestamoDAO;
import model.Libro;
import model.Usuario;
import model.Prestamo;
import service.PrestamoService;
import service.UsuarioService;
import service.LibroService;

public class BibliotecaController {

    private final LibroService    libroService    = new LibroService();
    private final UsuarioService  usuarioService  = new UsuarioService();
    private final PrestamoService prestamoService = new PrestamoService();

    // ── LIBROS ──────────────────────────────────────────────────────
    public boolean agregarLibro(String titulo, String autor, String imagen, String resena) {
        return libroService.guardarLibro(titulo, autor, imagen, resena);
    }
    public boolean agregarLibro(String titulo, String autor) {
        return libroService.guardarLibro(titulo, autor, "", "");
    }
    public ArrayList<Libro> obtenerLibros() {
        return libroService.obtenerLibros();
    }
    /** Solo libros disponibles (para combo de préstamo) */
    public ArrayList<Libro> obtenerLibrosDisponibles() {
        return libroService.obtenerDisponibles();
    }
    /** Búsqueda por título desde base de datos */
    public ArrayList<Libro> buscarLibrosPorTitulo(String titulo) {
        return libroService.buscarPorTitulo(titulo);
    }
    public boolean actualizarLibro(Libro l) {
        return libroService.actualizarLibro(l);
    }
    public boolean eliminarLibro(int id) {
        return libroService.eliminarLibro(id);
    }

    // ── USUARIOS ────────────────────────────────────────────────────
    public boolean agregarUsuario(String nombre, String correo) {
        return usuarioService.guardarUsuario(nombre, correo);
    }
    public ArrayList<Usuario> obtenerUsuarios() {
        return usuarioService.obtenerUsuarios();
    }
    public boolean eliminarUsuario(int id) {
        return usuarioService.eliminarUsuario(id);
    }
    /** Busca usuario por correo (equivalente a findByCorreo) */
    public Usuario buscarUsuarioPorCorreo(String correo) {
        return usuarioService.buscarPorCorreo(correo);
    }

    // ── PRÉSTAMOS ───────────────────────────────────────────────────
    /** Registra un préstamo verificando disponibilidad (transaccional) */
    public boolean prestarLibro(int idLibro, int idUsuario) {
        return prestamoService.prestarLibro(idLibro, idUsuario);
    }
    /** Registra devolución y libera el libro */
    public boolean devolverLibro(int idPrestamo, int idLibro) {
        return prestamoService.devolverLibro(idPrestamo, idLibro);
    }
    /** Historial completo de préstamos */
    public ArrayList<Prestamo> obtenerPrestamos() {
        return prestamoService.obtenerTodos();
    }
    /** Solo préstamos activos (sin fecha de devolución) */
    public ArrayList<Prestamo> obtenerPrestamosActivos() {
        return prestamoService.obtenerActivos();
    }
    /** Préstamos de un usuario específico */
    public ArrayList<Prestamo> obtenerPrestamosPorUsuario(int idUsuario) {
        return prestamoService.obtenerPorUsuario(idUsuario);
    }

    // ── ESTADÍSTICAS ─────────────────────────────────────────────
    public int contarLibros()    { return libroService.obtenerLibros().size(); }
    public int contarUsuarios()  { return usuarioService.obtenerUsuarios().size(); }
    /** Muestra préstamos ACTIVOS en el dashboard (como Spring Boot) */
    public int contarPrestamos() { return prestamoService.contarActivos(); }

    // ── LOGIN ────────────────────────────────────────────────────
    public String login(String usuario, String password) {
        return usuarioService.validarUsuario(usuario, password);
    }
}
