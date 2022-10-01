package abel.modelo;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class ActividadFormativaDTO 
{
	private String title; //private int sesiones;
	private double price; //private int numeroPlazas;
	private List<Date> days = new ArrayList<Date>(); //private String instalaciones;
	//private int[] price; //private int estado;
	
	public ActividadFormativaDTO(String title, double price, List<Date> days)
	{
		setTitle(title); setPrice(price); setDays(days);
	}
	
	private void setTitle(String title)
	{
		this.title = title;
	}
	
	private void setPrice(double price)
	{
		this.price = price;
	}
	
	private void setDays(List<Date> days)
	{
		for(Date d : days)
		{
			days.add(d);
		}
	}
	
	public double getPrice()
	{
		return price;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public List<Date> getDays()
	{
		return days;
	}
}
