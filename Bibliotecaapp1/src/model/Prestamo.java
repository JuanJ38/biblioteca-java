package model;

import java.util.Date;

public class Prestamo {

    private int    id;
    private int    idLibro;
    private int    idUsuario;
    private Date   fechaPrestamo;
    private Date   fechaDevolucion;
    private String tituloLibro;
    private String nombreUsuario;

    public Prestamo(int id, int idLibro, int idUsuario, Date fechaPrestamo, Date fechaDevolucion) {
        this.id = id; this.idLibro = idLibro; this.idUsuario = idUsuario;
        this.fechaPrestamo = fechaPrestamo; this.fechaDevolucion = fechaDevolucion;
    }

    public Prestamo(int idLibro, int idUsuario) {
        this.idLibro = idLibro; this.idUsuario = idUsuario;
        this.fechaPrestamo = new Date();
    }

    public int    getId()              { return id; }
    public int    getIdLibro()         { return idLibro; }
    public int    getIdUsuario()       { return idUsuario; }
    public Date   getFechaPrestamo()   { return fechaPrestamo; }
    public Date   getFechaDevolucion() { return fechaDevolucion; }
    public String getTituloLibro()     { return tituloLibro  != null ? tituloLibro  : "ID " + idLibro; }
    public String getNombreUsuario()   { return nombreUsuario != null ? nombreUsuario : "ID " + idUsuario; }

    public void setId(int id)                       { this.id = id; }
    public void setIdLibro(int v)                   { this.idLibro = v; }
    public void setIdUsuario(int v)                 { this.idUsuario = v; }
    public void setFechaPrestamo(Date d)            { this.fechaPrestamo = d; }
    public void setFechaDevolucion(Date d)          { this.fechaDevolucion = d; }
    public void setTituloLibro(String s)            { this.tituloLibro = s; }
    public void setNombreUsuario(String s)          { this.nombreUsuario = s; }

    @Override
    public String toString() {
        return "Prestamo[" + id + "]";
    }
}
