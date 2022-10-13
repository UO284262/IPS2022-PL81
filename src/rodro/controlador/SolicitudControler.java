package rodro.controlador;

import java.time.LocalDateTime;

import rodro.modelo.ColegiadoDto;
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
		if (camposCorrectos()) {
				if (telefonoCorrecto()) {
					if (!model.isTrueColegiado(view.getDni())){
						model.addColegiado(newColegiado());
						view.completado();
						view.getJDialog().setVisible(false);
					} else {
						view.mostrarErrorColegiadoExistente();
					}
				} else {
					view.mostrarErrorTelefono();
				}			
		} else {
			view.mostrarError();
	}
	}
	
	
	private boolean telefonoCorrecto() {
		try {
			Integer.parseInt(view.getTelefono());
			if (view.getTelefono().trim().length() == 9) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	private ColegiadoDto newColegiado() {
		ColegiadoDto dto = new ColegiadoDto();
		dto.apellidos = view.getApellidos();
		dto.dni = view.getDni();
		dto.nombre = view.getNombre();
		dto.tlfn = Integer.parseInt(view.getTelefono());
		dto.poblacion = view.getPoblacion();
		dto.titulacion = view.getTitulacion();
		dto.año = Integer.parseInt(view.getAño());
		dto.cuentaBancaria = view.getCuentaBancaria();
		dto.fecha = LocalDateTime.now();
		dto.isValid = false;
		
		
		
		return dto;
	}
	
	/**
	 * Se comprueba que todos los campos no están vacíos
	 */
	private boolean camposCorrectos() {
		if (!view.getNombre().trim().isEmpty()) {
			if (!view.getApellidos().trim().isEmpty()) {
				if (!view.getDni().trim().isEmpty()) {
					if (!view.getPoblacion().isEmpty()) {
						if (!view.getTitulacion().isEmpty()) {
							if (!view.getAño().isEmpty()) {
								if (!view.getCentro().isEmpty()) {
									if (!view.getCuentaBancaria().isEmpty()) {
										return true;
									}
								}
							}
						}
					}

				}
			}
		}

		return false;
	}
	
	
	
}
