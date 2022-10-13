package rodro.controlador;

import rodro.modelo.NuevosColegiadosModel;
import rodro.vista.VentanaSolicitud;

public class SolicitudControler {
	
	private NuevosColegiadosModel model;
	private VentanaSolicitud view;
	
	public SolicitudControler(NuevosColegiadosModel model, 
			VentanaSolicitud view) {
		this.model = model;
		this.view = view;
		
		this.initView();
	}
	
	private void initView() {
		view.getContentPane().setVisible(true);
		
	}
	
	/**
	 * Cancela el registro de un nuevo trabajador
	 */
	public void cancelarRegistro() {
		view.getContentPane().setVisible(false);
	}

	public void validarSolicitud(String dni) {
//		if (camposCorrectos()) {
//				if (telefonoCorrecto()) {
//					if (!model.isTrueColegiado(view.getUsuario())){
//						model.addColegiado(newVolegiado());
//						view.completado();
//						view.getFrame().setVisible(false);
//					} else {
//						view.mostrarErrorTrabajadorExistente();
//					}
//				} else {
//					view.mostrarErrorTelefono();
//				}
//			
//			
//		} else {
//			view.mostrarError();
//		}
	}
	
//	private boolean telefonoCorrecto() {
//		try {
//			Integer.parseInt(view.getTelefono());
//			if (view.getTelefono().trim().length() == 9) {
//				return true;
//			}
//			return false;
//		} catch (Exception e) {
//			return false;
//		}
//	}
	
//	/**
//	 * Se comprueba que todos los campos no están vacíos
//	 */
//	private boolean camposCorrectos() {
//		if (!view.getNombre().trim().isEmpty()) {
//			if (!view.getApellidos().trim().isEmpty()) {
//				if (!view.getDni().trim().isEmpty()) {
//					if(!view.getUsuario().trim().isEmpty()) {
//						if (!view.getOcupacion().trim().isEmpty()) {
//							if (!view.getContraseña().trim().isEmpty()) {
//								if (!view.getHoraInicial().trim().isEmpty()) {
//									if (!view.getHoraFinal().trim().isEmpty()) {
//										return true;
//									}
//								}
//							}
//						}
//						
//					}
//				}
//			}
//		}
//		
//		return false;
//	}
//	
	
	
}
