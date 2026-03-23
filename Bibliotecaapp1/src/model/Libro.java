package model;

public class Libro {
	
	
	 private int id;           // ID del libro en la BD
	    private String titulo;    // Título del libro
	    private String autor;     // Autor del libro
	    private boolean disponible; // true si está disponible

	    // Constructor completo (para leer de BD)
	    public Libro(int id, String titulo, String autor, boolean disponible) {
	        this.id = id;
	        this.titulo = titulo;
	        this.autor = autor;
	        this.disponible = disponible;
	    }

	    // Constructor sin ID (para crear antes de guardar en BD)
	    public Libro(String titulo, String autor) {
	        this.titulo = titulo;
	        this.autor = autor;
	        this.disponible = true; // por defecto disponible
	    }

	    // Getters y setters
	    public int getId() { return id; }
	    public String getTitulo() { return titulo; }
	    public String getAutor() { return autor; }
	    public boolean isDisponible() { return disponible; }

	    public void setId(int id) { this.id = id; }
	    public void setTitulo(String titulo) { this.titulo = titulo; }
	    public void setAutor(String autor) { this.autor = autor; }
	    public void setDisponible(boolean disponible) { this.disponible = disponible; }

	   /* @Override
	    public String toString() {
	        return "Libro [ID=" + id + ", Título=" + titulo + ", Autor=" + autor + ", Disponible=" + disponible + "]";
	    }*/
	
	    @Override
	    public String toString() {
	        return titulo; // 🔹 lo que se mostrará en el combo
	    }
	
	
	
	
	
	

}
