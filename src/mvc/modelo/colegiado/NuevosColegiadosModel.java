package mvc.modelo.colegiado;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import main.DatabaseConnection;

public class NuevosColegiadosModel {
	
	private final static String QUERY_INSERT_NUEVO_COLEGIADO = "INSERT INTO COLEGIADO (id_colegiado,DNI, NOMBRE, APELLIDOS, CIUDAD, TITULACION, AÑO, IBAN, CENTRO, TELEFONO) VALUES "
			+ "(?,?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final static String QUERY_FIND_COLEGIADO_BY_DNI = "SELECT dni FROM COLEGIADO WHERE dni = ?";
	
	
	/**
	 * Añadimos un nuevo colegiado a la base de datos
	 * @param colegiado, colegiado que deseamos añadir
	 */
	public void addColegiado(ColegiadoDto2 colegiado) {
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
	
	private void addColegiadoToDataBase(ColegiadoDto2 colegiado)
	{
		try (Connection conn = DatabaseConnection.getConnection();)
		{	
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement(QUERY_INSERT_NUEVO_COLEGIADO);
			st.setString(1, colegiado.dni);
			st.setString(2, colegiado.dni);
			st.setString(3, colegiado.nombre);
			st.setString(4, colegiado.apellidos);
			st.setString(5, colegiado.poblacion);
			st.setString(6, colegiado.titulacion);
			st.setString(8, colegiado.cuentaBancaria);
			st.setInt(7, colegiado.año);
			st.setString(9, colegiado.centro);
			st.setInt(10, colegiado.tlfn);
			st.executeUpdate();
			
			conn.commit();
				
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * Conexión a la base de datos para conseguir un colegiado
	 * @param id, identificador del colegiado
	 * @return false si no existe o true si existe
	 */
	private boolean isTrueColegiadoInDataBase(String id){
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement st = null;
		boolean existe;
		try 
		{
			conn = DatabaseConnection.getConnection();
			conn.setAutoCommit(false);
			st = conn.prepareStatement(QUERY_FIND_COLEGIADO_BY_DNI);
			st.setString(1, id);
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
