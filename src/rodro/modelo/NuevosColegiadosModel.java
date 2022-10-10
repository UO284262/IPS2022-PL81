package rodro.modelo;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import main.DatabaseConnection;

public class NuevosColegiadosModel {
	
	private final static String QUERY_INSERT_NUEVO_COLEGIADO = "INSERT INTO TRABAJADOR (DNI, NOMBRE, APELLIDOS, POBLACION, TITULACION, AÑO, IBAN, CENTRO, TELEFONO) VALUES "
			+ "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	public static boolean addColegiadoToDataBase(ColegiadoDto colegiado)
	{
		try (Connection conn = DatabaseConnection.getConnection();)
		{	
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement(QUERY_INSERT_NUEVO_COLEGIADO);
			st.setString(1, colegiado.dni);
			st.setString(2, colegiado.nombre);
			st.setString(3, colegiado.apellidos);
			st.setString(4, colegiado.poblacion);
			st.setString(5, colegiado.titulacion);
			st.setString(6, colegiado.cuentaBancaria);
			st.setInt(7, colegiado.año);
			st.setString(8, colegiado.centro);
			st.setInt(9, colegiado.tlfn);
			st.executeUpdate();
				
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

}
