package abel.modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import abel.controlador.ConfigurarActividadControler;
import abel.controlador.SolicitarTitulacionControler;
import abel.controlador.VisualizarInscritosCursoControler;
import main.DatabaseConnection;

public class DataBaseManagement 
{
	private final static String QUERY_INSERT_ACTIVIDAD_PERICIAL = "INSERT INTO Actividad_Pericial(numero, tipo_pericial, prioridad, nombre_solicitante, mail_solicitante, telefono_solicitante, descripcion, estado) "
																	+ "VALUES(%s,\"%s\",\"%s\",\"%s\",\"%s\",%s,\"%s\",\"%s\");";
	private final static String QUERY_INSERT_ACTIVIDAD_FORMATIVA = "INSERT INTO Actividad_Formativa(nombre_curso,precio,fecha_orientativa) VALUES(\"%s\",%s,%s);";
	private final static String QUERY_FIND_ACTIVIDAD_FORMATIVA_BY_ID = "SELECT nombre_curso FROM Actividad_Formativa WHERE nombre_curso = \"%s\";";
	private final static String QUERY_INSERT_FECHA_IMPARTICION  = "INSERT INTO Fecha_Imparticion(nombre_curso,fecha) VALUES(\"%s\",'%s');";
	private final static String QUERY_OBTENER_ACTIVIDADES_FORMATIVAS = "SELECT a.nombre_curso, a.precio, a.fecha_orientativa, a.is_open  FROM Actividad_Formativa a "
																	+ " WHERE a.fecha_orientativa >= \"%s\" ORDER BY a.nombre_curso;";
	private final static String QUERY_OBTENER_INSCRITOS_ACTIVIDAD_FORMATIVA = "SELECT c.nombre, c.apellidos, a.fecha_inscripcion, a.estado, a.cantidad_abonada "
																			+ "FROM apuntado a INNER JOIN Colegiado c ON a.id_colegiado = c.id_colegiado"
																			+ " WHERE a.nombre_curso = \"%s\" ORDER BY c.apellidos ASC, c.nombre ASC;";
	
	private final static String QUERY_INGRESOS_FOR_ACTIVIDAD_FORMATIVA = "SELECT SUM(cantidad_abonada) from apuntado where nombre_curso = \"%s\";";
	
	private final static String QUERY_NEXT_FORMULARIO_NUM = "SELECT MAX(numero) from Actividad_Pericial;";
	
	private final static String QUERY_SELECT_PENDING_REQUEST = "SELECT dni, nombre, apellidos, telefono FROM Colegiado WHERE tipoSolicitud = \"PENDIENTE\";";
	
	private final static String QUERY_SELECT_PENDING_REQUEST_BY_DNI = "SELECT dni, nombre, apellidos, telefono FROM Colegiado WHERE dni = ? and tipoSolicitud = \"PENDIENTE\";";
	
	private final static String QUERY_SET_VALIDANDO = "UPDATE Colegiado set tipoSolicitud = \"VALIDANDO\" WHERE dni = ?";
	
	private final static String QUERY_IMPARTE = "insert into Imparte(profesor,nombre_curso) VALUES (?,?)";
	
	private final static String QUERY_FIND_PROFESORS = "select * from Profesor";
	
	private final static String QUERY_FIND_FECHAS_CURSO = "select fecha from Fecha_Imparticion where nombre_curso = ?";
	
	private final static String QUERY_FIND_CONFIGURED_IMPARTICION = "select * from Fecha_Imparticion where duracion > 0";
	
	private final static String QUERY_UPDATE_IMPARTICION = "update Fecha_Imparticion set hora = ?, duracion = ? where nombre_curso = ? and fecha = ?";
	
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
	
	public static ColegiadoInscritoDTO getColegiadoPendienteByDni(String dni) {
		List<ColegiadoInscritoDTO> pendientes = new ArrayList<ColegiadoInscritoDTO>();
		try (Connection conn = DatabaseConnection.getConnection();)
		{	
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement(QUERY_SELECT_PENDING_REQUEST_BY_DNI);
			st.setString(1, dni);
			ResultSet rs = st.executeQuery();
			pendientes = SolicitarTitulacionControler.toApuntadoList(rs);
			
			conn.commit();
		} catch (SQLException e) {
		}
		return pendientes.size() == 1 ? pendientes.get(0) : null;
	}

