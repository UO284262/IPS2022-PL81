package rodro.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

import main.DatabaseConnection;

public class EmitirRecibosModel {
	
	private final static String QUERY_OBTENER_RECIBOS = "SELECT * FROM RECIBO "; 
	
	public static List<ReciboDto> getRecibos()
	{
		List<ReciboDto> recibos = null;
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{	
			conn = DatabaseConnection.getConnection();
			conn.setAutoCommit(false);
		    st = conn.prepareStatement(QUERY_OBTENER_RECIBOS);
		    rs = st.executeQuery();
			
			recibos = toReciboList(rs);
			
			conn.commit();
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		    throw new RuntimeException(e);	 
		}finally {
			try {
				rs.close();
				st.close();
				conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);				
			}			
		}
		
		return  recibos ;
	}
	
	private static List<ReciboDto> toReciboList(ResultSet rs) throws SQLException
	{
		List<ReciboDto> recibos = new ArrayList<ReciboDto>();
		while(rs.next())
		{
			ReciboDto dto = new ReciboDto();
			dto.idRecibo= rs.getString("id_recibo");
			dto.emision = rs.getDate("emision");
			dto.dniColegiado = rs.getString("dni");
			dto.iban = rs.getString("iban");
			dto.cantidad = rs.getDouble("cantidad");
			recibos.add(dto);
		}
		return recibos;
	}

}
