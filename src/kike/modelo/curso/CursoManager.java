package kike.modelo.curso;

import kike.persistence.CursoDataBase;

public class CursoManager {
	
	CursoDTO curosDTO;
	
	public CursoManager(CursoDTO dto) {
		curosDTO = dto;
	}
	
	public void abrirCurso() {
		curosDTO.abierto = true;		
		CursoDataBase.abrirCurso(curosDTO);
	}
	
		
	public void ocuparPlaza() {
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
}

