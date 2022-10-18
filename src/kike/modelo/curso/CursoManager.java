package kike.modelo.curso;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListModel;

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

	public static DefaultListModel<CursoDTOForColegiados> getModeloCursosAbiertos() {
		DefaultListModel<CursoDTOForColegiados> modelo = new DefaultListModel<CursoDTOForColegiados>();
		
		modelo.addAll(getCursosDisponibles(CursoDataBase.getCursosAbiertos()));
		return modelo;
	}

	private static List<CursoDTOForColegiados> getCursosDisponibles(List<CursoDTOForColegiados> cursosAbiertos) {
		Iterator<CursoDTOForColegiados> it = cursosAbiertos.iterator();
		
		while(it.hasNext()) {
			CursoDTOForColegiados dto = it.next();
			if(dto.cdto.plazasDisponibles <= 0 || 
					dto.cdto.fechaInicioInscipcion.compareTo(new Date(System.currentTimeMillis())) > 0 ||
					dto.cdto.fechaFinInscipcion.compareTo(new Date(System.currentTimeMillis())) < 0) {
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
}

