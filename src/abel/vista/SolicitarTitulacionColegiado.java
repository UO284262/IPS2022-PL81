package abel.vista;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import abel.controlador.SolicitarTitulacionControler;
import abel.modelo.ColegiadoInscritoDTO;

public class SolicitarTitulacionColegiado extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SolicitarTitulacionControler controler;
	private DefaultTableModel modeloPendientes;

	/**
	 * Create the panel.
	 */
	public SolicitarTitulacionColegiado(SolicitarTitulacionControler controler) {
		this.controler = controler;
		cargarPendientes();
	}
	
	private boolean cargarPendientes()
	{
		
		List<ColegiadoInscritoDTO> apuntados = controler.getListaApuntados();
		if(apuntados == null)
		{
			return false;
		}
		else if(apuntados.size() == 0)
		{
			return false;
		}
		else
		{
			modeloPendientes = new DefaultTableModel();
			cargarTablaApuntados(apuntados);
			//this.getTable_1().setModel(modeloPendientes);
			return true;
		}
	}
	
	private void cargarTablaApuntados(List<ColegiadoInscritoDTO> colegiados)
	{
		Object[] columns = {"Nombre","Apellidos","Fecha inscripción","Estado", "Abonado (€)"};
		modeloPendientes.setColumnIdentifiers(columns);
		for(ColegiadoInscritoDTO ci : colegiados)
		{
			Object[] data = {ci.nombre, ci.apellidos, ci.fecha_inscripcion.toString() , ci.estado , ci.cantidad_abonada};
			modeloPendientes.addRow(data);
		}
	}

}
