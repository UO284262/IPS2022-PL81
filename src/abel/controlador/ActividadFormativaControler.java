package abel.controlador;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import abel.modelo.ActividadFormativaDTO;
import abel.modelo.DataBaseManagement;
import exception.TypeConvertException;

public class ActividadFormativaControler {
	
	private List<Date> days = new ArrayList<Date>();
	private DataBaseManagement db = new DataBaseManagement();
	
	@SuppressWarnings("deprecation")
	public String añadirDia(int dia, int mes)
	{
		Date d = mes + 1 < LocalDate.now().getMonthValue() ? new Date(LocalDate.now().getYear() - 1900 + 1,mes + 1,dia) : new Date(LocalDate.now().getYear() - 1900,mes + 1,dia);
		if(!days.contains(d))
		{
			days.add(d);
			return String.format("%s-%s-%s",dia,mes+1,d.getYear() + 1900);
		}
		return null;
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
			days = new ArrayList<Date>();
			if(db.addActividadToDataBase(af))
			{
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
