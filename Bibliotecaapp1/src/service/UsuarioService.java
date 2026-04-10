package service;

import dao.UsuarioDAO;
import model.Usuario;
import java.util.ArrayList;

public class UsuarioService {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    /** Guarda un usuario con validación de campos */
    public boolean guardarUsuario(String nombre, String correo) {
        try {
            if (nombre == null || nombre.trim().isEmpty()) return false;
            if (correo == null || correo.trim().isEmpty()) return false;
            // Validación básica de correo (igual que @Email en Spring)
            if (!correo.contains("@") || !correo.contains(".")) return false;
            Usuario usuario = new Usuario(nombre.trim(), correo.trim().toLowerCase());
            return usuarioDAO.guardarUsuario(usuario);
        } catch (Exception e) {
            System.out.println("Error en UsuarioService (guardarUsuario): " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Usuario> obtenerUsuarios() {
        return usuarioDAO.listarUsuarios();
    }

    public String validarUsuario(String usuario, String password) {
        try {
            return usuarioDAO.validar(usuario, password);
        } catch (Exception e) {
            System.out.println("Error en UsuarioService (validarUsuario): " + e.getMessage());
            return null;
        }
    }

    public boolean eliminarUsuario(int idUsuario) {
        try {
            return usuarioDAO.eliminarUsuario(idUsuario);
        } catch (Exception e) {
            System.out.println("Error en UsuarioService (eliminarUsuario): " + e.getMessage());
            return false;
        }
    }

    /** Busca usuario por correo (igual que findByCorreo en Spring) */
    public Usuario buscarPorCorreo(String correo) {
        try {
            return usuarioDAO.buscarPorCorreo(correo);
        } catch (Exception e) {
            System.out.println("Error en UsuarioService (buscarPorCorreo): " + e.getMessage());
            return null;
        }
    }
}
