package mvc.modelo.curso;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ActividadFormativaDTO 
{
	public String title; //private int sesiones;
	public double price; public int numeroPlazas;
	public List<Date> days = new ArrayList<Date>(); //private String instalaciones;
	public boolean is_open; public int numeroInscritos;
	public LocalDate fecha_orientativa; public LocalDate fin_inscripcion;
	//private int[] price; //private int estado;
}
