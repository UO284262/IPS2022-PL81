package abel.controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import abel.modelo.ActividadFormativaDTO;
import abel.modelo.ColegiadoInscritoDTO;
import abel.modelo.DataBaseManagement;

public class VisualizarInscritosCursoControler {
	
	public List<ActividadFormativaDTO> getModeloActividades()
	{
		return DataBaseManagement.getActividadesFormativasFrom(LocalDate.now().getYear());
	}
	
	public List<ColegiadoInscritoDTO> getListaApuntados(String nombre)
	{
		return DataBaseManagement.getInscritosEn(nombre);
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
	
	public static List<ColegiadoInscritoDTO> toApuntadoList(ResultSet rs) throws SQLException
	{
		List<ColegiadoInscritoDTO> apuntados = new ArrayList<ColegiadoInscritoDTO>();
		while(rs.next())
		{
			ColegiadoInscritoDTO colegiado = new ColegiadoInscritoDTO();
			colegiado.apellidos = rs.getString(2);
			colegiado.nombre = rs.getString(1);
			colegiado.fecha_inscripcion = LocalDate.parse(rs.getString(3));
			colegiado.estado = rs.getString(4);
			colegiado.cantidad_abonada = rs.getDouble(5);
			apuntados.add(colegiado);
		}
		return apuntados;
	}
}
