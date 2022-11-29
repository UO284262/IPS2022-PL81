package mvc.controlador;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mvc.modelo.DataBaseManagement;
import mvc.modelo.curso.ImparticionDTO;
import mvc.modelo.tercero.ProfesorDTO;

public class ConfigurarActividadControler {
	
	private DataBaseManagement db;
	
	public ConfigurarActividadControler(DataBaseManagement db) {
		this.db = db;
	}
	
	public Connection getConn() {
		return this.db.getConn();
	}

	public static List<ProfesorDTO> toProfesorList(ResultSet rs) throws SQLException {
		List<ProfesorDTO> profesores = new ArrayList<ProfesorDTO>();
		while(rs.next())
		{
			ProfesorDTO profesor = new ProfesorDTO();
			profesor.profesor = rs.getString(2);
			profesor.especialidad = rs.getString(3);
			profesor.dni = rs.getString(1);
			profesores.add(profesor);
		}
		return profesores;
	}

	public static List<ImparticionDTO> toImparticionesList(ResultSet rs) throws SQLException {
		List<ImparticionDTO> imparticiones = new ArrayList<ImparticionDTO>();
		while(rs.next())
		{
			ImparticionDTO imparticion = new ImparticionDTO();
			imparticion.fecha = rs.getDate(2);
			imparticion.curso = rs.getString(1);
			imparticion.hora = rs.getString(3);
			imparticion.duracion = rs.getInt(4);
			imparticiones.add(imparticion);
		}
		return imparticiones;
	}
	
	public List<Date> getDatesFor(String curso)
	{
		return db.findImparticionesFor(curso);
	}
	
	public boolean añadirSesion(String curso, Date fecha, String hora, int duracion)
	{
		return db.configurarImparticion(curso, fecha, hora, duracion);
	}
	
	public List<ProfesorDTO> getAllProfesores()
	{
		return db.findAllProfesor();
	}
	
	public boolean asignarProfesor(String curso, String dni, String profesor)
	{
		return db.addProfesorCurso(curso, dni, profesor);
	}
	
	public void finalizar() {
		db.finalizar();
	}
	
	public void cancelar() {
		db.cancelar();
	}

	public List<String> getColectivos() {
		return db.getColectivos();
	}
}
