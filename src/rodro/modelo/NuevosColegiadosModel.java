package rodro.modelo;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import main.DatabaseConnection;

public class NuevosColegiadosModel {
	
	private final static String QUERY_INSERT_NUEVO_COLEGIADO = "INSERT INTO TRABAJADOR (DNI, NOMBRE, APELLIDOS, POBLACION, TITULACION, AÑO, IBAN, CENTRO, TELEFONO) VALUES "
			+ "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	
	/**
	 * Añadimos un nuevo colegiado a la base de datos
	 * @param colegiado, colegiado que deseamos añadir
	 */
	public void addColegiado(ColegiadoDto colegiado) {
		addColegiadoToDataBase(colegiado);
	}
	
	/**
	 * Devuelve un boolean dependiendo de si existe el colegiado con el id 
	 * pasado por parámetro
	 * @param id, identificador del colegiado
	 * @return true si existe, false en caso contrario
	 */
	public boolean isTrueColegiado(String id){
		if (isTrueColegiadoInDataBase(id)) {
			return true;
		}
		return false;
	}
	
	private void addColegiadoToDataBase(ColegiadoDto colegiado)
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
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * Conexión a la base de datos para conseguir un colegiado
	 * @param id, identificador del colegiado
	 * @return null si no existe o el colegiado
	 */
	private boolean isTrueColegiadoInDataBase(String id){
		ResultSet rs = null;
		try (Connection conn = DatabaseConnection.getConnection();)
		{
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement(QUERY_INSERT_NUEVO_COLEGIADO);
			st.setString(1, id);
		    rs = st.executeQuery();

			if (rs.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			close(rs);
		}
	}
		
		protected static void close(ResultSet rs) {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					/* ignore */}
		}
	
	
	

}
