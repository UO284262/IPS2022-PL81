package kike.modelo.curso;

import java.sql.Date;

public class CursoDTOForColegiados {
	
	public CursoDTO cdto;

	public CursoDTOForColegiados(CursoDTO cdto) {
		this.cdto = cdto;
	}
	
	@Override
	public String toString() {
		return cdto.title + " - Precio: " + cdto.price + " - Fecha de Inicio: " + getFirstDate();
	}

	private Date getFirstDate() {
		Date fechamin = cdto.days.get(0);
		for(Date fecha : cdto.days) {
			if(fecha.compareTo(fechamin) < 0) {
				fechamin = fecha;
			}
		}
		return fechamin;
	}

}
