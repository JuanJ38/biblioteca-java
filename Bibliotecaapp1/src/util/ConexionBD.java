package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // Cadena de conexión con usuario y contraseña
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=BibliotecaDB;encrypt=false";
    private static final String USER = "sa";
    private static final String PASSWORD = "2025root";

    // Método para obtener la conexión
    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error de conexión a BD: " + e.getMessage());
            return null;
        }
    }
}