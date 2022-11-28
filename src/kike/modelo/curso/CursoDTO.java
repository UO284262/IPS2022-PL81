package kike.modelo.curso;

import java.sql.Date;
import java.util.List;

public class CursoDTO {
	public String title;
	public double price; 
	public List<Date> days;
	public boolean abierto;
	public Date fechaInicioInscipcion;
	public Date fechaFinInscipcion;
	public int plazasDisponibles;
	public int plazasSolicitadas;
	
	@Override
	public String toString() {
		return title + " - Precio: " + price;
	}
}
