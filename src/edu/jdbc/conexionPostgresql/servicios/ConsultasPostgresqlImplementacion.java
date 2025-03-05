package edu.jdbc.conexionPostgresql.servicios;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import edu.jdbc.conexionPostgresql.dtos.LibroDto;
import edu.jdbc.conexionPostgresql.util.ADto;

/**
 * Implementación de la interfaz de consultas sobre Postgresql
 * 220923-rfg
 */
public class ConsultasPostgresqlImplementacion implements ConsultasPostgresqlInterfaz {

	@Override
	public ArrayList<LibroDto> seleccionaTodosLibros(Connection conexionGenerada) {
		
		Statement declaracionSQL = null;
		ResultSet resultadoConsulta = null;
		ArrayList<LibroDto> listaLibros = new ArrayList<>();
		ADto adto = new ADto();
					
		try {
            if (conexionGenerada == null) {
                throw new NullPointerException("[ERROR] La conexión a la base de datos es nula.");
            }

            // Se abre una declaración
            declaracionSQL = conexionGenerada.createStatement();

            // Se define la consulta de la declaración y se ejecuta
            resultadoConsulta = declaracionSQL.executeQuery("SELECT * FROM \"gbp_almacen\".\"gbp_alm_cat_libros\"");

            // Llamada a la conversión a DTO
            listaLibros = adto.resultsALibrosDto(resultadoConsulta);
            int i = listaLibros.size();
            System.out.println("[INFORMACIÓN] Número de libros: " + i);

        } catch (SQLException e) {
            System.err.println("[ERROR] Error al ejecutar la consulta SQL: ");
            
        } catch (NullPointerException e) {
            System.err.println("[ERROR]");
        } catch (Exception e) {
            System.err.println("[ERROR] Se produjo un error inesperado: ");
        }
      
            try {
                if (resultadoConsulta != null) resultadoConsulta.close();
                if (declaracionSQL != null) declaracionSQL.close();
                if (conexionGenerada != null) conexionGenerada.close();
                System.out.println("[INFORMACIÓN] Conexión, declaración y resultado cerrados correctamente.");
            } catch (SQLException e) {
                System.err.println("[ERROR] Error al cerrar los recursos: ");
               
            }
        

        return listaLibros;
    }
}