	public static void setValidando(List<String> dnis) {
		try (Connection conn = DatabaseConnection.getConnection();)
		{	
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement(QUERY_SET_VALIDANDO);
			for(String dni : dnis)
			{
				st.setString(1, dni);
				st.executeUpdate();
				System.out.println(1);
			}
			conn.commit();
		} catch (SQLException e) {
		}
	}
	
	public static List<ProfesorDTO> findAllProfesor()
	{
		List<ProfesorDTO> profesores = new ArrayList<ProfesorDTO>();
		try (Connection conn = DatabaseConnection.getConnection();)
		{	
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement(QUERY_FIND_PROFESORS);
			ResultSet rs = st.executeQuery();
			profesores = ConfigurarActividadControler.toProfesorList(rs);
			
			conn.commit();
		} catch (SQLException e) {
		}
		return profesores;
	}
	
	public static List<Date> findImparticionesFor(String curso)
	{
		List<Date> dates = new ArrayList<Date>();
		try (Connection conn = DatabaseConnection.getConnection();)
		{	
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement(QUERY_FIND_FECHAS_CURSO);
			st.setString(1, curso);
			ResultSet rs = st.executeQuery();
			while(rs.next())
			{
				dates.add(rs.getDate(1));
			}
			
			conn.commit();
		} catch (SQLException e) {
		}
		return dates;
	}
	
	public static boolean configurarImparticion(String curso, Date fecha, String hora, int duracion)
	{
		if(seSolapa(fecha,hora,duracion)) return false;
		try (Connection conn = DatabaseConnection.getConnection();)
		{	
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement(QUERY_UPDATE_IMPARTICION);
			st.setString(1, hora);
			st.setInt(2, duracion);
			st.setString(3, curso);
			st.setDate(4, fecha);
			st.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
		}
		return true;
	}
	
	public static void addProfesorCurso(String curso, String profesor)
	{
		try (Connection conn = DatabaseConnection.getConnection();)
		{	
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement(QUERY_IMPARTE);
			st.setString(1, profesor);
			st.setString(2, curso);
			st.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
		}
	}
	
	private static boolean seSolapa(Date fecha, String hora, int duracion)
	{
		List<ImparticionDTO> imparticiones = new ArrayList<ImparticionDTO>();
		try (Connection conn = DatabaseConnection.getConnection();)
		{	
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement(QUERY_FIND_CONFIGURED_IMPARTICION);
			ResultSet rs = st.executeQuery();
			conn.commit();
			imparticiones = ConfigurarActividadControler.toImparticionesList(rs);
		} catch (SQLException e) {
		}
		for(ImparticionDTO imparticion : imparticiones)
		{
			int[] horaInt = {Integer.valueOf(imparticion.hora.split(":")[0]) , Integer.valueOf(imparticion.hora.split(":")[1])};
			int minFin = horaInt[1] + imparticion.duracion;
			int horasMas = minFin / 60;
			int[] horaFin = {horaInt[0] + horasMas, minFin % 60};
			if(imparticion.fecha.compareTo(fecha) == 0)
			{
				int[] horaInt2 = {Integer.valueOf(hora.split(":")[0]) , Integer.valueOf(hora.split(":")[1])};
				int minFin2 = horaInt2[1] + duracion;
				int horasMas2 = minFin2 / 60;
				int[] horaFin2 = {horaInt2[0] + horasMas2, minFin2 % 60};
				if(horaInt2[0] >= horaInt[0] && horaInt2[0] <= horaFin[0] || horaFin2[0] <= horaFin[0] && horaFin2[0] >= horaInt[0])
					if(horaFin2[0] == horaInt[0] && horaFin2[1] > horaInt[1] || horaInt2[0] == horaFin[0] && horaInt2[1] < horaFin[1])
						return true;
			}
		}
		return false;
	}
}
