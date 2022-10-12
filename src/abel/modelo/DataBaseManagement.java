package abel.modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import main.DatabaseConnection;

public class DataBaseManagement 
{
	
	private final static String QUERY_INSERT_ACTIVIDAD_FORMATIVA = "INSERT INTO Actividad_Formativa(nombre,precio) VALUES(\"%s\",%s);";
	private final static String QUERY_INSERT_FECHA_IMPARTICION  = "INSERT INTO Fecha_Imparticion VALUES(\"%s\",'%s');";
	private final static String QUERY_OBTENER_ACTIVIDADES_FORMATIVAS = "SELECT DISTINCT nombre FROM Actividad_Formativa NATURAL JOIN"
																	+ "Fecha_Imparticion WHERE fecha >= \"%s\";";
	private final static String QUERY_OBTENER_INSCRITOS_ACTIVIDAD_FORMATIVA = "SELECT c.nombre, c.apellidos, a.fecha_inscripcion FROM apuntado a NATURAL JOIN"
																			+ "Colegiado c WHERE a.nombre_curso = \"%s\";";
	
	public static boolean addActividadToDataBase(ActividadFormativaDTO actividad)
	{
		try (Connection conn = DatabaseConnection.getConnection();)
		{	
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement(String.format(QUERY_INSERT_ACTIVIDAD_FORMATIVA,actividad.title,actividad.price));
			st.executeUpdate();
				for(Date day : actividad.days)
				{
					CallableStatement st2 = conn.prepareCall(String.format(QUERY_INSERT_FECHA_IMPARTICION,actividad.title,day));
					st2.executeUpdate();
				}
				conn.commit();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	public static List<String> getActividadesFormativasFrom(Integer año)
	{
		try (Connection conn = DatabaseConnection.getConnection();)
		{	
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement(String.format(QUERY_OBTENER_ACTIVIDADES_FORMATIVAS,año.toString() + "-1-1"));
			ResultSet rs = st.executeQuery();
			conn.commit();
			List<String> nombres = toNombreList(rs);
			st.close();
			rs.close();
			return nombres;
			
		} catch (SQLException e) { }
		return null;
	}
	
	private static List<String> toNombreList(ResultSet rs) throws SQLException
	{
		List<String> nombres = new ArrayList<String>();
		while(rs.next())
		{
			nombres.add(rs.getString("nombre"));
		}
		return nombres;
	}
}
