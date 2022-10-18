package abel.controlador;

import java.time.LocalDate;
import java.util.List;

import abel.modelo.DataBaseManagement;

public class VisualizarInscritosCursoControler {
	
	public List<String> getModeloActividades()
	{
		return DataBaseManagement.getActividadesFormativasFrom(LocalDate.now().getYear());
	}
	
	public List<String> getListaApuntados(String nombre)
	{
		return DataBaseManagement.getInscritosEn(nombre);
	}
}
