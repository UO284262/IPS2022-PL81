package abel.controlador;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import abel.modelo.ActividadFormativaDTO;
import abel.modelo.DataBaseManagement;

public class ActividadFormativaControler {
	
	private List<Date> days = new ArrayList<Date>();
	
	public boolean añadirDia(int dia, int mes)
	{
		@SuppressWarnings("deprecation")
		Date d = new Date(Calendar.YEAR,mes,dia);
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
			if(DataBaseManagement.addActividadToDataBase(new ActividadFormativaDTO(titulo,precio,days)))
			{
				return true;
			}
			return false;
		}
		return false;
	}
}
