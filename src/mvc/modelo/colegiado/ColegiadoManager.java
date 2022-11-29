package mvc.modelo.colegiado;

import mvc.controlador.ColegiadoDataBase;
import mvc.controlador.InscripcionDataBase;

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

	public boolean isInscrito(String title) {
		return InscripcionDataBase.isInscrito(dto.id_colegiado, title);
	}

}
