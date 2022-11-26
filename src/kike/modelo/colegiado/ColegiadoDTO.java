package kike.modelo.colegiado;

import java.time.LocalDateTime;

public class ColegiadoDTO {
	
	public final static int SOLICITUD_PRE_COLEGIADO = 0;
	public final static int SOLICITUD_COLEGIADO = 1;
	
	
	public String id_colegiado;
	public String dni;
	public String nombre;
	public String apellidos;
	public String poblacion;
	public String tlfn;
	public String cuentaBancaria;
	public int tipoSolicitud;
	public LocalDateTime fecha; // = LocalDateTime.now();
	public int tipo;
}
