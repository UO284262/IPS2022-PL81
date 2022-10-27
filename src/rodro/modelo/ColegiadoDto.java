package rodro.modelo;

import java.time.LocalDateTime;


public class ColegiadoDto {
	
	public String dni;
	public String nombre;
	public String apellidos;
	public String poblacion;
	public String titulacion;
	public String centro;
	public int año;
	public String cuentaBancaria;
	public int tlfn;
	public int isValid = 0;
	public LocalDateTime fecha; // = LocalDateTime.now();
	
	
	
	public String getNombre() {return nombre;}
	public String getApellidos() {return apellidos;}
	
	public String getDni() {
		return dni;
	}
	public String getPoblacion() {
		return poblacion;
	}
	public String getTitulacion() {
		return titulacion;
	}
	public String getCentro() {
		return centro;
	}
	public int getAño() {
		return año;
	}
	public String getCuentaBancaria() {
		return cuentaBancaria;
	}
	public int getTlfn() {
		return tlfn;
	}
	@Override
	public String toString() {
		return nombre + "-" + apellidos + "-" + dni ;
	}
	
	

}
