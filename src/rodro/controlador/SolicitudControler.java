package rodro.controlador;



import rodro.modelo.ColegiadoDto;
import rodro.modelo.NuevosColegiadosModel;


public class SolicitudControler {
	
	private NuevosColegiadosModel model = new NuevosColegiadosModel();
	

	public void validarSolicitud( ColegiadoDto colegiado) {
					
						model.addColegiado(colegiado);
						
			
	}
	
	public boolean colegiadoExistente(String dni) {
		return model.isTrueColegiado(dni);
	}
			
	
	
	
	
	
	
	
}
