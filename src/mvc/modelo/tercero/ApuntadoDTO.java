package mvc.modelo.tercero;

import java.time.LocalDate;

public class ApuntadoDTO {
	public String dni;
	public String nombre;
	public String apellidos;
	public double cantidad_abonada;
	public String estado;
	public LocalDate fecha_inscripcion; // = LocalDateTime.now();
	public String colectivo;
}
