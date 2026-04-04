package service;

import dao.UsuarioDAO;
import model.Usuario;
import java.util.ArrayList;

public class UsuarioService {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    //  Guardar un usuario nuevo
    public boolean guardarUsuario(String nombre, String correo) {
        try {
            Usuario usuario = new Usuario(nombre, correo);
            usuarioDAO.guardarUsuario(usuario);
            return true;
        } catch (Exception e) {
            System.out.println("Error en UsuarioService (guardarUsuario): " + e.getMessage());
            return false;
        }
    }

    //  Obtener lista de usuarios
    public ArrayList<Usuario> obtenerUsuarios() {
        return usuarioDAO.listarUsuarios();
    }

    // Validar login
    public String validarUsuario(String usuario, String password) {
        try {
            return usuarioDAO.validar(usuario, password);
        } catch (Exception e) {
            System.out.println("Error en UsuarioService (validarUsuario): " + e.getMessage());
            return null;
        }
    }
    
    
 // Eliminar usuario
    public boolean eliminarUsuario(int idUsuario) {
        try {
            return usuarioDAO.eliminarUsuario(idUsuario);
        } catch (Exception e) {
            System.out.println("Error en UsuarioService (eliminarUsuario): " + e.getMessage());
            return false;
        }
    }
}