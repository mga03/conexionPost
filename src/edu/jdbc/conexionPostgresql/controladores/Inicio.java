package edu.jdbc.conexionPostgresql.controladores;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.jdbc.conexionPostgresql.dtos.LibroDto;
import edu.jdbc.conexionPostgresql.servicios.ConexionPostgresqlImplementacion;
import edu.jdbc.conexionPostgresql.servicios.ConexionPostgresqlInterfaz;
import edu.jdbc.conexionPostgresql.servicios.ConsultasPostgresqlImplementacion;
import edu.jdbc.conexionPostgresql.servicios.ConsultasPostgresqlInterfaz;

/**
 * Clase principal de la aplicación
 * 220923 - rfg
 */
public class Inicio {

	/**
	 * Método de acceso a la aplicación de consola
	 * @param args
	 */
	public static void main(String[] args) throws SQLException {
		
		ConexionPostgresqlInterfaz cpi = new ConexionPostgresqlImplementacion();
		ConsultasPostgresqlInterfaz consultaspi = new ConsultasPostgresqlImplementacion();
		ArrayList<LibroDto> listaLibros = new ArrayList<>();
		   Connection conexion = null;
		try {
	            conexion = cpi.generaConexion();
	       
	            if (conexion != null) {
	                listaLibros = consultaspi.seleccionaTodosLibros(conexion);
	                
	                for (LibroDto libro : listaLibros) {
	                    System.out.println(libro.toString());
	                }
	            } else {
	                System.out.println("Error: No se pudo establecer conexión con la base de datos.");
	            }

	        } catch (NullPointerException e) {
	            System.out.println("Error: Se intentó acceder a un objeto nulo. " + e.getMessage());
	            e.printStackTrace();
	        } catch (Exception e) {
	            System.out.println("Error inesperado: " + e.getMessage());
	            e.printStackTrace();
	        } finally {
	            
	            try {
	                if (conexion != null) {
	                    conexion.close();
	                    System.out.println("Conexión cerrada correctamente.");
	                }
	            } catch (SQLException e) {
	                System.out.println("Error al cerrar la conexión: " + e.getMessage());
	                e.printStackTrace();
	            }
	        }
	    }
	}
