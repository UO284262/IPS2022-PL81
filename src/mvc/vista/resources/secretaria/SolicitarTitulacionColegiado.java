package mvc.vista.resources.secretaria;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import mvc.controlador.SolicitarTitulacionControler;
import mvc.modelo.colegiado.ColegiadoInscritoDTO;
import mvc.vista.dialogs.secretaria.JustificanteLote;
import util.FileUtil;

public class SolicitarTitulacionColegiado extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SolicitarTitulacionControler controler;
	private DefaultTableModel modeloPendientes;
	private DefaultTableModel modeloSeleccionados;
	private JLabel lbTitulo;
	private JButton btSolicitar;
	private JScrollPane spPendientes;
	private JTable tablePendientes;
	private JScrollPane spSeleccionados;
	private JTable tableSeleccionados;
	private JLabel lbPendientes;
	private JLabel lbSeleccionados;

	/**
	 * Create the panel.
	 */
	public SolicitarTitulacionColegiado(SolicitarTitulacionControler controler) {
		this.controler = controler;
		setLayout(null);
		add(getLbTitulo());
		add(getBtSolicitar());
		add(getSpPendientes());
		add(getSpSeleccionados());
		add(getLbPendientes());
		add(getLbSeleccionados());
		cargarPendientes();
		cargarTablaSeleccionados();
	}
	
	private boolean cargarPendientes()
	{
		List<ColegiadoInscritoDTO> apuntados = controler.getListaPendientes();
		modeloPendientes = new DefaultTableModel();
		if(apuntados == null)
		{
			this.getTablePendientes().repaint();
			return false;
		}
		else if(apuntados.size() == 0)
		{
			cargarTablaPendientes(apuntados);
			this.getTablePendientes().setModel(modeloPendientes);
			this.getTablePendientes().repaint();
			return false;
		}
		else
		{
			cargarTablaPendientes(apuntados);
			this.getTablePendientes().setModel(modeloPendientes);
			this.getTablePendientes().repaint();
			return true;
		}
	}
	
	private void cargarTablaPendientes(List<ColegiadoInscritoDTO> colegiados)
	{
		Object[] columns = {"Dni","Nombre","Apellidos","Telefono"};
		modeloPendientes.setColumnIdentifiers(columns);
		for(ColegiadoInscritoDTO ci : colegiados)
		{
			Object[] data = {ci.dni,ci.nombre, ci.apellidos, ci.telefono};
			modeloPendientes.addRow(data);
		}
	}
	private void cargarTablaSeleccionados()
	{
		modeloSeleccionados = new DefaultTableModel();
		Object[] columns = {"Dni","Nombre","Apellidos","Telefono"};
		modeloSeleccionados.setColumnIdentifiers(columns);
		this.getTableSeleccionados().setModel(modeloSeleccionados);
		this.getTableSeleccionados().repaint();
	}
	private boolean añadirSeleccionado(String dni)
	{
		for(int i = 0; i < modeloSeleccionados.getRowCount(); i++)
		{
			if(((String) modeloSeleccionados.getValueAt(i, 0)).equals(dni))
			{
				return false;
			}
		}
		ColegiadoInscritoDTO ci = controler.getPendiente(dni);
		Object[] data = {ci.dni,ci.nombre, ci.apellidos, ci.telefono};
		modeloSeleccionados.addRow(data);
		return true;
	}
	private boolean eliminarSeleccionado(String dni)
	{
		for(int i = 0; i < modeloSeleccionados.getRowCount(); i++)
		{
			if(((String) modeloSeleccionados.getValueAt(i, 0)).equals(dni))
			{
				modeloSeleccionados.removeRow(i);
				return true;
			}
		}
		return false;
	}
	public boolean cargarAFichero()
	{
		List<String> colegiados = getColegiados();
		if(colegiados.size() == 0)
		{
			return false;
		}
		String toFichero = getJustificante();
		FileUtil.appendToFile("consultaTitulacion.csv", toFichero);
		controler.setValidando(colegiados);
		return true;
	}
	public String getJustificante()
	{
		String toFichero = "";
		List<String> colegiados = getColegiados();
		if(colegiados.size() > 0)
		{
			for(String[] c : controler.getPendientes(colegiados))
			{
				toFichero += String.format("%s,%s\n", c[0],c[1]);
			}
		}
		return toFichero;
	}
	private List<String> getColegiados() {
		List<String> colegiados = new ArrayList<String>();
		for(int i = 0; i < modeloSeleccionados.getRowCount(); i++)
		{
			colegiados.add((String) modeloSeleccionados.getValueAt(i, 0));
		}
		return colegiados;
	}
	private JLabel getLbTitulo() {
		if (lbTitulo == null) {
			lbTitulo = new JLabel("Consultar titulación solicitudes");
			lbTitulo.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lbTitulo.setHorizontalAlignment(SwingConstants.CENTER);
			lbTitulo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			lbTitulo.setBackground(Color.WHITE);
			lbTitulo.setBounds(10, 10, 932, 36);
		}
		return lbTitulo;
	}
	private JButton getBtSolicitar() {
		if (btSolicitar == null) {
			btSolicitar = new JButton("Solicitar");
			btSolicitar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(getColegiados().size() > 0)
					{
						mostrarJustificante();
					}
				}
			});
			btSolicitar.setBackground(Color.GREEN);
			btSolicitar.setForeground(Color.BLACK);
			btSolicitar.setBounds(857, 426, 85, 21);
		}
		return btSolicitar;
	}
	private void mostrarJustificante()
	{
		JustificanteLote lote = new JustificanteLote(this);
		lote.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		lote.setLocationRelativeTo(this);
		lote.setModal(true);
		lote.setVisible(true);
	}
	public void resetear()
	{
		cargarPendientes();
		cargarTablaSeleccionados();
	}
	private JScrollPane getSpPendientes() {
		if (spPendientes == null) {
			spPendientes = new JScrollPane();
			spPendientes.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			spPendientes.setBounds(10, 114, 456, 302);
			spPendientes.setViewportView(getTablePendientes());
		}
		return spPendientes;
	}
	private JTable getTablePendientes() {
		if (tablePendientes == null) {
			tablePendientes = new JTable();
			tablePendientes.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Object dni = tablePendientes.getValueAt(tablePendientes.getSelectedRow(),0);
					if(!añadirSeleccionado((String) dni))
					{
						mostrarMensajeYaAñadido();
					}
				}
			});
			tablePendientes.setFocusable(false);
			tablePendientes.setDefaultEditor(Object.class, null);
		}
		return tablePendientes;
	}
	
	private void mostrarMensajeYaAñadido()
	{
		JOptionPane.showConfirmDialog(this,new String("Ya se ha añadido esa solicitud."),"Aviso de ya añadido",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
	}
	private JScrollPane getSpSeleccionados() {
		if (spSeleccionados == null) {
			spSeleccionados = new JScrollPane();
			spSeleccionados.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			spSeleccionados.setBounds(476, 114, 466, 302);
			spSeleccionados.setViewportView(getTableSeleccionados());
		}
		return spSeleccionados;
	}
	private JTable getTableSeleccionados() {
		if (tableSeleccionados == null) {
			tableSeleccionados = new JTable();
			tableSeleccionados.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Object dni = tableSeleccionados.getValueAt(tableSeleccionados.getSelectedRow(),0);
					eliminarSeleccionado((String) dni);
				}
			});
			tableSeleccionados.setFocusable(false);
			tableSeleccionados.setDefaultEditor(Object.class, null);
		}
		return tableSeleccionados;
	}
	private JLabel getLbPendientes() {
		if (lbPendientes == null) {
			lbPendientes = new JLabel("Solicitudes pendientes");
			lbPendientes.setBounds(10, 91, 197, 13);
		}
		return lbPendientes;
	}
	private JLabel getLbSeleccionados() {
		if (lbSeleccionados == null) {
			lbSeleccionados = new JLabel("Solicitudes seleccionadas para consulta");
			lbSeleccionados.setBounds(476, 91, 297, 13);
		}
		return lbSeleccionados;
	}
}
