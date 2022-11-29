package mvc.controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mvc.modelo.ColegiadoInscritoDTO;
import mvc.modelo.DataBaseManagement;

public class SolicitarTitulacionControler {
	
	private DataBaseManagement db = new DataBaseManagement();
	
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
		return db.getAllColegiadosPendientes();
	}
	
	public ColegiadoInscritoDTO getPendiente(String dni)
	{
		return db.getColegiadoPendienteByDni(dni);
	}
	
	public List<String[]> getPendientes(List<String> dnis)
	{
		return db.getColegiadosPendientes(dnis);
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
		db.setValidando(dnis);
	}
	
	public void finalizar() {
		db.finalizar();
	}
	
	public void cancelar() {
		db.cancelar();
	}
}
