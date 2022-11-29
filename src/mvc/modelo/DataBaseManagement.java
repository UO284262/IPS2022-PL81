package mvc.modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import main.DatabaseConnection;
import mvc.controlador.CanceladorCursoControler;
import mvc.controlador.ConfigurarActividadControler;
import mvc.controlador.SolicitarTitulacionControler;
import mvc.controlador.VisualizarInscritosCursoControler;
import mvc.modelo.colegiado.ColegiadoInscritoDTO;
import mvc.modelo.curso.ActividadFormativaDTO;
import mvc.modelo.curso.ImparticionDTO;
import mvc.modelo.tercero.ApuntadoDTO;
import mvc.modelo.tercero.FormularioPericialDTO;
import mvc.modelo.tercero.ProfesorDTO;

public class DataBaseManagement 
{
	private final static String QUERY_INSERT_ACTIVIDAD_PERICIAL = "INSERT INTO Actividad_Pericial(numero, tipo_pericial, prioridad, nombre_solicitante, mail_solicitante, telefono_solicitante, descripcion, estado) "
																	+ "VALUES(%s,\"%s\",\"%s\",\"%s\",\"%s\",%s,\"%s\",\"%s\");";
	
	private final static String QUERY_INSERT_ACTIVIDAD_FORMATIVA = "INSERT INTO Actividad_Formativa(nombre_curso,precio,fecha_orientativa,estado) VALUES(?,?,?,\"VIGENTE\");";
	
	private final static String QUERY_FIND_ACTIVIDAD_FORMATIVA_BY_ID = "SELECT nombre_curso FROM Actividad_Formativa WHERE nombre_curso = \"%s\" and estado != \"CANCELADA\";";
	
	private final static String QUERY_INSERT_FECHA_IMPARTICION  = "INSERT INTO Fecha_Imparticion(nombre_curso,fecha) VALUES(\"%s\",'%s');";
	
	private final static String QUERY_OBTENER_ACTIVIDADES_FORMATIVAS = "SELECT a.nombre_curso, a.precio, a.fecha_orientativa, a.is_open  FROM Actividad_Formativa a "
																	+ " WHERE a.fecha_orientativa >= \"%s\" and a.estado != \"CANCELADA\" ORDER BY a.nombre_curso;";
	
	private final static String QUERY_OBTENER_INSCRITOS_COLEGIADO = "SELECT c.nombre, c.apellidos, a.fecha_inscripcion, a.estado, a.cantidad_abonada, c.id_colegiado "
																			+ "FROM apuntado a INNER JOIN Colegiado c ON a.dni = c.id_colegiado"
																			+ " WHERE a.nombre_curso = \"%s\" and a.estado != \"CANCELADA\" ORDER BY c.apellidos ASC, c.nombre ASC;";
	
	private static final String GET_COLECTIVOS = "select * from colectivo";
	
	private final static String QUERY_OBTENER_INSCRITOS_TERCEROS = "SELECT t.nombre, t.dni, a.fecha_inscripcion, a.estado, a.cantidad_abonada, t.colectivo "
			+ "FROM apuntado a INNER JOIN Terceros t ON a.dni = t.dni"
			+ " WHERE a.nombre_curso = \"%s\" and a.estado != \"CANCELADA\" ORDER BY t.colectivo ASC, t.nombre ASC;";
	
	private final static String QUERY_INGRESOS_FOR_ACTIVIDAD_FORMATIVA = "SELECT SUM(cantidad_abonada) from apuntado where nombre_curso = \"%s\";";
	
	private final static String QUERY_NEXT_FORMULARIO_NUM = "SELECT MAX(numero) from Actividad_Pericial;";
	
	private final static String QUERY_SELECT_PENDING_REQUEST = "SELECT dni, nombre, apellidos, telefono FROM Colegiado WHERE tipoSolicitud = \"PENDIENTE\";";
	
