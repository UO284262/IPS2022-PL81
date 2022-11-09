package kike.modelo.curso;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import kike.persistence.CursoDataBase;
import kike.persistence.InscripcionDataBase;
import kike.persistence.dto.InscripcionDTO;
import kike.persistence.dto.InscripcionDTO.TipoInscripcion;

public class CursoManager {
	
	CursoDTO curosDTO;
	
	public CursoManager(CursoDTO dto) {
		curosDTO = dto;
	}
	
	public void abrirCurso() {
		curosDTO.abierto = true;		
		CursoDataBase.abrirCurso(curosDTO);
	}
	
		
	private void ocuparPlaza() {
		if(hayPlazasDisponibles())
			curosDTO.plazasDisponibles--;
	}

	public boolean hayPlazasDisponibles() {
		if(curosDTO.plazasDisponibles>0)
			return true;
		return false;
	}
	
		
	@Override
	public String toString() {
		return curosDTO.title + " - Precio: " + curosDTO.price;
	}

	public static List<CursoDTO> getModeloCursosAbiertos() {			
		return getCursosDisponibles(CursoDataBase.getCursosAbiertos());
	}

	private static List<CursoDTO> getCursosDisponibles(List<CursoDTO> cursosAbiertos) {
		Iterator<CursoDTO> it = cursosAbiertos.iterator();
		
		while(it.hasNext()) {
			CursoDTO dto = it.next();
			if(dto.plazasDisponibles <= 0 || 
					dto.fechaInicioInscipcion.compareTo(new Date(System.currentTimeMillis())) > 0 ||
					dto.fechaFinInscipcion.compareTo(new Date(System.currentTimeMillis())) < 0) {
				it.remove();
			}
		}
		
		return cursosAbiertos;
	}

	public void inscribirse(String idSocio) {
		ocuparPlaza();
		InscripcionDTO idto = new InscripcionDTO();
		
		idto.id_socio = idSocio;
		idto.nombre_curso = curosDTO.title;
		idto.fecha_Inscripcion = new Date(System.currentTimeMillis());
		idto.pagado = false;
		idto.estado = TipoInscripcion.PRE_INSCRITO;
		idto.cantidad_abonada = 0;
		
		CursoDataBase.actualizarCurso(curosDTO);
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

