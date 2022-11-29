package mvc.controlador;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import exception.TypeConvertException;
import mvc.modelo.ActividadFormativaDTO;
import mvc.modelo.DataBaseManagement;

public class ActividadFormativaControler {
	
	private List<Date> days = new ArrayList<Date>();
	private DataBaseManagement db = new DataBaseManagement();
	
	@SuppressWarnings("deprecation")
	public String añadirDia(int dia, int mes)
	{
		Date d = (mes + 1 < LocalDate.now().getMonthValue()) ? (new Date(LocalDate.now().getYear() - 1900 + 1,mes,dia)) : ((mes + 1 == LocalDate.now().getMonthValue()) ?
				((dia < LocalDate.now().getDayOfMonth()) ? (new Date(LocalDate.now().getYear() - 1900 + 1,mes,dia)) : (new Date(LocalDate.now().getYear() - 1900,mes,dia))) 
						: new Date(LocalDate.now().getYear() - 1900,mes,dia));
		if(!days.contains(d))
		{
			days.add(d);
			return String.format("%s-%s-%s",dia,mes+1,d.getYear() + 1900);
		}
		return null;
	}
	
	public void resetear() {
		this.days = new ArrayList<Date>();
		db.cancelar();
	}
	
	public DataBaseManagement getDb() {
		return db;
	}
	
	public boolean validarActividad(String titulo, String precioStr) throws TypeConvertException
	{
		double precio;
		try {
			precio = Double.parseDouble(precioStr);
		}
		catch(Exception e)
		{
			throw new TypeConvertException("Formato requerido 0.00");
		}
		if(titulo.trim().length() > 0 && precio >= 0 && days.size() > 0)
		{
			ActividadFormativaDTO af = new ActividadFormativaDTO();
			af.title = titulo;
			af.price = precio;
			af.days = days;
			if(db.addActividadToDataBase(af))
			{
				days = new ArrayList<Date>();
				return true;
			}
			return false;
		}
		return false;
	}
	
	public void finalizar() {
		db.finalizar();
	}
	
	public void cancelar() {
		db.cancelar();
	}
}
