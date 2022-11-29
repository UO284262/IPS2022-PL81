package abel.controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import abel.modelo.ActividadFormativaDTO;
import abel.modelo.ColegiadoSolicitadoDTO;
import abel.modelo.DataBaseManagement;
import abel.modelo.FileUtil;

public class ComprobarTitulacionColegiadoControler {
	
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
	
	public List<ColegiadoSolicitadoDTO> getListaRecibidos() {
		return FileUtil.ReadFile("recibirTitulacion.csv", ",");
	}
	
	public void finalizar() {
		db.finalizar();
	}
	
	public void cancelar() {
		db.cancelar();
	}

	public void setEstado(String dni, String estado) {
		db.setEstado(dni, estado);
	}

	public void eleminarFichero() {
		FileUtil.deleteRecibidos();
	}
	
	
}
