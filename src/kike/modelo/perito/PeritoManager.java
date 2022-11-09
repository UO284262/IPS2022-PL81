package kike.modelo.perito;

import java.sql.Date;
import java.util.UUID;

import kike.persistence.PeritoDataBase;

public class PeritoManager {

	private PeritoDTO perito;
	
	public PeritoManager(String id_colegiado) {
		perito = new PeritoDTO();
		perito.id_colegiado = id_colegiado;
	}

	public PeritoManager(PeritoDTO p) {
		this.perito = p;
	}

	public PeritoDTO getPerito() {
		return perito;
	}

	public PeritoDTO inicializarDatos() {
		perito.id_perito = UUID.randomUUID().toString().subSequence(0, 15).toString();
		perito.pos_Lista = PeritoDataBase.getLastInQueue() + 1;
		
		return perito;
	}

	@SuppressWarnings("deprecation")
	public void inscribir() {
		perito.fecha_Tope = new Date(System.currentTimeMillis());
		perito.fecha_Tope.setYear(perito.fecha_Tope.getYear() + 1);
		
		PeritoDataBase.createPerito(perito);
	}

	@SuppressWarnings("deprecation")
	public void renovar() {		
		perito.fecha_Tope.setYear(perito.fecha_Tope.getYear() + 1);
		
		PeritoDataBase.renuevaPerito(perito);
	}
		

}
