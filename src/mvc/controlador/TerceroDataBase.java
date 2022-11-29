package mvc.controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import main.DatabaseConnection;
import mvc.modelo.tercero.TerceroDTO;

public class TerceroDataBase {

	private static final String FIND_TERCERO_DNI = "select * from terceros where dni = ?";

	public static Optional<TerceroDTO> getTerceroByDNI(String dni) {
		Optional<TerceroDTO> cdto;
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			conn = DatabaseConnection.getConnection();			
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement(FIND_TERCERO_DNI);
			st.setString(1, dni);
			
			rs = st.executeQuery();			
						
			cdto = Optional.ofNullable(toTerceroDTO(rs));
			
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

	private static TerceroDTO toTerceroDTO(ResultSet rs) throws SQLException {
		if(!rs.next()) {
			return null;
		}
		
		TerceroDTO ter = new TerceroDTO();
		
		ter.colectivo = rs.getString("colectivo");
		ter.dni = rs.getString("dni");
		ter.nombre = rs.getString("nombre");
		
		return ter;
	}

}