	private final static String QUERY_SELECT_PENDING_REQUEST_BY_DNI = "SELECT dni, nombre, apellidos, telefono FROM Colegiado WHERE dni = ? and tipoSolicitud = \"PENDIENTE\";";
	
	private final static String QUERY_SET_VALIDANDO = "UPDATE Colegiado set tipoSolicitud = \"VALIDANDO\" WHERE dni = ?";
	
	private final static String QUERY_IMPARTE = "insert into Imparte(profesor,nombre_curso) VALUES (?,?)";
	
	private final static String QUERY_FIND_PROFESORS = "select nombre, descripcion from Terceros where colectivo = \"PROFESOR\"";
	
	private final static String QUERY_FIND_FECHAS_CURSO = "select fecha from Fecha_Imparticion where nombre_curso = ?";
	
	private final static String QUERY_CANCELAR_CURSO = "update Actividad_Formativa set estado = \"CANCELADA\" where nombre_curso = ?";
	
	private final static String QUERY_CANCELAR_INSCRITO = "update apuntado set estado = \"CANCELADO\", cantidad_devolver = cantidad_abonada where nombre_curso = ? and dni = ?";
	
	private final static String QUERY_FIND_CONFIGURED_IMPARTICION = "select * from Fecha_Imparticion where duracion > 0";
	
	private final static String QUERY_UPDATE_IMPARTICION = "update Fecha_Imparticion set hora = ?, duracion = ? where nombre_curso = ? and fecha = ?";
	
	private final static String QUERY_OBTENER_ACTIVIDADES_FORMATIVAS_CURRENT = "SELECT a.nombre_curso, a.precio, a.fecha_orientativa, a.is_open, a.fin_inscripcion, a.numero_plazas, a.estado FROM Actividad_Formativa a "
			+ " WHERE a.fecha_orientativa >= \"%s\" and a.estado != \"CANCELADA\";";
	
	private final static String QUERY_SET_ESTADO = "UPDATE Colegiado set tipoSolicitud = ? WHERE dni = ?";
	
	private final static String QUERY_DELETE_APUNTADO = "DELETE FROM apuntado WHERE dni = ? and pagado = 1;";
	private final static String QUERY_DELETE_RECIBO = "DELETE FROM recibo WHERE dni = ? and pagado = 1;";
	private final static String QUERY_DELETE_PERITO = "DELETE FROM perito WHERE id_colegiado = ?";
	private final static String QUERY_DE_BAJA = "update colegiado set tipoSolicitud = \"DE BAJA\" where id_colegiado = ?";
	
	private final static String QUERY_BY_DNI = "SELECT dni, nombre, apellidos, telefono FROM Colegiado WHERE dni = ? and tipoSolicitud = \"ACEPTADA\";";
	
	private final static String QUERY_GET_PENDING_AMOUNT = "SELECT SUM(cantidad) FROM recibo WHERE dni = ? and pagado = 0";
	
	private Connection conn;
	
	public DataBaseManagement()
	{
		try {
			conn = DatabaseConnection.getConnection();
			conn.setAutoCommit(false);
		}
		catch(Exception e)
		{
			
		}
			
	}
	
	public void finalizar()
	{
		try
		{
			conn.commit();
		} catch(Exception e) {
			
		}
			
	}
	
	public Connection getConn() {
		return this.conn;
	}
	
	public void cancelar()
	{
		try
		{
			conn.rollback();
		} catch(Exception e) {
		
		}
	}
	
