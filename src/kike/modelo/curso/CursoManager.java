package kike.modelo.curso;

import java.sql.Date;

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
		modelo.addAll(CursoDataBase.getCursosAbiertos());
		return modelo;
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

