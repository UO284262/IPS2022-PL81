package abel.modelo;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ActividadFormativaDTO 
{
	public String title; //private int sesiones;
	public double price; //private int numeroPlazas;
	public List<Date> days = new ArrayList<Date>(); //private String instalaciones;
	public boolean is_open;
	public LocalDate fecha_orientativa;
	//private int[] price; //private int estado;
}
