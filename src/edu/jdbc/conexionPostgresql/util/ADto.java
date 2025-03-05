package edu.jdbc.conexionPostgresql.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.jdbc.conexionPostgresql.dtos.LibroDto;
/**
 * Clase de utilidad que contiene los métodos de paso a DTO
 * 220923 - rfg
 */
public class ADto {
	
	/**
	 * Método que pasa un resultSet con libros a lista de LibroDto
	 * @param resultadoConsulta
	 * 220923 - rfg
	 * @return lista de libros
	 */
	public ArrayList<LibroDto> resultsALibrosDto(ResultSet resultadoConsulta){
		
		ArrayList<LibroDto> listaLibros = new ArrayList<>();
		
		//Leemos el resultado de la consulta hasta que no queden filas
		while (resultadoConsulta.next()) {
				
			listaLibros.add(new LibroDto(resultadoConsulta.getLong("id_libro"),
					resultadoConsulta.getString("titulo"),
					resultadoConsulta.getString("autor"),
					resultadoConsulta.getString("isbn"),
					resultadoConsulta.getInt("edicion"))
					);
		}
			
		int i = listaLibros.size();
		System.out.println("[INFORMACIÓN-ADto-resultsALibrosDto] Número libros: "+i);
			
		
		return listaLibros;
		
	}
}
