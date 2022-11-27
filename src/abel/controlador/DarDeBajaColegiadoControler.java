package abel.controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import abel.modelo.DataBaseManagement;

public class DarDeBajaColegiadoControler {
	
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
	
	public double getRecibosPendientes(String id_colegiado)
	{
		return db.amountAPagar(id_colegiado);
	}
	
	public boolean darDeBaja(String id_colegiado)
	{
		return db.darDeBaja(id_colegiado);
	}
	
	public void finalizar() {
		db.finalizar();
	}
	
	public void cancelar() {
		db.finalizar();
	}
}
