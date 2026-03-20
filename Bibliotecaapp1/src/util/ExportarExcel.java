package util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import model.Libro;

public class ExportarExcel {

	public static void exportarLibros(ArrayList<Libro> libros) {

	    try {
	        FileWriter writer = new FileWriter("libros.csv");

	        // Encabezados
	        writer.append("ID;Titulo;Autor;Disponible\n");

	        // Datos
	        for (Libro l : libros) {
	            writer.append(l.getId() + ";");
	            writer.append("\"" + l.getTitulo() + "\";");
	            writer.append("\"" + l.getAutor() + "\";");
	            writer.append(l.isDisponible() ? "SI" : "NO");
	            writer.append("\n");
	        }

	        writer.close();

	        System.out.println("CSV generado correctamente.");

	    } catch (IOException e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	}
}