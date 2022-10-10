package rodro.modelo;

import java.util.Date;

public class ReciboDto {
	
	public int idRecibo;
	public String dniColegiado;
	public Date emision;
	public String iban;
	public double cantidad;
	
	
	
	public int getIdRecibo() {
		return idRecibo;
	}
	public void setIdRecibo(int idRecibo) {
		this.idRecibo = idRecibo;
	}
	public String getDniColegiado() {
		return dniColegiado;
	}
	public void setDniColegiado(String dniColegiado) {
		this.dniColegiado = dniColegiado;
	}
	public Date getEmision() {
		return emision;
	}
	public void setEmision(Date emision) {
		this.emision = emision;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	
	
	
	

}
