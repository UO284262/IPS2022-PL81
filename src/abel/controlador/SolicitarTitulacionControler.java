package abel.controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import abel.modelo.ColegiadoInscritoDTO;
import abel.modelo.DataBaseManagement;

public class SolicitarTitulacionControler {
	public static List<String[]> toStringArray(ResultSet rs)
	{
		List<String[]> lista = new ArrayList<String[]>();
		try {
			while(rs.next())
			{
				String[] elemento = new String[2];
				elemento[0] = rs.getString("dni");
				elemento[1] = rs.getString("nombre");
				lista.add(elemento);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}
	
	public static String[] toStringElement(ResultSet rs)
	{
		try {
			if(rs.next())
			{
				String[] elemento = new String[2];
				elemento[0] = rs.getString("dni");
				elemento[1] = rs.getString("nombre");
				return elemento;
			}
		} catch (SQLException e) {
		}
		return null;
	}
	
	public List<ColegiadoInscritoDTO> getListaPendientes()
	{
		return DataBaseManagement.getAllColegiadosPendientes();
	}
	
	public ColegiadoInscritoDTO getPendiente(String dni)
	{
		return DataBaseManagement.getColegiadoPendienteByDni(dni);
	}
	
	public List<String[]> getPendientes(List<String> dnis)
	{
		return DataBaseManagement.getColegiadosPendientes(dnis);
	}
	
	public static List<ColegiadoInscritoDTO> toApuntadoList(ResultSet rs) throws SQLException
	{
		List<ColegiadoInscritoDTO> apuntados = new ArrayList<ColegiadoInscritoDTO>();
		while(rs.next())
		{
			ColegiadoInscritoDTO colegiado = new ColegiadoInscritoDTO();
			colegiado.dni = rs.getString(1);
			colegiado.nombre = rs.getString(2);
			colegiado.apellidos = rs.getString(3);
			colegiado.telefono = rs.getInt(4);
			apuntados.add(colegiado);
		}
		return apuntados;
	}
	
	public void setValidando(List<String> dnis)
	{
		DataBaseManagement.setValidando(dnis);
	}
}
