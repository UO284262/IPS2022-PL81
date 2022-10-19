package rodro.controlador;



import rodro.modelo.ColegiadoDto;
import rodro.modelo.NuevosColegiadosModel;


public class SolicitudControler {
	
	private NuevosColegiadosModel model = new NuevosColegiadosModel();
	

	public void validarSolicitud(String dni, ColegiadoDto colegiado) {
					if (!colegiadoExistente(dni)){
						model.addColegiado(colegiado);
						}
			
	}
	
	public boolean colegiadoExistente(String dni) {
		if (!model.isTrueColegiado(dni))
			return false;
		return true;
	}
	
	
	
	
	
	
	
}
