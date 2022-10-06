package abel.controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import abel.modelo.ActividadFormativaDTO;
import abel.modelo.DataBaseManagement;

public class ActividadFormativaControler {
	
	private List<Date> days = new ArrayList<Date>();
	
	public boolean añadirDia(int dia, int mes)
	{
		@SuppressWarnings("deprecation")
		Date d = new Date(new Date(System.currentTimeMillis()).getYear(),mes,dia);
		if(!days.contains(d))
		{
			days.add(d);
			return true;
		}
		return false;
	}
	
	public boolean validarActividad(String titulo, String precioStr)
	{
		int precio = Integer.parseInt(precioStr);
		if(titulo.trim().length() > 0 && precio >= 0)
		{
			ActividadFormativaDTO af = new ActividadFormativaDTO();
			af.title = titulo;
			af.price = precio;
			af.days = days;
			if(DataBaseManagement.addActividadToDataBase(af))
			{
				return true;
			}
			return false;
		}
		return false;
	}
}
