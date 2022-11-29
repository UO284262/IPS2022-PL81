package mvc.controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.DatabaseConnection;
import mvc.modelo.curso.CursoDTO;

public class CursoDataBase {

	private final static String CURSOS_SIN_ABRIR = "select * from Actividad_formativa where is_open = false and estado != 'CANCELADA'";
	private final static String CURSOS_ABRIR = "update Actividad_formativa set is_open = true, numero_plazas = ?, inicio_inscripcion = ?, fin_inscripcion = ? where nombre_curso = ? and estado != 'CANCELADA'";
	private final static String CURSOS_INSCRIBIR = "update Actividad_formativa set numero_plazas = ? where nombre_curso = ? and estado != 'CANCELADA'";
	private final static String CURSOS_ABIERTOS = "select * from Actividad_formativa where is_open = true and estado != 'CANCELADA'";
	private final static String CURSOS_ABIERTOS_COLECTIVO = "select * from Actividad_formativa af natural join colectivos_asignados ca where af.is_open = true and ca.nombre_colectivo = ? and estado != 'CANCELADA'";

	private final static String CURSOS_MAKE_CANCELABLE = "update Actividad_formativa set cancelable = true, a_devolver = ? where nombre_curso = ?";
	
	private static final String FECHAS_CURSOS = "select * from fecha_imparticion where nombre_curso = ?";
	private static final String GET_COLECTIVOS = "select * from colectivos_asignados where nombre_curso = ?";
	private static final String INSERT_COLECTIVO = "insert into colectivos_asignados(nombre_curso,nombre_colectivo,precio_colectivo) values (?,?,?)";

	
	public static List<CursoDTO> getCursosSinAbrir() {
		List<CursoDTO> cursos = null;
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			conn = DatabaseConnection.getConnection();			
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement(CURSOS_SIN_ABRIR);
			rs = st.executeQuery();			
			
			cursos = toCursoDTOList(rs);
			
			for(CursoDTO c : cursos) {
				rellenarFechas(c, conn);
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
		
		return cursos;
	}
	
	public static void abrirCurso(CursoDTO curosDTO) {
		Connection conn = null;
		PreparedStatement st = null;
		
		try
		{
			conn = DatabaseConnection.getConnection();			
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement(CURSOS_ABRIR);
			
			st.setInt(1, curosDTO.plazasDisponibles);
			st.setDate(2, curosDTO.fechaInicioInscipcion);
			st.setDate(3, curosDTO.fechaFinInscipcion);
			st.setString(4, curosDTO.title);
			
			st.executeUpdate();	
			
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
				st.close();
				conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);				
			}			
		}		
	}
	
	public static void inscribirCurso(CursoDTO curosDTO) {
		Connection conn = null;
		PreparedStatement st = null;
		
		try
		{
			conn = DatabaseConnection.getConnection();			
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement(CURSOS_INSCRIBIR);
			
			st.setInt(1, curosDTO.plazasDisponibles);
			st.setString(2, curosDTO.title);
			
			st.executeUpdate();	
			
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
				st.close();
				conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);				
			}			
		}	
	}
	
	public static List<CursoDTO> getCursosAbiertos() {
		List<CursoDTO> cursos = null;
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			conn = DatabaseConnection.getConnection();			
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement(CURSOS_ABIERTOS);
			rs = st.executeQuery();			
			
			cursos = toCursoDTOList(rs);
			
			for(CursoDTO c : cursos) {
				rellenarFechas(c, conn);
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
		
		return cursos;
	}
	
	private static void rellenarFechas(CursoDTO cdto, Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{					
			st = conn.prepareStatement(FECHAS_CURSOS);
			
			st.setString(1, cdto.title);
			
			rs = st.executeQuery();			
						
			while(rs.next()) {
				cdto.days.add(rs.getDate("fecha"));
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
	}

	private static List<CursoDTO> toCursoDTOList(ResultSet rs) throws SQLException {
		List<CursoDTO> res = new ArrayList<>();
		while(rs.next()) {
			res.add( toCursoDto( rs ) );
		}
		return res;
	}
	
	private static CursoDTO toCursoDto(ResultSet m) throws SQLException {
		CursoDTO dto = new CursoDTO();
		dto.title = m.getString("nombre_curso");
		dto.price = m.getDouble("precio");
		
		dto.fechaInicioInscipcion = m.getDate("inicio_inscripcion");
		dto.fechaFinInscipcion = m.getDate("fin_inscripcion");
		dto.plazasDisponibles = m.getInt("numero_plazas");
		dto.abierto = m.getBoolean("is_open");
		
		dto.days = new ArrayList<>();
		
		dto.plazasSolicitadas = InscripcionDataBase.getApuntados(dto.title);
		
		return dto;
	}

	public static HashMap<String, Double> getColectivos(CursoDTO curso) {
		HashMap<String,Double> cols = new HashMap<String, Double>();
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			conn = DatabaseConnection.getConnection();			
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement(GET_COLECTIVOS);			
			st.setString(1, curso.title);
			
			rs = st.executeQuery();			
			
			while(rs.next()) {
				cols.put(rs.getString("nombre_colectivo"), rs.getDouble("precio_colectivo"));
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
	
	public static void inscribirColectivos(List<ColectivoCursoDTO> colectivos, Connection conn ) {
		if(colectivos.isEmpty()) {
			return;
		}
		
		PreparedStatement st = null;
		
		try
		{
			
			st = conn.prepareStatement(INSERT_COLECTIVO);
			
			for (ColectivoCursoDTO cc : colectivos) {			
				st.setString(1, cc.nombre_curso);
				st.setString(2, cc.nombre_colectivo);
				st.setDouble(3, cc.precio);
				
				st.executeUpdate();	
			}			
			
		} catch (SQLException e) {
			throw new RuntimeException(e);	
			
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);				
			}			
		}	
	}

	public static List<CursoDTO> getCursosAbiertosColectivo(String colectivo) {
		List<CursoDTO> cursos = null;
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			conn = DatabaseConnection.getConnection();			
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement(CURSOS_ABIERTOS_COLECTIVO);
			st.setString(1, colectivo);
			
			rs = st.executeQuery();			
			
			cursos = toCursoDTOListWithCol(rs);
			
			for(CursoDTO c : cursos) {
				rellenarFechas(c, conn);
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
		
		return cursos;
	}
	
	private static List<CursoDTO> toCursoDTOListWithCol(ResultSet rs) throws SQLException {
		List<CursoDTO> res = new ArrayList<>();
		while(rs.next()) {
			res.add( toCursoDtoWithCol( rs ) );
		}
		return res;
	}
	
	private static CursoDTO toCursoDtoWithCol(ResultSet m) throws SQLException {
		CursoDTO dto = new CursoDTO();
		dto.title = m.getString("nombre_curso");
		dto.price = m.getDouble("precio_colectivo");
		
		dto.fechaInicioInscipcion = m.getDate("inicio_inscripcion");
		dto.fechaFinInscipcion = m.getDate("fin_inscripcion");
		dto.plazasDisponibles = m.getInt("numero_plazas");
		dto.abierto = m.getBoolean("is_open");
		
		dto.days = new ArrayList<>();
		
		dto.plazasSolicitadas = InscripcionDataBase.getApuntados(dto.title);
		
		return dto;
	}

	public static void makeCancelable(String nombre_curso, int porcentajeDevolver, Connection conn) {
		
		PreparedStatement st = null;
		
		try
		{
			
			st = conn.prepareStatement(CURSOS_MAKE_CANCELABLE);
			
			st.setInt(1, porcentajeDevolver);
			st.setString(2, nombre_curso);
			
			st.executeUpdate();	
						
		} catch (SQLException e) {
			throw new RuntimeException(e);	
			
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);				
			}			
		}	
		
	}
	
}
