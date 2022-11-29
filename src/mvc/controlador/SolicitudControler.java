package mvc.controlador;



import mvc.modelo.colegiado.ColegiadoDto2;
import mvc.modelo.colegiado.NuevosColegiadosModel;


public class SolicitudControler {
	
	private NuevosColegiadosModel model = new NuevosColegiadosModel();
	

	public void validarSolicitud( ColegiadoDto2 colegiado) {
					
						model.addColegiado(colegiado);
						
			
	}
	
	public boolean colegiadoExistente(String dni) {
		return model.isTrueColegiado(dni);
	}
			
	
	
	
	
	
	
	
}
