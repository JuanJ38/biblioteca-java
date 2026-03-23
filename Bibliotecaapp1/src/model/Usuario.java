package model;

public class Usuario {
	
	private int id;        // ID en la BD
    private String nombre; // Nombre del usuario
    private String correo; // Correo del usuario

    // Constructor completo (para leer de BD)
    public Usuario(int id, String nombre, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
    }

    // Constructor sin ID (para crear antes de guardar en BD)
    public Usuario(String nombre, String correo) {
        this.nombre = nombre;
        this.correo = correo;
    }

    // Getters y setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }

    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCorreo(String correo) { this.correo = correo; }

   /*  @Override
    public String toString() {
        return "Usuario [ID=" + id + ", Nombre=" + nombre + ", Correo=" + correo + "]";
    }	*/
    
    @Override
    public String toString() {
        return nombre; // 🔹 lo que se mostrará en el combo
    }

}
