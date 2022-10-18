package kike.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kike.persistence.dto.InscripcionDTO;
import kike.persistence.dto.InscripcionDTO.TipoInscripcion;
import main.DatabaseConnection;

public class InscripcionDataBase {

	private static final String CREAR_INSCRIPCION = "INSERT INTO apuntado(id_colegiado, nombre_curso, fecha_inscripcion, pagado, tipo_inscripcion) VALUES(?, ?, ?, ?, ?)";
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
			st.setInt(5, parseToDatabase(idto.estado));
			
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

	private static int parseToDatabase(TipoInscripcion estado) {
		switch (estado) {
		case PRE_INSCRITO: {
			return 1;
		}
		case INSCRITO: {
			return 2;
		}
		case CANCELADO: {
			return 0;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + estado);
		}
	}
	
	private static TipoInscripcion parseFromDatabase(int estado) {
		switch (estado) {
		case 1: {
			return TipoInscripcion.PRE_INSCRITO;
		}
		case 2: {
			return TipoInscripcion.INSCRITO;
		}
		case 0: {
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
