package util;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.Libro;
import model.Prestamo;
import model.Usuario;

public class ExportarExcel {

    /** Exporta el catálogo de libros a CSV */
    public static void exportarLibros(ArrayList<Libro> libros) {
        try (FileWriter writer = new FileWriter("libros.csv")) {
            writer.append("ID;Titulo;Autor;Disponible;Imagen;Resena\n");
            for (Libro l : libros) {
                writer.append(l.getId() + ";");
                writer.append("\"" + escapar(l.getTitulo()) + "\";");
                writer.append("\"" + escapar(l.getAutor())  + "\";");
                writer.append(l.isDisponible() ? "SI;" : "NO;");
                writer.append("\"" + escapar(l.getImagen()) + "\";");
                writer.append("\"" + escapar(l.getResena()) + "\"");
                writer.append("\n");
            }
            System.out.println("CSV de libros generado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al exportar libros: " + e.getMessage());
        }
    }

    /** Exporta el historial de préstamos a CSV */
    public static void exportarPrestamos(ArrayList<Prestamo> prestamos) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try (FileWriter writer = new FileWriter("prestamos.csv")) {
            writer.append("ID;Libro;Usuario;Fecha Prestamo;Fecha Devolucion;Estado\n");
            for (Prestamo p : prestamos) {
                writer.append(p.getId() + ";");
                writer.append("\"" + escapar(p.getTituloLibro())   + "\";");
                writer.append("\"" + escapar(p.getNombreUsuario()) + "\";");
                writer.append((p.getFechaPrestamo()   != null ? fmt.format(p.getFechaPrestamo())   : "") + ";");
                writer.append((p.getFechaDevolucion() != null ? fmt.format(p.getFechaDevolucion()) : "") + ";");
                writer.append(p.getFechaDevolucion() != null ? "Devuelto" : "Activo");
                writer.append("\n");
            }
            System.out.println("CSV de préstamos generado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al exportar préstamos: " + e.getMessage());
        }
    }

    /** Exporta usuarios a CSV */
    public static void exportarUsuarios(ArrayList<Usuario> usuarios) {
        try (FileWriter writer = new FileWriter("usuarios.csv")) {
            writer.append("ID;Nombre;Correo\n");
            for (Usuario u : usuarios) {
                writer.append(u.getId() + ";");
                writer.append("\"" + escapar(u.getNombre()) + "\";");
                writer.append("\"" + escapar(u.getCorreo()) + "\"");
                writer.append("\n");
            }
            System.out.println("CSV de usuarios generado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al exportar usuarios: " + e.getMessage());
        }
    }

    private static String escapar(String s) {
        if (s == null) return "";
        return s.replace("\"", "\"\"");
    }
}
