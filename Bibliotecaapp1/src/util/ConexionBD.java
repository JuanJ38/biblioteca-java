package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de utilidad para gestionar la conexión a la base de datos SQL Server.
 */
public class ConexionBD {

    // Configuración de la cadena de conexión
    private static final String URL = "jdbc:sqlserver://localhost:1433;"
            + "databaseName=BibliotecaDB;"
            + "encrypt=false;"
            + "trustServerCertificate=true;";
    
    private static final String USER = "sa";
    private static final String PASSWORD = "2025root"; 

    /**
     * Establece y retorna una conexión con SQL Server.
     * @return Connection objeto de conexión o null si falla.
     */
    public static Connection conectar() {
        Connection cn = null;
        try {
            // Carga opcional del driver (recomendado en entornos antiguos o manuales)
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            cn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a BibliotecaDB");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el driver de SQL Server: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error de conexión a la BD: " + e.getMessage());
        }
        return cn;
    }
}