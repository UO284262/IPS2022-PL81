package kike.modelo;

import java.util.Date;

public class Curso {
	private boolean abierto;
	private Date fechaInicioInscipcion;
	private Date fechaFinInscipcion;
	private int plazasDisponibles;
	
	
	public Curso() {
		abierto = false;
	}
	
	public void abrirCurso(Date fi, Date ff, int plazas) {
		fechaFinInscipcion = fi;
		fechaFinInscipcion = ff;
		plazasDisponibles = plazas;
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
}
