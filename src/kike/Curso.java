package kike;

import java.util.Date;

public class Curso {
	private boolean abierto;
	private Date fechaInicioInscipcion;
	private Date fechaFinInscipcion;
	
	
	public Curso() {
		abierto = false;
	}
	
	public void abrirCurso(Date fi, Date ff) {
		fechaFinInscipcion = fi;
		fechaFinInscipcion = ff;
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
}
