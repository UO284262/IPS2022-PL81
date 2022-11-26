package kike.persistence.dto;

import java.sql.Date;

public class InscripcionDTO {
	
	public enum TipoInscripcion {PRE_INSCRITO, INSCRITO, CANCELADO};
	
	public String dni;
	public String nombre_curso;
	public Date fecha_Inscripcion;
	public boolean pagado;
	public TipoInscripcion estado;
	public double cantidad_abonada;
	public Integer pos_cola = null;
}
