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
	private final static String QUERY_INSERT_ACTIVIDAD_PERICIAL = "INSERT INTO Actividad_Pericial(numero, tipo_pericial, prioridad, nombre_solicitante, mail_solicitante, telefono_solicitante, descripcion, estado) "
																	+ "VALUES(%s,\"%s\",\"%s\",\"%s\",\"%s\",%s,\"%s\",\"%s\");";
	private final static String QUERY_INSERT_FECHA_IMPARTICION  = "INSERT INTO Fecha_Imparticion VALUES(\"%s\",'%s');";
	private final static String QUERY_OBTENER_ACTIVIDADES_FORMATIVAS = "SELECT DISTINCT a.nombre FROM Actividad_Formativa a INNER JOIN Fecha_Imparticion f "
																	+ "ON a.nombre = f.nombre WHERE f.fecha >= \"%s\";";
	private final static String QUERY_OBTENER_INSCRITOS_ACTIVIDAD_FORMATIVA = "SELECT c.nombre, c.apellidos, a.fecha_inscripcion, a.estado, a.cantidad_abonada "
																			+ "FROM apuntado a INNER JOIN Colegiado c ON a.id_colegiado = c.id_colegiado"
																			+ " WHERE a.nombre = \"%s\" ORDER BY c.apellidos ASC, c.nombre ASC;";
	
	private final static String QUERY_INGRESOS_FOR_ACTIVIDAD_FORMATIVA = "SELECT SUM(cantidad_abonada) from apuntado where nombre = \"%s\";";
	
	private final static String QUERY_NEXT_FORMULARIO_NUM = "SELECT MAX(numero) from Actividad_Pericial;";
	
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
	
	public static List<String> getInscritosEn(String actividadFormativa)
	{
		try (Connection conn = DatabaseConnection.getConnection();)
		{	
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement(String.format(QUERY_OBTENER_INSCRITOS_ACTIVIDAD_FORMATIVA,actividadFormativa));
			ResultSet rs = st.executeQuery();
			conn.commit();
			List<String> nombres = toApuntadoList(rs);
			st.close();
			rs.close();
			return nombres;
			
		} catch (SQLException e) { e.printStackTrace();}
		return null;
	}
	
	private static List<String> toApuntadoList(ResultSet rs) throws SQLException
	{
		List<String> apuntados = new ArrayList<String>();
		while(rs.next())
		{
			String apellidos = rs.getString(2);
			String nombre = rs.getString(1);
			String fecha = rs.getString(3);
			String estado = rs.getString(4);
			String pagado = rs.getString(5);
			apuntados.add(String.format("%s, %s (%s) -- %s -- %s €",apellidos,nombre,fecha,estado,pagado));
		}
		return apuntados;
	}
	
	public static double getIngresosFor(String actividadFormativa)
	{
		double d = 0;
		try (Connection conn = DatabaseConnection.getConnection();)
		{	
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement(String.format(QUERY_INGRESOS_FOR_ACTIVIDAD_FORMATIVA,actividadFormativa));
			ResultSet rs = st.executeQuery();
			if(rs.next())
			{
				d = rs.getDouble(1);
			}
			conn.commit();
			st.close();
			rs.close();
			
		} catch (SQLException e) { e.printStackTrace();}
		return d;
	}

	public static int getNextNumeroFormulario() {
		int d = 0;
		try (Connection conn = DatabaseConnection.getConnection();)
		{	
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement(String.format(QUERY_NEXT_FORMULARIO_NUM));
			ResultSet rs = st.executeQuery();
			if(rs.next())
			{
				d = rs.getInt(1) + 1;
			}
			conn.commit();
			st.close();
			rs.close();
			
		} catch (SQLException e) { e.printStackTrace();}
		return d;
	}
	
	public static boolean addFormulario(FormularioPericialDTO actividad)
	{
		try (Connection conn = DatabaseConnection.getConnection();)
		{	
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement(String.format(QUERY_INSERT_ACTIVIDAD_PERICIAL,actividad.numero,actividad.tipo_pericial,actividad.prioridad,
					actividad.nombre_solicitante,actividad.mail_solicitante,actividad.telefono_solicitante,actividad.descripcion,actividad.estado));
			st.executeUpdate();
			
			conn.commit();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
}
