package kike.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kike.persistence.dto.InscripcionDTO;
import kike.persistence.dto.InscripcionDTO.TipoInscripcion;
import main.DatabaseConnection;

public class InscripcionDataBase {

	private static final String CREAR_INSCRIPCION = "INSERT INTO apuntado(id_colegiado, nombre_curso, fecha_inscripcion, pagado, estado, cantidad_abonada) VALUES(?, ?, ?, ?, ?, ?)";
	private static final String EXISTS_INSCRIPCION = "Select * from apuntado where id_colegiado = ? and nombre_curso = ?";

	public static void createPreInscripcion(InscripcionDTO idto) {
		Connection conn = null;
		PreparedStatement st = null;
		
		try
		{
			conn = DatabaseConnection.getConnection();			
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement(CREAR_INSCRIPCION);
			
			st.setString(1, idto.id_socio);
			st.setString(2, idto.nombre_curso);
			st.setDate(3, idto.fecha_Inscripcion);
			st.setBoolean(4, idto.pagado);
			st.setString(5, parseToDatabase(idto.estado));
			st.setDouble(6, idto.cantidad_abonada);
			
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

	private static String parseToDatabase(TipoInscripcion estado) {
		switch (estado) {
		case PRE_INSCRITO: {
			return "PRE-INSCRITO";
		}
		case INSCRITO: {
			return "INSCRITO";
		}
		case CANCELADO: {
			return "CANCELADO";
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + estado);
		}
	}
	
	private static TipoInscripcion parseFromDatabase(String estado) {
		switch (estado) {
		case "PRE-INSCRITO": {
			return TipoInscripcion.PRE_INSCRITO;
		}
		case "INSCRITO": {
			return TipoInscripcion.INSCRITO;
		}
		case "CANCELADO": {
			return TipoInscripcion.CANCELADO;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + estado);
		}
	}

	public static boolean isInscrito(String id_colegiado, String title) {

		boolean existe = false;
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			conn = DatabaseConnection.getConnection();			
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement(EXISTS_INSCRIPCION);
			st.setString(1, id_colegiado);
			st.setString(2, title);
			
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

}
