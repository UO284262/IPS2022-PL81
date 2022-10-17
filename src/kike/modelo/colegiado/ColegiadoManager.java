package kike.modelo.colegiado;

import kike.persistence.ColegiadoDataBase;

public class ColegiadoManager {
	
	public ColegiadoDTO dto;

	public ColegiadoManager(String id) {
		dto = new ColegiadoDTO();
		dto.id_colegiado = id;
	}

	public boolean validaID() {
		return ColegiadoDataBase.isValidId(dto.id_colegiado);
	}

	public void getInfo() {
		dto = ColegiadoDataBase.findById(dto.id_colegiado);
	}

}