	public boolean addActividadToDataBase(ActividadFormativaDTO actividad)
	{
		try
		{	
			PreparedStatement st = conn.prepareStatement(QUERY_INSERT_ACTIVIDAD_FORMATIVA);
			st.setString(1, actividad.title);
			st.setDouble(2, actividad.price);
			st.setString(3, actividad.days.get(0).toLocalDate().toString());
			st.executeUpdate();
			for(Date day : actividad.days)
			{
				CallableStatement st2 = conn.prepareCall(String.format(QUERY_INSERT_FECHA_IMPARTICION,actividad.title,day));
				st2.executeUpdate();
			}
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	public List<ActividadFormativaDTO> getActividadesFormativasFrom(Integer año)
	{
		try
		{	
			PreparedStatement st = conn.prepareStatement(String.format(QUERY_OBTENER_ACTIVIDADES_FORMATIVAS,año.toString() + "-1-1"));
			ResultSet rs = st.executeQuery();
			List<ActividadFormativaDTO> lista = VisualizarInscritosCursoControler.toDTOList(rs);
			st.close();
			rs.close();
			return lista;
			
		} catch (SQLException e) { }
		return null;
	}
	
	public List<ApuntadoDTO> getInscritosEn(String actividadFormativa)
	{
		List<ApuntadoDTO> nombres = new ArrayList<ApuntadoDTO>();
		try
		{	
			PreparedStatement st2 = conn.prepareStatement(String.format(QUERY_FIND_ACTIVIDAD_FORMATIVA_BY_ID,actividadFormativa));
			ResultSet rs2 = st2.executeQuery();
			if(!rs2.next())
			{
				return null;
			}
			PreparedStatement st = conn.prepareStatement(String.format(QUERY_OBTENER_INSCRITOS_COLEGIADO,actividadFormativa));
			ResultSet rs = st.executeQuery();
			nombres = VisualizarInscritosCursoControler.toApuntadoCList(rs);
			PreparedStatement st3 = conn.prepareStatement(String.format(QUERY_OBTENER_INSCRITOS_TERCEROS,actividadFormativa));
			ResultSet rs3 = st3.executeQuery();
			nombres.addAll(VisualizarInscritosCursoControler.toApuntadoTList(rs3));
			st.close();
			rs.close();
			st2.close();
			rs2.close();
			st3.close();
			rs3.close();
			return nombres;
			
		} catch (SQLException e) {}
		return nombres;
	}
	
	public double getIngresosFor(String actividadFormativa)
	{
		double d = 0;
		try
		{	
			PreparedStatement st = conn.prepareStatement(String.format(QUERY_INGRESOS_FOR_ACTIVIDAD_FORMATIVA,actividadFormativa));
			ResultSet rs = st.executeQuery();
			if(rs.next())
			{
				d = rs.getDouble(1);
			}
			st.close();
			rs.close();
			
		} catch (SQLException e) {}
		return d;
	}

	public int getNextNumeroFormulario() {
		int d = 0;
		try
		{	

			PreparedStatement st = conn.prepareStatement(String.format(QUERY_NEXT_FORMULARIO_NUM));
			ResultSet rs = st.executeQuery();
			if(rs.next())
			{
				d = rs.getInt(1) + 1;
			}

			st.close();
			rs.close();
			
		} catch (SQLException e) {}
		return d;
	}
	
	public boolean addFormulario(FormularioPericialDTO actividad)
	{
		try
		{	

			PreparedStatement st = conn.prepareStatement(String.format(QUERY_INSERT_ACTIVIDAD_PERICIAL,actividad.numero,actividad.tipo_pericial,actividad.prioridad,
					actividad.nombre_solicitante,actividad.mail_solicitante,actividad.telefono_solicitante,actividad.descripcion,actividad.estado));
			st.executeUpdate();
			st.close();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	public List<String[]> getColegiadosPendientes(List<String> colegiados)
	{
		List<String[]> pendientes = new ArrayList<String[]>();
		try
		{	
			PreparedStatement st = conn.prepareStatement(QUERY_SELECT_PENDING_REQUEST_BY_DNI);
			for(String dni : colegiados)
			{
				st.setString(1, dni);
				ResultSet rs = st.executeQuery();
				String[] elemento = SolicitarTitulacionControler.toStringElement(rs);
				if(elemento != null) pendientes.add(elemento);
			}
			st.close();
		} catch (SQLException e) {
		}
		return pendientes;
	}

	public List<ColegiadoInscritoDTO> getAllColegiadosPendientes() {
		List<ColegiadoInscritoDTO> pendientes = new ArrayList<ColegiadoInscritoDTO>();
		try
		{	
			PreparedStatement st = conn.prepareStatement(QUERY_SELECT_PENDING_REQUEST);
			ResultSet rs = st.executeQuery();
			pendientes = SolicitarTitulacionControler.toApuntadoList(rs);
			st.close();
		} catch (SQLException e) {
		}
		return pendientes;
	}
	
	public ColegiadoInscritoDTO getColegiadoPendienteByDni(String dni) {
		List<ColegiadoInscritoDTO> pendientes = new ArrayList<ColegiadoInscritoDTO>();
		try
		{	
			PreparedStatement st = conn.prepareStatement(QUERY_SELECT_PENDING_REQUEST_BY_DNI);
			st.setString(1, dni);
			ResultSet rs = st.executeQuery();
			pendientes = SolicitarTitulacionControler.toApuntadoList(rs);
			st.close();
		} catch (SQLException e) {
		}
		return pendientes.size() == 1 ? pendientes.get(0) : null;
	}
	
	public ColegiadoInscritoDTO getColegiadoByDni(String dni) {
		List<ColegiadoInscritoDTO> pendientes = new ArrayList<ColegiadoInscritoDTO>();
		try
		{	
			PreparedStatement st = conn.prepareStatement(QUERY_BY_DNI);
			st.setString(1, dni);
			ResultSet rs = st.executeQuery();
			pendientes = SolicitarTitulacionControler.toApuntadoList(rs);
			st.close();
		} catch (SQLException e) {
	
		}
		return pendientes.size() == 1 ? pendientes.get(0) : null;
	}

	public void setValidando(List<String> dnis) {
		try
		{	
			PreparedStatement st = conn.prepareStatement(QUERY_SET_VALIDANDO);
			for(String dni : dnis)
			{
				st.setString(1, dni);
				st.executeUpdate();
			}
			st.close();
		} catch (SQLException e) {
		}
	}
	
	public void setEstado(String dni, String estado) {
		try
		{	
			PreparedStatement st = conn.prepareStatement(QUERY_SET_ESTADO);
			st.setString(2, dni);
			st.setString(1, estado);
			st.executeUpdate();
		} catch (SQLException e) {
		}
	}
	
	public List<ProfesorDTO> findAllProfesor()
	{
		List<ProfesorDTO> profesores = new ArrayList<ProfesorDTO>();
		try
		{	
			PreparedStatement st = conn.prepareStatement(QUERY_FIND_PROFESORS);
			ResultSet rs = st.executeQuery();
			profesores = ConfigurarActividadControler.toProfesorList(rs);
			st.close();
		} catch (SQLException e) {
		}
		return profesores;
	}
	
	public List<Date> findImparticionesFor(String curso)
	{
		List<Date> dates = new ArrayList<Date>();
		try 
		{	
			PreparedStatement st = conn.prepareStatement(QUERY_FIND_FECHAS_CURSO);
			st.setString(1, curso);
			ResultSet rs = st.executeQuery();
			while(rs.next())
			{
				dates.add(rs.getDate(1));
			}
			st.close();
		} catch (SQLException e) {
		}
		return dates;
	}
	
	public boolean configurarImparticion(String curso, Date fecha, String hora, int duracion)
	{
		if(seSolapa(fecha,hora,duracion)) return false;
		try
		{	
			PreparedStatement st = conn.prepareStatement(QUERY_UPDATE_IMPARTICION);
			st.setString(1, hora);
			st.setInt(2, duracion);
			st.setString(3, curso);
			st.setDate(4, fecha);
			st.executeUpdate();
			st.close();
		} catch (SQLException e) {
		}
		return true;
	}
	
	public boolean addProfesorCurso(String curso, String profesor)
	{
		try
		{	

			PreparedStatement st = conn.prepareStatement(QUERY_IMPARTE);
			st.setString(1, profesor);
			st.setString(2, curso);
			st.executeUpdate();
			st.close();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public List<String> getColectivos() {
		List<String> cols = new ArrayList<String>();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			
			st = conn.prepareStatement(GET_COLECTIVOS);
			
			rs = st.executeQuery();			
			
			while(rs.next()) {
				cols.add(rs.getString("nombre_colectivo"));
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);	
			
		} finally {
			try {
				rs.close();
				st.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);				
			}			
		}
		
		return cols;
	}
	
	private boolean seSolapa(Date fecha, String hora, int duracion)
	{
		List<ImparticionDTO> imparticiones = new ArrayList<ImparticionDTO>();
		try
		{	
	
			PreparedStatement st = conn.prepareStatement(QUERY_FIND_CONFIGURED_IMPARTICION);
			ResultSet rs = st.executeQuery();
			st.close();
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
	
	public void cancelarCurso(String nombre_curso) {
		try 
		{	
			PreparedStatement st = conn.prepareStatement(QUERY_CANCELAR_CURSO);
			st.setString(1, nombre_curso);
			st.executeUpdate();
			st.close();
		} catch (SQLException e) {
		}
	}
	
	public void cancelarApuntados(String nombre_curso) {
		try 
		{	
			List<ApuntadoDTO> colegiados = this.getInscritosEn(nombre_curso);
			PreparedStatement st = conn.prepareStatement(QUERY_CANCELAR_INSCRITO);
			st.setString(1, nombre_curso);
			for(ApuntadoDTO c : colegiados) {
				st.setString(2, c.dni);
				st.executeUpdate();
			}
			st.close();
		} catch (SQLException e) {
		}
	}
	
	public List<ActividadFormativaDTO> getAllCurrentCursos() {
		try
		{	
			PreparedStatement st = conn.prepareStatement(String.format(QUERY_OBTENER_ACTIVIDADES_FORMATIVAS_CURRENT,LocalDate.now().toString()));
			ResultSet rs = st.executeQuery();
			List<ActividadFormativaDTO> lista = CanceladorCursoControler.toDTOList(rs);
			for(ActividadFormativaDTO af : lista) {
				af.numeroInscritos = this.getInscritosEn(af.title).size();
			}
			st.close();
			rs.close();
			return lista;
			
		} catch (SQLException e) { }
		return null;
	}
	
	public boolean darDeBaja(String id_colegiado) {
		if(this.getColegiadoByDni(id_colegiado) != null)
		{
			try 
			{	
				PreparedStatement st = conn.prepareStatement(QUERY_DE_BAJA);
				st.setString(1, id_colegiado);
				st.executeUpdate();
				st.close();
			} catch (SQLException e) {
				return false;
			}
			deleteColegiado(id_colegiado);
			return true;
		}
		return false;
	}
	
	private void deleteColegiado(String id_colegiado) {
		try
		{	
			PreparedStatement st = conn.prepareStatement(QUERY_DELETE_APUNTADO);
			PreparedStatement st2 = conn.prepareStatement(QUERY_DELETE_RECIBO);
			PreparedStatement st3 = conn.prepareStatement(QUERY_DELETE_PERITO);
			st.setString(1, id_colegiado);
			st2.setString(1, id_colegiado);
			st3.setString(1, id_colegiado);
			st.executeUpdate();
			st2.executeUpdate();
			st3.executeUpdate();
			st.close();
			st2.close();
			st3.close();
			
		} catch (SQLException e) {}
	}
	
	public double amountAPagar(String id_colegiado) {
		double amount = 0;
		try 
		{	
			PreparedStatement st = conn.prepareStatement(QUERY_GET_PENDING_AMOUNT);
			st.setString(1, id_colegiado);
			ResultSet rs = st.executeQuery();
			rs.next();
			amount = rs.getDouble(1);
			st.close();
		} catch (SQLException e) { 
		}
		return amount;
	}
}
