package model;

import java.util.Date;


public class Prestamo {



    private int id;           // ID del préstamo en la BD
    private int idLibro;      // ID del libro prestado
    private int idUsuario;    // ID del usuario que tomó el libro
    private Date fechaPrestamo;   // Fecha del préstamo
    private Date fechaDevolucion; // Fecha de devolución (null si no se ha devuelto)

    // Constructor completo (al traer datos de la BD)
    public Prestamo(int id, int idLibro, int idUsuario, Date fechaPrestamo, Date fechaDevolucion) {
        this.id = id;
        this.idLibro = idLibro;
        this.idUsuario = idUsuario;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    // Constructor para registrar un préstamo nuevo
    public Prestamo(int idLibro, int idUsuario) {
        this.idLibro = idLibro;
        this.idUsuario = idUsuario;
        this.fechaPrestamo = new Date(); // fecha actual
    }

    // Getters y setters
    public int getId() { return id; }
    public int getIdLibro() { return idLibro; }
    public int getIdUsuario() { return idUsuario; }
    public Date getFechaPrestamo() { return fechaPrestamo; }
    public Date getFechaDevolucion() { return fechaDevolucion; }

    public void setId(int id) { this.id = id; }
    public void setIdLibro(int idLibro) { this.idLibro = idLibro; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public void setFechaPrestamo(Date fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }
    public void setFechaDevolucion(Date fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }

    @Override
    public String toString() {
        return "Prestamo [ID=" + id + ", LibroID=" + idLibro + ", UsuarioID=" + idUsuario +
                ", Prestamo=" + fechaPrestamo + ", Devolucion=" + fechaDevolucion + "]";
    }
	
	
}
