package abel.controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import abel.modelo.ActividadFormativaDTO;
import abel.modelo.ApuntadoDTO;
import abel.modelo.DataBaseManagement;

public class VisualizarInscritosCursoControler {
	
	private DataBaseManagement db = new DataBaseManagement();
	
	public List<ActividadFormativaDTO> getModeloActividades()
	{
		return db.getActividadesFormativasFrom(LocalDate.now().getYear());
	}
	
	public List<ApuntadoDTO> getListaApuntados(String nombre)
	{
		return db.getInscritosEn(nombre);
	}
	
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
				lista.add(af);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public static List<ApuntadoDTO> toApuntadoCList(ResultSet rs) throws SQLException
	{
		List<ApuntadoDTO> apuntados = new ArrayList<ApuntadoDTO>();
		while(rs.next())
		{
			ApuntadoDTO colegiado = new ApuntadoDTO();
			colegiado.apellidos = rs.getString(2);
			colegiado.nombre = rs.getString(1);
			colegiado.fecha_inscripcion = LocalDate.parse(rs.getString(3));
			colegiado.dni = rs.getString(6);
			colegiado.estado = rs.getString(4);
			colegiado.cantidad_abonada = rs.getDouble(5);
			colegiado.colectivo = "COLEGIADO";
			apuntados.add(colegiado);
		}
		return apuntados;
	}
	
	public static List<ApuntadoDTO> toApuntadoTList(ResultSet rs) throws SQLException
	{
		List<ApuntadoDTO> apuntados = new ArrayList<ApuntadoDTO>();
		while(rs.next())
		{
			ApuntadoDTO colegiado = new ApuntadoDTO();
			colegiado.dni = rs.getString(2);
			colegiado.nombre = rs.getString(1);
			colegiado.fecha_inscripcion = LocalDate.parse(rs.getString(3));
			colegiado.estado = rs.getString(4);
			colegiado.cantidad_abonada = rs.getDouble(5);
			colegiado.colectivo = rs.getString(6);
			colegiado.apellidos = "No registrado";
			apuntados.add(colegiado);
		}
		return apuntados;
	}

	public double getIngresosFor(String nombre) {
		return db.getIngresosFor(nombre);
	}
	
	public void finalizar() {
		db.finalizar();
	}
	
	public void cancelar() {
		db.finalizar();
	}
}
