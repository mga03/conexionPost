package edu.jdbc.conexionPostgresql.servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Implementación de la interfaz de conexión a PostgreSQL
 * 220923 - rfg
 */
public class ConexionPostgresqlImplementacion implements ConexionPostgresqlInterfaz {

	private Object conexion;

	@Override
	public Connection generaConexion() {
		
		Connection conexion = null;
		String[] parametrosConexion = configuracionConexion(); //url, user, pass
		
		if(!parametrosConexion[2].isEmpty()) { //Se controla que los parámetros de conexión se completen
			try {
                // Cargar el driver de PostgreSQL
                Class.forName("org.postgresql.Driver");

                // Establecer la conexión
                conexion = DriverManager.getConnection(parametrosConexion[0], parametrosConexion[1], parametrosConexion[2]);

                // Validar la conexión
                boolean esValida = conexion.isValid(50000);
                if (!esValida) {
                    conexion = null;
                }

                System.out.println(esValida 
                    ? "[INFORMACIÓN] Conexión a PostgreSQL válida" 
                    : "[ERROR] Conexión a PostgreSQL no válida");

            } catch (ClassNotFoundException e) {
                System.err.println("[ERROR] No se encontró el driver de PostgreSQL: ");
              
            } catch (SQLException e) {
                System.err.println("[ERROR] No se pudo conectar a la base de datos: ");
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println("[ERROR] Error inesperado en la conexión: ");
                e.printStackTrace();
            }
        } else {
            System.err.println("[ERROR] Los parámetros de conexión no se han establecido correctamente");
            conexion = null;
        }
        return conexion;
    }

    /**
     * Método que configura los parámetros de la conexión desde un archivo de propiedades
     * 221023 - rfg
     * @return Vector de string con: url, user, pass
     */
    private String[] configuracionConexion() throws FileNotFoundException, IOException {
        String user = "", pass = "", port = "", host = "", db = "", url = "";
        String[] stringConfiguracion = {"", "", ""};
        Properties propiedadesConexion = new Properties();

        try  {
            user = propiedadesConexion.getProperty("user");
            pass = propiedadesConexion.getProperty("pass");
            port = propiedadesConexion.getProperty("port");
            host = propiedadesConexion.getProperty("host");
            db = propiedadesConexion.getProperty("db");
            url = "jdbc:postgresql://" + host + ":" + port + "/" + db;

            stringConfiguracion[0] = url;
            stringConfiguracion[1] = user;
            stringConfiguracion[2] = pass;

        } catch (Exception e) {
            System.err.println("[ERROR] Error inesperado al cargar la configuración: ");
            conexion = null;
        }

        return stringConfiguracion;
    }
}
