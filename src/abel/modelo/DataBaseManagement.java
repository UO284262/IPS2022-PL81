package abel.modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import abel.controlador.SolicitarTitulacionControler;
import abel.controlador.VisualizarInscritosCursoControler;

import java.sql.Date;

import main.DatabaseConnection;

public class DataBaseManagement 
{
	private final static String QUERY_INSERT_ACTIVIDAD_PERICIAL = "INSERT INTO Actividad_Pericial(numero, tipo_pericial, prioridad, nombre_solicitante, mail_solicitante, telefono_solicitante, descripcion, estado) "
																	+ "VALUES(%s,\"%s\",\"%s\",\"%s\",\"%s\",%s,\"%s\",\"%s\");";
	private final static String QUERY_INSERT_ACTIVIDAD_FORMATIVA = "INSERT INTO Actividad_Formativa(nombre_curso,precio,fecha_orientativa) VALUES(\"%s\",%s,%s);";
	private final static String QUERY_FIND_ACTIVIDAD_FORMATIVA_BY_ID = "SELECT nombre_curso FROM Actividad_Formativa WHERE nombre_curso = \"%s\";";
	private final static String QUERY_INSERT_FECHA_IMPARTICION  = "INSERT INTO Fecha_Imparticion VALUES(\"%s\",'%s');";
	private final static String QUERY_OBTENER_ACTIVIDADES_FORMATIVAS = "SELECT a.nombre_curso, a.precio, a.fecha_orientativa, a.is_open  FROM Actividad_Formativa a "
																	+ " WHERE a.fecha_orientativa >= \"%s\" ORDER BY a.nombre_curso;";
	private final static String QUERY_OBTENER_INSCRITOS_ACTIVIDAD_FORMATIVA = "SELECT c.nombre, c.apellidos, a.fecha_inscripcion, a.estado, a.cantidad_abonada "
																			+ "FROM apuntado a INNER JOIN Colegiado c ON a.id_colegiado = c.id_colegiado"
																			+ " WHERE a.nombre_curso = \"%s\" ORDER BY c.apellidos ASC, c.nombre ASC;";
	
	private final static String QUERY_INGRESOS_FOR_ACTIVIDAD_FORMATIVA = "SELECT SUM(cantidad_abonada) from apuntado where nombre_curso = \"%s\";";
	
	private final static String QUERY_NEXT_FORMULARIO_NUM = "SELECT MAX(numero) from Actividad_Pericial;";
	
	private final static String QUERY_SELECT_PENDING_REQUEST = "SELECT * FROM Colegiado WHERE tipoSolicitud = 0;";
	
	private final static String QUERY_SELECT_PENDING_REQUEST_BY_DNI = "SELECT dni, nombre FROM Colegiado WHERE dni = ?";
	
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
	
	public static List<ColegiadoInscritoDTO> getInscritosEn(String actividadFormativa)
	{
		List<ColegiadoInscritoDTO> nombres = new ArrayList<ColegiadoInscritoDTO>();
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
			nombres = VisualizarInscritosCursoControler.toApuntadoList(rs);
			st.close();
			rs.close();
			return nombres;
			
		} catch (SQLException e) { e.printStackTrace();}
		return nombres;
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
	
	public static List<String[]> getColegiadosPendientes(List<String> colegiados)
	{
		List<String[]> pendientes = new ArrayList<String[]>();
		try (Connection conn = DatabaseConnection.getConnection();)
		{	
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement(QUERY_SELECT_PENDING_REQUEST_BY_DNI);
			for(String dni : colegiados)
			{
				st.setString(1, dni);
				ResultSet rs = st.executeQuery();
				String[] elemento = SolicitarTitulacionControler.toStringElement(rs);
				if(elemento != null) pendientes.add(elemento);
			}
			
			conn.commit();
		} catch (SQLException e) {
		}
		return pendientes;
	}

	public static List<ColegiadoInscritoDTO> getAllColegiadosPendientes() {
		List<ColegiadoInscritoDTO> pendientes = new ArrayList<ColegiadoInscritoDTO>();
		try (Connection conn = DatabaseConnection.getConnection();)
		{	
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement(QUERY_SELECT_PENDING_REQUEST);
			ResultSet rs = st.executeQuery();
			pendientes = SolicitarTitulacionControler.toApuntadoList(rs);
			
			conn.commit();
		} catch (SQLException e) {
		}
		return pendientes;
	}
}
