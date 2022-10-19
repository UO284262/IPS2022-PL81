package rodro.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.DatabaseConnection;

public class EmitirRecibosModel {
	
	private final static String QUERY_OBTENER_RECIBOS = "SELECT * FROM RECIBOS "; 
	
	public static List<ReciboDto> getRecibos()
	{
		try (Connection conn = DatabaseConnection.getConnection();)
		{	
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement(String.format(QUERY_OBTENER_RECIBOS));
			ResultSet rs = st.executeQuery();
			conn.commit();
			List<ReciboDto> recibos = toReciboList(rs);
			st.close();
			rs.close();
			return recibos;
			
		} catch (SQLException e) { }
		
		return  null ;
	}
	
	private static List<ReciboDto> toReciboList(ResultSet rs) throws SQLException
	{
		List<ReciboDto> recibos = new ArrayList<ReciboDto>();
		while(rs.next())
		{
			String numRecibo = rs.getString(1);
			Date fecha = rs.getDate(2);
			String dni = rs.getString(3);
			String numCuenta = rs.getString(4);
			double cantidad = rs.getDouble(5);
			recibos.add(new ReciboDto(numRecibo,fecha,dni,numCuenta,cantidad));
		}
		return recibos;
	}

}
