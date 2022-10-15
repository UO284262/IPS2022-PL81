package kike.modelo.colegiado;

import rodro.modelo.NuevosColegiadosModel;

public class ColegiadoManager {
	
	public ColegiadoDTO dto;

	public ColegiadoManager(String id) {
		dto = new ColegiadoDTO();
		dto.id_colegiado = id;
	}

	public boolean validaID() {
		return new NuevosColegiadosModel().isTrueWorker(dto.id_colegiado);
	}

}
