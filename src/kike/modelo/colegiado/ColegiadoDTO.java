package kike.modelo.colegiado;

import java.time.LocalDateTime;

public class ColegiadoDTO {
	public String id_colegiado;
	public String dni;
	public String nombre;
	public String apellidos;
	public String poblacion;
	public int tlfn;
	public String cuentaBancaria;
	public int tipoSolicitud;
	public LocalDateTime fecha; // = LocalDateTime.now();
}
