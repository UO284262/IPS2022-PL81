package mvc.modelo;

import java.time.LocalDate;

public class ColegiadoInscritoDTO {
	public String id_colegiado;
	public String dni;
	public String nombre;
	public String apellidos;
	public double cantidad_abonada;
	public String estado;
	public LocalDate fecha_inscripcion; // = LocalDateTime.now();
	public int telefono;
}
