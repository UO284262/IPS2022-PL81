package rodro.modelo;

import java.time.LocalDateTime;
import java.util.Objects;

public class ColegiadoDto {
	
	public String dni;
	public String nombre;
	public String apellidos;
	public String poblacion;
	public String titulacion;
	public String centro;
	public int a�o;
	public String cuentaBancaria;
	public int tlfn;
	public boolean isValid = false;
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
	public int getA�o() {
		return a�o;
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
	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColegiadoDto other = (ColegiadoDto) obj;
		return Objects.equals(dni, other.dni);
	}
	

}
