package controller;

import java.util.ArrayList;
import dao.LibroDAO;
import dao.UsuarioDAO;
import dao.PrestamoDAO;
import model.Libro;
import model.Usuario;
import model.Prestamo;
import service.PrestamoService;
public class BibliotecaController {

    private LibroDAO libroDAO = new LibroDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private PrestamoDAO prestamoDAO = new PrestamoDAO();
    
    private service.UsuarioService usuarioService = new service.UsuarioService();
    //se agrega por el service 
    
    private service.LibroService libroService = new service.LibroService();

    // ==============================
    // LIBROS
    // ==============================

    // Agregar un libro nuevo , lo comenmos para cambiar a servie 
    
    /*public void agregarLibro(String titulo, String autor) {
        Libro libro = new Libro(titulo, autor);
        libroDAO.guardarLibro(libro); 
    }*/
    
    public boolean agregarLibro(String titulo, String autor) {
        return libroService.guardarLibro(titulo, autor);
    }
    
    

    // Obtener lista de libros , agregando service 
   /* public ArrayList<Libro> obtenerLibros() {
        return libroDAO.listarLibros();
    } */
    public ArrayList<Libro> obtenerLibros() {
        return libroService.obtenerLibros();
    }
    
    

    // ==============================
    // USUARIOS
    // ==============================

    // Agregar un usuario nuevo , se coemnta para agregar service 
    
    public boolean agregarUsuario(String nombre, String correo) {
        return usuarioService.guardarUsuario(nombre, correo);
    }
    
    
    
    /*public void agregarUsuario(String nombre, String correo) {
        Usuario usuario = new Usuario(nombre, correo);
        usuarioDAO.guardarUsuario(usuario);
    } */

    // Obtener lista de usuarios


    
   /* public ArrayList<Usuario> obtenerUsuarios() {
        return usuarioDAO.listarUsuarios();
    } */
    
    public ArrayList<Usuario> obtenerUsuarios() {
        return usuarioService.obtenerUsuarios();
    }

    

    // ==============================
    // PRÉSTAMOS
    // ==============================

    // Prestar un libro a un usuario
    public boolean prestarLibro(int idLibro, int idUsuario) {
        // Verificar si el libro está disponible
        ArrayList<Libro> libros = libroDAO.listarLibros();
        Libro libro = null;
        for (Libro l : libros) {
            if (l.getId() == idLibro) {
                libro = l;
                break;
            }
        }

        if (libro != null && libro.isDisponible()) {
        	
        	
        	PrestamoService service = new PrestamoService();
        	return service.prestarLibro(idLibro, idUsuario);
        	
            // Registrar préstamo , se puso comentario ya que se creo service
            //prestamoDAO.registrarPrestamo(idLibro, idUsuario);
            // Actualizar disponibilidad
           // libroDAO.actualizarDisponibilidad(idLibro, false);
           // return true;
        } else {
            return false; // libro no disponible
        }
    }

    // Listar todos los préstamos
    public ArrayList<Prestamo> obtenerPrestamos() {
        return prestamoDAO.listarPrestamos();
    }
    
    // se comenta para usar service 
    
   /* public void eliminarLibro(int idLibro) {
        libroDAO.eliminarLibro(idLibro); 
    }*/
    
    public boolean eliminarLibro(int idLibro) {
        return libroService.eliminarLibro(idLibro);
    }
    
    //se agrega el service 
   /* public void actualizarLibro(Libro libro) {
        libroDAO.actualizarLibro(libro);
    }*/
    
    public boolean actualizarLibro(Libro libro) {
        return libroService.actualizarLibro(libro);
    }
    
    
    public String login(String usuario, String password) {
        return usuarioService.validarUsuario(usuario, password);
    }
    
    
   /* public String login(String usuario, String password) {
        dao.UsuarioDAO dao = new dao.UsuarioDAO();
        return dao.validar(usuario, password);
    } */
    
    public int contarLibros() {
        return libroService.obtenerLibros().size();
    }

    public int contarUsuarios() {
        return usuarioService.obtenerUsuarios().size();
    }

    public int contarPrestamos() {
        return prestamoDAO.listarPrestamos().size();
    }
}