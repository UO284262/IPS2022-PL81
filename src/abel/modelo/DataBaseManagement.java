package abel.modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import abel.controlador.VisualizarInscritosCursoControler;

import java.sql.Date;

import main.DatabaseConnection;

public class DataBaseManagement 
{
	
	private final static String QUERY_INSERT_ACTIVIDAD_FORMATIVA = "INSERT INTO Actividad_Formativa(nombre_curso,precio,fecha_orientativa) VALUES(\"%s\",%s,%s);";
	private final static String QUERY_FIND_ACTIVIDAD_FORMATIVA_BY_ID = "SELECT nombre_curso FROM Actividad_Formativa WHERE nombre_curso = \"%s\";";
	private final static String QUERY_INSERT_FECHA_IMPARTICION  = "INSERT INTO Fecha_Imparticion VALUES(\"%s\",'%s');";
	private final static String QUERY_OBTENER_ACTIVIDADES_FORMATIVAS = "SELECT a.nombre_curso, a.precio, a.fecha_orientativa, a.is_open  FROM Actividad_Formativa a "
																	+ " WHERE a.fecha_orientativa >= \"%s\" ORDER BY a.nombre_curso;";
	private final static String QUERY_OBTENER_INSCRITOS_ACTIVIDAD_FORMATIVA = "SELECT c.nombre, c.apellidos, a.fecha_inscripcion, a.estado, a.cantidad_abonada "
																			+ "FROM apuntado a INNER JOIN Colegiado c ON a.id_colegiado = c.id_colegiado"
																			+ " WHERE a.nombre_curso = \"%s\" ORDER BY c.apellidos ASC, c.nombre ASC;";
	
	private final static String QUERY_INGRESOS_FOR_ACTIVIDAD_FORMATIVA = "SELECT SUM(cantidad_abonada) from apuntado where nombre_curso = \"%s\";";
	
	public static boolean addActividadToDataBase(ActividadFormativaDTO actividad)
	{
		try (Connection conn = DatabaseConnection.getConnection();)
		{	
			conn.setAutoCommit(false);
			
			PreparedStatement st = conn.prepareStatement(String.format(QUERY_INSERT_ACTIVIDAD_FORMATIVA,actividad.title,actividad.price,actividad.days.get(0).toLocalDate().toString()));
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
	
	public static List<ActividadFormativaDTO> getActividadesFormativasFrom(Integer año)
	{
		try (Connection conn = DatabaseConnection.getConnection();)
		{	
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement(String.format(QUERY_OBTENER_ACTIVIDADES_FORMATIVAS,año.toString() + "-1-1"));
			ResultSet rs = st.executeQuery();
			conn.commit();
			List<ActividadFormativaDTO> lista = VisualizarInscritosCursoControler.toDTOList(rs);
			st.close();
			rs.close();
			return lista;
			
		} catch (SQLException e) { }
		return null;
	}
	
	public static List<String> getInscritosEn(String actividadFormativa)
	{
		List<String> nombres = new ArrayList<String>();
		try (Connection conn = DatabaseConnection.getConnection();)
		{	
			conn.setAutoCommit(false);
			PreparedStatement st2 = conn.prepareStatement(String.format(QUERY_FIND_ACTIVIDAD_FORMATIVA_BY_ID,actividadFormativa));
			ResultSet rs2 = st2.executeQuery();
			if(!rs2.next())
			{
				return null;
			}
			PreparedStatement st = conn.prepareStatement(String.format(QUERY_OBTENER_INSCRITOS_ACTIVIDAD_FORMATIVA,actividadFormativa));
			ResultSet rs = st.executeQuery();
			conn.commit();
			nombres = toApuntadoList(rs);
			st.close();
			rs.close();
			return nombres;
			
		} catch (SQLException e) { e.printStackTrace();}
		return nombres;
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
}
