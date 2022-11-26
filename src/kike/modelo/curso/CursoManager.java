package kike.modelo.curso;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import kike.persistence.CursoDataBase;
import kike.persistence.InscripcionDataBase;
import kike.persistence.dto.InscripcionDTO;
import kike.persistence.dto.InscripcionDTO.TipoInscripcion;

public class CursoManager {
	
	private CursoDTO curosDTO;
	
	public CursoManager(CursoDTO dto) {
		curosDTO = dto;
	}
	
	public CursoDTO getCurosDTO() {
		return curosDTO;
	}
	
	public void abrirCurso() {
		curosDTO.abierto = true;		
		CursoDataBase.abrirCurso(curosDTO);
	}
	
	@Override
	public String toString() {
		return curosDTO.title + " - Precio: " + curosDTO.price;
	}

	public static List<CursoDTO> getModeloCursosAbiertos() {			
		return getCursosDisponibles(CursoDataBase.getCursosAbiertos());
	}
	
	public static List<CursoDTO> getModeloCursosAbiertosColectivo(String colectivo) {			
		return getCursosDisponibles(CursoDataBase.getCursosAbiertosColectivo(colectivo));
	}

	private static List<CursoDTO> getCursosDisponibles(List<CursoDTO> cursosAbiertos) {
		Iterator<CursoDTO> it = cursosAbiertos.iterator();
		
		while(it.hasNext()) {
			CursoDTO dto = it.next();
			if(dto.fechaInicioInscipcion.compareTo(new Date(System.currentTimeMillis())) > 0 ||
					dto.fechaFinInscipcion.compareTo(new Date(System.currentTimeMillis())) < 0) {
				it.remove();
			}
		}
		
		return cursosAbiertos;
	}

	public void inscribirse(String dni) {
		InscripcionDTO idto = new InscripcionDTO();
		
		idto.dni = dni;
		idto.nombre_curso = curosDTO.title;
		idto.fecha_Inscripcion = new Date(System.currentTimeMillis());
		idto.pagado = false;
		idto.estado = TipoInscripcion.PRE_INSCRITO;
		idto.cantidad_abonada = 0;
		
		InscripcionDataBase.createPreInscripcion(idto);	
	}
	
	public void inscribirse(String dni, int cola) {
		InscripcionDTO idto = new InscripcionDTO();
		
		idto.dni = dni;
		idto.nombre_curso = curosDTO.title;
		idto.fecha_Inscripcion = new Date(System.currentTimeMillis());
		idto.pagado = false;
		idto.estado = TipoInscripcion.PRE_INSCRITO;
		idto.cantidad_abonada = 0;
		idto.pos_cola = cola;
		
		InscripcionDataBase.createPreInscripcion(idto);	
	}

	public static Date getPrimerDiaCurso(CursoDTO dto) {
		Date minDay = null;
		for(Date d : dto.days) {
			if(minDay == null || minDay.compareTo(d)>0) {
				minDay = d;
			}
		}
		return minDay;
	}

	public static List<CursoDTO> removePastCursos(List<CursoDTO> cursos) {
		Iterator<CursoDTO> it = cursos.iterator();
		
		while(it.hasNext()) {
			CursoDTO dto = it.next();
			if(getPrimerDiaCurso(dto).compareTo(new Date(System.currentTimeMillis())) < 0) {
				it.remove();
			}
		}
		
		return cursos;
	}
}

