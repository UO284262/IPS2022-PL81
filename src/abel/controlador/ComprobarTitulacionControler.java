package abel.controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import abel.modelo.ColegiadoSolicitadoDTO;
import abel.modelo.DataBaseManagement;
import abel.modelo.FileUtil;

public class ComprobarTitulacionControler {
	
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
	
	public List<ColegiadoSolicitadoDTO> getListaRecibidos()
	{
		return FileUtil.ReadFile("recibirTitulacion.csv", ",");
	}
	
	public void setEstado(String dni, String estado)
	{
		db.setEstado(dni,estado);
	}
	
	public void finalizar() {
		db.finalizar();
	}
	
	public void cancelar() {
		db.finalizar();
	}
	
	public void eleminarFichero() {
		FileUtil.deleteRecibidos();
	}
}
