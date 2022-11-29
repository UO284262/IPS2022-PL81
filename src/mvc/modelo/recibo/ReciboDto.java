package mvc.modelo.recibo;

import java.sql.Date;

public class ReciboDto {
	
	public String idRecibo;
	public String dniColegiado;
	public Date emision;
	public String iban;
	public double cantidad;
	
	
	
	
	public String getIdRecibo() {
		return idRecibo;
	}
	public void setIdRecibo(String idRecibo) {
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
	
	
	public String toString() {
		return getIdRecibo() + ", " + getEmision() + ", " + getDniColegiado() + ", " + getIban()+ ", " + getCantidad() +"€ \n"; 
	}
	
	
	
	

}
