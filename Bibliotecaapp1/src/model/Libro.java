package model;

public class Libro {

    private int     id;
    private String  titulo;
    private String  autor;
    private boolean disponible;
    private String  imagen;
    private String  resena;

    public Libro(int id, String titulo, String autor, boolean disponible, String imagen, String resena) {
        this.id = id; this.titulo = titulo; this.autor = autor;
        this.disponible = disponible; this.imagen = imagen; this.resena = resena;
    }

    public Libro(int id, String titulo, String autor, boolean disponible) {
        this(id, titulo, autor, disponible, "", "");
    }

    public Libro(String titulo, String autor) {
        this(0, titulo, autor, true, "", "");
    }

    public int     getId()        { return id; }
    public String  getTitulo()    { return titulo; }
    public String  getAutor()     { return autor; }
    public boolean isDisponible() { return disponible; }
    public String  getImagen()    { return imagen != null ? imagen : ""; }
    public String  getResena()    { return resena != null ? resena : ""; }

    public void setId(int id)            { this.id = id; }
    public void setTitulo(String t)      { this.titulo = t; }
    public void setAutor(String a)       { this.autor = a; }
    public void setDisponible(boolean d) { this.disponible = d; }
    public void setImagen(String i)      { this.imagen = i; }
    public void setResena(String r)      { this.resena = r; }

    @Override
    public String toString() { return titulo; }
}
