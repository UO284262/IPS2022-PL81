package abel.controlador;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import abel.modelo.DataBaseManagement;
import abel.modelo.FormularioPericialDTO;

public class FormularioPericialControler {
	
	public int a�adirFormulario(String descripcion, String nombre_solicitante, String telefono_solicitante, String mail_solicitante, String prioridad)
	{
		Pattern pattern = Pattern
                .compile("^[a-z](\\.?[a-z0-9])*@[a-z]+\\.[a-z]+$");
 
        Matcher matcher = pattern.matcher(mail_solicitante);
		
		int telefono = 0;
		try
		{
			if(telefono_solicitante.length() != 9)
			{
				return -2;
			}
			telefono = Integer.valueOf(telefono_solicitante);
			if(!matcher.find())
			{
				return -2;
			}
			
		}
		catch(Exception e)
		{
			return - 2;
		}
		FormularioPericialDTO f = new FormularioPericialDTO();
		f.descripcion = descripcion;
		f.estado = "PENDIENTE";
		f.mail_solicitante = mail_solicitante;
		f.nombre_solicitante = nombre_solicitante;
		f.prioridad = prioridad;
		f.tipo_pericial = "PARTE";
		f.telefono_solicitante = telefono;
		f.numero = DataBaseManagement.getNextNumeroFormulario();
		if(!DataBaseManagement.addFormulario(f))
		{
			return -1;
		}
		return f.numero;
	}
}
