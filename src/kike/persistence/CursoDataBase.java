package kike.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kike.modelo.curso.CursoDTO;
import kike.modelo.curso.CursoDTOForColegiados;
import main.DatabaseConnection;

public class CursoDataBase {

	private final static String CURSOS_SIN_ABRIR = "select * from Actividad_formativa where is_open = false";
	private final static String CURSOS_ABRIR = "update Actividad_formativa set is_open = true, numero_plazas = ?, inicio_inscripcion = ?, fin_inscripcion = ? where nombre = ?";
	private final static String CURSOS_INSCRIBIR = "update Actividad_formativa set numero_plazas = ? where nombre = ?";
	private final static String CURSOS_ABIERTOS = "select * from Actividad_formativa where is_open = true";
	private static final String FECHAS_CURSOS = "select * from fecha_imparticion where nombre = ?";
	
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
				// TODO Auto-generated catch block
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
	
	public static List<CursoDTOForColegiados> getCursosAbiertos() {
		List<CursoDTOForColegiados> cursos = null;
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			conn = DatabaseConnection.getConnection();			
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement(CURSOS_ABIERTOS);
			rs = st.executeQuery();			
			
			cursos = toCursoDTOForColegiadosList(rs);
			
			for(CursoDTOForColegiados c : cursos) {
				rellenarFechas(c.cdto, conn);
			}
			
			conn.commit();
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
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
		dto.title = m.getString("nombre");
		dto.price = m.getDouble("precio");
		
		dto.fechaInicioInscipcion = m.getDate("inicio_inscripcion");
		dto.fechaFinInscipcion = m.getDate("fin_inscripcion");
		dto.plazasDisponibles = m.getInt("numero_plazas");
		dto.abierto = m.getBoolean("is_open");
		
		dto.days = new ArrayList<>();
		
		return dto;
	}

	private static List<CursoDTOForColegiados> toCursoDTOForColegiadosList(ResultSet rs) throws SQLException {
		List<CursoDTOForColegiados> res = new ArrayList<>();
		while(rs.next()) {
			res.add(new  CursoDTOForColegiados(toCursoDto( rs )));
		}
		return res;
	}

	public static void actualizarCurso(CursoDTO curosDTO) {
		// TODO Auto-generated method stub
		
	}
	
}