package mvc.controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import mvc.modelo.ActividadFormativaDTO;
import mvc.modelo.DataBaseManagement;

public class CanceladorCursoControler {
	
	private DataBaseManagement db = new DataBaseManagement();
	
	public static List<ActividadFormativaDTO> toDTOList(ResultSet rs)
	{
		List<ActividadFormativaDTO> lista = new ArrayList<ActividadFormativaDTO>();
		try {
			while(rs.next())
			{
				ActividadFormativaDTO af = new ActividadFormativaDTO();
				af.title = rs.getString(1);
				af.price= rs.getDouble(2);
				af.fecha_orientativa = LocalDate.parse(rs.getString(3));
				af.is_open = rs.getBoolean(4);
				af.fin_inscripcion = LocalDate.parse(rs.getString(5));
				af.numeroPlazas = rs.getInt(6);
				lista.add(af);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public void cancelarCurso(String nombre_curso) {
		db.cancelarApuntados(nombre_curso);
		db.cancelarCurso(nombre_curso);
	}
	
	public List<ActividadFormativaDTO> getCurrentCursos() {
		return this.db.getAllCurrentCursos();
	}
	
	public void finalizar() {
		db.finalizar();
	}
	
	public void cancelar() {
		db.cancelar();
	}
	
	
}
