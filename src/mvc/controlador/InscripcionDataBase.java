package mvc.controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import main.DatabaseConnection;
import mvc.controlador.InscripcionDTO.TipoInscripcion;

public class InscripcionDataBase {

	private static final String CREAR_INSCRIPCION = "INSERT INTO apuntado(dni, nombre_curso, fecha_inscripcion, pagado, estado, cantidad_abonada, posicion_cola) VALUES(?, ?, ?, ?, ?, ?, ?)";
	private static final String EXISTS_INSCRIPCION = "Select * from apuntado where dni = ? and nombre_curso = ?";
	private static final String GET_APUNTADOS = "Select count(*) from apuntado where nombre_curso = ?";

	public static void createPreInscripcion(InscripcionDTO idto) {
		Connection conn = null;
		PreparedStatement st = null;
		
		try
		{
			conn = DatabaseConnection.getConnection();			
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement(CREAR_INSCRIPCION);
			
			st.setString(1, idto.dni);
			st.setString(2, idto.nombre_curso);
			st.setDate(3, idto.fecha_Inscripcion);
			st.setBoolean(4, idto.pagado);
			st.setString(5, parseToDatabase(idto.estado));
			st.setDouble(6, idto.cantidad_abonada);
			
			if(idto.pos_cola != null) {
				st.setInt(7, idto.pos_cola);
			} else {
				st.setNull(7, Types.INTEGER);
			}
			
			st.executeUpdate();
			
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
	
//	private static TipoInscripcion parseFromDatabase(String estado) {
//		switch (estado) {
//		case "PRE-INSCRITO": {
//			return TipoInscripcion.PRE_INSCRITO;
//		}
//		case "INSCRITO": {
//			return TipoInscripcion.INSCRITO;
//		}
//		case "CANCELADO": {
//			return TipoInscripcion.CANCELADO;
//		}
//		default:
//			throw new IllegalArgumentException("Unexpected value: " + estado);
//		}
//	}

	public static boolean isInscrito(String dni, String title) {

		boolean existe = false;
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			conn = DatabaseConnection.getConnection();			
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement(EXISTS_INSCRIPCION);
			st.setString(1, dni);
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

	public static int getApuntados(String title) {
		int apuntados = 0;
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			conn = DatabaseConnection.getConnection();			
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement(GET_APUNTADOS);
			st.setString(1, title);
			
			rs = st.executeQuery();			
					
			if(rs.next()) {
				apuntados += rs.getInt(1);
			}
			
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
		
		return apuntados;
	}

}
