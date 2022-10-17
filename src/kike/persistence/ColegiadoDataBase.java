package kike.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kike.modelo.colegiado.ColegiadoDTO;
import main.DatabaseConnection;

public class ColegiadoDataBase {

	private static final String EXISTS_COLEGIADO = "select id_colegiado from colegiado where id_colegiado = ?";
	private static final String FIND_COLEGIADO_ID = "select * from colegiado where id_colegiado = ?";

	public static boolean isValidId(String id_colegiado) {
		
		boolean existe = false;
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			conn = DatabaseConnection.getConnection();			
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement(EXISTS_COLEGIADO);
			st.setString(1, id_colegiado);
			
			rs = st.executeQuery();			
						
			existe = rs.next();
			
			conn.commit();
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
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
		
		return existe;
	}

	public static ColegiadoDTO findById(String id_colegiado) {
		
		ColegiadoDTO cdto = null;
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			conn = DatabaseConnection.getConnection();			
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement(FIND_COLEGIADO_ID);
			st.setString(1, id_colegiado);
			
			rs = st.executeQuery();			
						
			cdto = toColegiadoDTO(rs);
			
			conn.commit();
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
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
		
		return cdto;
	}

	private static ColegiadoDTO toColegiadoDTO(ResultSet rs) throws SQLException {
		rs.next();
		
		ColegiadoDTO cdto = new ColegiadoDTO();
		
		cdto.id_colegiado = rs.getString("id_colegiado");
		cdto.nombre = rs.getString("nombre");
		cdto.apellidos = rs.getString("apellidos");
		cdto.dni = rs.getString("dni");
		
		return cdto;
	}

}
