package kike.persistence.dto;

import java.sql.Date;

public class InscripcionDTO {
	
	public enum TipoInscripcion {PRE_INSCRITO, INSCRITO, CANCELADO};
	
	public String id_socio;
	public String nombre_curso;
	public Date fecha_Inscripcion;
	public boolean pagado;
	public TipoInscripcion estado; 
}
