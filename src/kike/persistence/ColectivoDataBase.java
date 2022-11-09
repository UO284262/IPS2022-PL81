package kike.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.DatabaseConnection;

public class ColectivoDataBase {

	private static final String GET_COLECTIVOS = "select * from colectivo";

	public static List<String> getColectivos() {
		List<String> cols = new ArrayList<String>();
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			conn = DatabaseConnection.getConnection();			
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement(GET_COLECTIVOS);
			
			rs = st.executeQuery();			
			
			while(rs.next()) {
				cols.add(rs.getString("nombre_colectivo"));
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
		
		return cols;
	}

}
