package kike.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kike.modelo.curso.CursoDTO;
import main.DatabaseConnection;

public class CursoDataBase {

	private final static String CURSOS_SIN_ABRIR = "select * from Actividad_formativa where is_open = false";
	private final static String CURSOS_ABRIR = "update Actividad_formativa set is_open = true, numero_plazas = ?, inicio_inscripcion = ?, fin_inscripcion = ? where nombre = ?";
	
	public static List<CursoDTO> getCursosSinAbrir() {
		List<CursoDTO> cursos = null;
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			conn = DatabaseConnection.getConnection();			
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement(CURSOS_SIN_ABRIR);
			rs = st.executeQuery();			
			
			cursos = toCursoDTOList(rs);
			
			conn.commit();
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new RuntimeException(e);	
			
		} finally {
			try {
				rs.close();
				st.close();
				conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);				
			}			
		}
		
		return cursos;
	}
	
	public static void abrirCurso(CursoDTO curosDTO) {
		Connection conn = null;
		PreparedStatement st = null;
		
		try
		{
			conn = DatabaseConnection.getConnection();			
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement(CURSOS_ABRIR);
			
			st.setInt(1, curosDTO.plazasDisponibles);
			st.setDate(2, curosDTO.fechaInicioInscipcion);
			st.setDate(3, curosDTO.fechaFinInscipcion);
			st.setString(4, curosDTO.title);
			
			st.executeUpdate();	
			
			conn.commit();
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new RuntimeException(e);	
			
		} finally {
			try {
				st.close();
				conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);				
			}			
		}
		
	}
	
	private static List<CursoDTO> toCursoDTOList(ResultSet rs) throws SQLException {
		List<CursoDTO> res = new ArrayList<>();
		while(rs.next()) {
			res.add( toMechanicDto( rs ) );
		}
		return res;
	}
	
	private static CursoDTO toMechanicDto(ResultSet m) throws SQLException {
		CursoDTO dto = new CursoDTO();
		dto.title = m.getString("nombre");
		dto.price = m.getDouble("precio");

		dto.fechaInicioInscipcion = m.getDate("inicio_inscripcion");
		dto.fechaFinInscipcion = m.getDate("fin_inscripcion");
		dto.abierto = m.getBoolean("is_open");
		return dto;
	}

	
}
