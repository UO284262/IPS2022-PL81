package abel.controlador;

import abel.modelo.DataBaseManagement;
import abel.modelo.FormularioPericialDTO;

public class FormularioPericialControler {
	
	public int añadirFormulario(String descripcion, String nombre_solicitante, int telefono_solitante, String mail_solicitante, String prioridad)
	{
		FormularioPericialDTO f = new FormularioPericialDTO();
		f.descripcion = descripcion;
		f.estado = "PENDIENTE";
		f.mail_solicitante = mail_solicitante;
		f.nombre_solicitante = nombre_solicitante;
		f.prioridad = prioridad;
		f.tipo_pericial = "PARTE";
		f.numero = DataBaseManagement.getNextNumeroFormulario();
		if(!DataBaseManagement.addFormulario(f))
		{
			return -1;
		}
		return f.numero;
	}
}
