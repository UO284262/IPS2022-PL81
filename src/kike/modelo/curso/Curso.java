package kike.modelo.curso;

import java.sql.Date;
import java.util.List;

public class Curso {
	private String name;
	private double price;
	private boolean abierto;
	private Date fechaInicioInscipcion;
	private Date fechaFinInscipcion;
	private int plazasDisponibles;
	public List<Date> days;	
	
	
	public Curso(String name, double price) {
		this.name = name;
		this.price = price;
		abierto = false;
	}
	
	public void abrirCurso(CursoDTO cdto) {
		fechaFinInscipcion = cdto.fechaFinInscipcion;
		fechaFinInscipcion = cdto.fechaInicioInscipcion;
		plazasDisponibles = cdto.plazasDisponibles;
		abierto = true;
	}
	
	public boolean isAbierto() {
		return abierto;
	}

	public Date getFechaInicioInscipcion() {
		return fechaInicioInscipcion;
	}

	public Date getFechaFinInscipcion() {
		return fechaFinInscipcion;
	}

	public int getPlazasDisponibles() {
		return plazasDisponibles;
	}
	
	public void ocuparPlaza() {
		if(hayPlazasDisponibles())
			plazasDisponibles--;
	}

	public boolean hayPlazasDisponibles() {
		if(plazasDisponibles>0)
			return true;
		return false;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name + " - Precio: " + price;
	}
	
	public CursoDTO toDto() {
		CursoDTO cdto = new CursoDTO();
		
		cdto.title = name;
		cdto.abierto = abierto;
		cdto.days = days;
		cdto.fechaFinInscipcion = fechaFinInscipcion;
		cdto.fechaInicioInscipcion = fechaInicioInscipcion;
		cdto.plazasDisponibles = plazasDisponibles;
		cdto.price = price;
		
		return cdto;
	}
}

