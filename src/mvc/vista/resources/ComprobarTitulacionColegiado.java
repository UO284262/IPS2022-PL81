package mvc.vista.resources;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import mvc.vista.dialogs.ComprobadorTitulacion;
import mvc.controlador.ComprobarTitulacionColegiadoControler;
import mvc.modelo.ColegiadoSolicitadoDTO;

public class ComprobarTitulacionColegiado extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ComprobarTitulacionColegiadoControler controler;
	private DefaultTableModel modeloRecibidos = new DefaultTableModel();
	private JLabel lbTitulo;
	private JButton btActualizar;
	private JScrollPane spRecibidos;
	private JTable tableRecibidos;
	private JLabel lbRecibidos;
	private JButton btCerrar;
	private ComprobadorTitulacion padre;

	/**
	 * Create the panel.
	 */
	public ComprobarTitulacionColegiado(ComprobarTitulacionColegiadoControler controler, ComprobadorTitulacion padre) {
		this.controler = controler;
		this.padre = padre;
		setLayout(null);
		add(getLbTitulo());
		add(getBtActualizar());
		add(getSpRecibidos());
		add(getLbRecibidos());
		add(getBtCerrar());
		cargarRecibidos();
	}
	
	private boolean cargarRecibidos()
	{
		List<ColegiadoSolicitadoDTO> recibidos = controler.getListaRecibidos();
		modeloRecibidos = new DefaultTableModel();
		if(recibidos.size() == 0)
		{
			this.mostrarMensajeNadaRecibido();
			controler.cancelar();
			this.cerrar();
			this.getBtActualizar().setEnabled(false);
			return false;
		}
		else
		{
			cargarTablaRecibidos(recibidos);
			return true;
		}
	}
	
	private void cargarTablaRecibidos(List<ColegiadoSolicitadoDTO> colegiados)
	{
		Object[] columns = {"Dni","Nombre","Estado","Titulacion"};
		modeloRecibidos.setColumnIdentifiers(columns);
		for(ColegiadoSolicitadoDTO ci : colegiados)
		{
			String estado = ci.titulacion.contains("Licenciado en Informatica") || 
							ci.titulacion.contains("Ingeniero en Informatica") ||
							ci.titulacion.contains("Ingeniero en Informatica + Master")? "ACEPTADA" : "RECHAZADA";
			Object[] data = {ci.dni,ci.nombre, estado ,ci.titulacion};
			modeloRecibidos.addRow(data);
			controler.setEstado(ci.dni, estado);
		}
		this.getTableRecibidos().setModel(modeloRecibidos);
	}
	private JLabel getLbTitulo() {
		if (lbTitulo == null) {
			lbTitulo = new JLabel("Comprobar titulaci\u00F3n solicitudes");
			lbTitulo.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lbTitulo.setHorizontalAlignment(SwingConstants.CENTER);
			lbTitulo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			lbTitulo.setBackground(Color.WHITE);
			lbTitulo.setBounds(10, 10, 660, 36);
		}
		return lbTitulo;
	}
	private JButton getBtActualizar() {
		if (btActualizar == null) {
			btActualizar = new JButton("Actualizar");
			btActualizar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(mostrarMensajeYaActualizado()) {
						controler.finalizar();
						controler.eleminarFichero();
						getBtActualizar().setEnabled(false);
					}
				}
			});
			btActualizar.setBackground(Color.GREEN);
			btActualizar.setForeground(Color.BLACK);
			btActualizar.setBounds(585, 426, 85, 21);
		}
		return btActualizar;
	}
	private JScrollPane getSpRecibidos() {
		if (spRecibidos == null) {
			spRecibidos = new JScrollPane();
			spRecibidos.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			spRecibidos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			spRecibidos.setBounds(10, 114, 660, 302);
			spRecibidos.setViewportView(getTableRecibidos());
		}
		return spRecibidos;
	}
	private JTable getTableRecibidos() {
		if (tableRecibidos == null) {
			tableRecibidos = new JTable();
			tableRecibidos.setFocusable(false);
			tableRecibidos.setDefaultEditor(Object.class, null);
		}
		return tableRecibidos;
	}
	private boolean mostrarMensajeYaActualizado()
	{
		return JOptionPane.showConfirmDialog(this,new String("Al dar a OK se actualizará la información recibida en la base de datos."
				),"Aviso información actualizada",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION;
	}
	private void mostrarMensajeNadaRecibido()
	{
		JOptionPane.showConfirmDialog(this,new String("No se ha recibido ningún fichero de vuelta por parte del ministerio."),"Aviso de no recibido",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
	}
	private JLabel getLbRecibidos() {
		if (lbRecibidos == null) {
			lbRecibidos = new JLabel("Informacion recibida:");
			lbRecibidos.setBounds(10, 91, 197, 13);
		}
		return lbRecibidos;
	}
	private JButton getBtCerrar() {
		if (btCerrar == null) {
			btCerrar = new JButton("Cerrar");
			btCerrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controler.cancelar();
					cerrar();
				}
			});
			btCerrar.setBackground(Color.RED);
			btCerrar.setBounds(490, 426, 85, 21);
		}
		return btCerrar;
	}
	private void cerrar() {
		padre.dispose();
	}
}
