package mvc.controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.DatabaseConnection;
import mvc.modelo.perito.PeritoDTO;

public class PeritoDataBase {

	
	private static final String PERITO_BY_COLEGIADO = "Select * from perito where id_colegiado = ?";
	private static final String LAST_IN_QUEUE = "Select max(posicion_lista) from perito";
	private static final String ADD_PERITO = "INSERT INTO perito (id_perito, id_colegiado, posicion_lista, fecha_tope_renovacion, activo) "
			+ "VALUES (?,?,?,?, ?)";
	private static final String RENUEVA_PERITO = "UPDATE perito SET fecha_tope_renovacion = ?, posicion_lista = ?, activo = ? WHERE id_perito = ?";

	public static PeritoDTO findByColegiadoId(String id) {
		PeritoDTO perito = null;
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			conn = DatabaseConnection.getConnection();			
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement(PERITO_BY_COLEGIADO);
			st.setString(1, id);
			
			rs = st.executeQuery();			
			
			perito = toPeritoDTO(rs);

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
		
		return perito;
	}

	private static PeritoDTO toPeritoDTO(ResultSet rs) throws SQLException {
		if(!rs.next())
			return null;
		
		PeritoDTO perito = new PeritoDTO();
		
		perito.id_perito = rs.getString("id_perito");
		perito.id_colegiado = rs.getString("id_colegiado");
		perito.pos_Lista = rs.getInt("posicion_lista");
		perito.fecha_Tope = rs.getDate("fecha_tope_renovacion");
		perito.activo = rs.getBoolean("activo");
		
		return perito;
	}

	public static int getLastInQueue() {
		int i = 1;
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			conn = DatabaseConnection.getConnection();			
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement(LAST_IN_QUEUE);
			
			rs = st.executeQuery();			
			
			if(rs.next())
				i = rs.getInt(1);

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
		
		return i;
	}

	public static void createPerito(PeritoDTO perito) {
		Connection conn = null;
		PreparedStatement st = null;
		
		try
		{
			conn = DatabaseConnection.getConnection();			
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement(ADD_PERITO);
			st.setString(1, perito.id_perito);
			st.setString(2, perito.id_colegiado);
			st.setInt(3, perito.pos_Lista);
			st.setDate(4, perito.fecha_Tope);
			st.setBoolean(5, perito.activo);
			
			st.execute();
			
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

	public static void renuevaPerito(PeritoDTO perito) {
		Connection conn = null;
		PreparedStatement st = null;
		
		try
		{
			conn = DatabaseConnection.getConnection();			
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement(RENUEVA_PERITO);
			st.setDate(1, perito.fecha_Tope);
			st.setInt(2, perito.pos_Lista);
			st.setBoolean(3, perito.activo);
			st.setString(4, perito.id_perito);
			
			
			
			st.execute();

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
}
