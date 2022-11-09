package abel.vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import abel.controlador.ConfigurarActividadControler;
import abel.modelo.ProfesorDTO;

public class ConfigurarCurso extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ConfigurarActividadControler controler;
	private String nombre_curso;
	private JLabel lbTitulo;
	private JScrollPane spFechas;
	private JTable tableFechas;
	private DefaultTableModel modeloFechas;
	private DefaultTableModel modeloProfesores;
	private JLabel lbHora;
	private JSpinner spHora;
	private JSpinner spMinutos;
	private JLabel lbDuracion;
	private JSpinner spDuracion;
	private JButton btAñadir;
	private JScrollPane spProfesores;
	private JTable tableProfesores;
	private JButton btFinalizar;
	private JDialog d;

	/**
	 * Create the panel.
	 */
	public ConfigurarCurso(ConfigurarActividadControler controler, String nombre_curso, JDialog d) {
		this.controler = controler;
		this.nombre_curso = nombre_curso;
		this.d = d;
		setLayout(null);
		add(getLbTitulo());
		add(getSpFechas());
		add(getLbHora());
		add(getSpHora());
		add(getSpMinutos());
		add(getLbDuracion());
		add(getSpDuracion());
		add(getBtAñadir());
		add(getSpProfesores());
		add(getBtFinalizar());
		cargarFechas();
		cargarProfesores();
	}
	private JLabel getLbTitulo() {
		if (lbTitulo == null) {
			lbTitulo = new JLabel("Configurar actividad formativa");
			lbTitulo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			lbTitulo.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lbTitulo.setHorizontalAlignment(SwingConstants.CENTER);
			lbTitulo.setBounds(10, 10, 722, 47);
		}
		return lbTitulo;
	}
	private JScrollPane getSpFechas() {
		if (spFechas == null) {
			spFechas = new JScrollPane();
			spFechas.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			spFechas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			spFechas.setBounds(10, 89, 111, 263);
			spFechas.setViewportView(getTableFechas());
		}
		return spFechas;
	}
	private JTable getTableFechas() {
		if (tableFechas == null) {
			tableFechas = new JTable();
			tableFechas.setFocusable(false);
			tableFechas.setDefaultEditor(Object.class, null);
		}
		return tableFechas;
	}
	private void cargarFechas()
	{
		modeloFechas = new DefaultTableModel();
		List<Date> dates = controler.getDatesFor(nombre_curso);
		Object[] data = {"Fecha"};
		modeloFechas.setColumnIdentifiers(data);
		for(Date date : dates)
		{
			Object[] fecha = {date};
			modeloFechas.addRow(fecha);
		}
		this.getTableFechas().setModel(modeloFechas);
	}
	private void cargarProfesores()
	{
		modeloProfesores = new DefaultTableModel();
		List<ProfesorDTO> profesores = controler.getAllProfesores();
		Object[] data = {"Nombre","Especialidad"};
		modeloProfesores.setColumnIdentifiers(data);
		for(ProfesorDTO p : profesores)
		{
			Object[] profesor = {p.profesor,p.especialidad};
			modeloProfesores.addRow(profesor);
		}
		this.getTableProfesores().setModel(modeloProfesores);
	}
	private JLabel getLbHora() {
		if (lbHora == null) {
			lbHora = new JLabel("Hora inicio:");
			lbHora.setBounds(133, 91, 80, 13);
		}
		return lbHora;
	}
	private JSpinner getSpHora() {
		if (spHora == null) {
			spHora = new JSpinner();
			spHora.setModel(new SpinnerNumberModel(0, 0, 24, 1));
			spHora.setBounds(204, 88, 62, 20);
		}
		return spHora;
	}
	private JSpinner getSpMinutos() {
		if (spMinutos == null) {
			spMinutos = new JSpinner();
			spMinutos.setModel(new SpinnerNumberModel(0, 0, 60, 5));
			spMinutos.setBounds(276, 88, 71, 20);
		}
		return spMinutos;
	}
	private JLabel getLbDuracion() {
		if (lbDuracion == null) {
			lbDuracion = new JLabel("Duracion: ");
			lbDuracion.setBounds(131, 128, 82, 13);
		}
		return lbDuracion;
	}
	private JSpinner getSpDuracion() {
		if (spDuracion == null) {
			spDuracion = new JSpinner();
			spDuracion.setModel(new SpinnerNumberModel(5, 5, null, 5));
			spDuracion.setBounds(276, 125, 71, 20);
		}
		return spDuracion;
	}
	private JButton getBtAñadir() {
		if (btAñadir == null) {
			btAñadir = new JButton("A\u00F1adir sesi\u00F3n");
			btAñadir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int row = getTableFechas().getSelectedRow();
					if(row < 0) mostrarMensajeElegirFecha();
					else añadirSesion(row);
				}
			});
			btAñadir.setBackground(Color.GREEN);
			btAñadir.setBounds(128, 167, 219, 20);
		}
		return btAñadir;
	}
	
	private void añadirSesion(int row)
	{
		Object date = this.getTableFechas().getValueAt(row, 0);
		if(!controler.añadirSesion(nombre_curso,(Date) date,String.format("%s:%s",this.getSpHora().getValue(), this.getSpMinutos().getValue()),(int) this.getSpDuracion().getValue()))
				mostrarMensajeSolapamiento();
		else
		{
			modeloFechas.removeRow(row);
		}
	}
	
	private void mostrarMensajeElegirFecha()
	{
		JOptionPane.showConfirmDialog(this,new String("No hay ninguna fecha seleccionada de la tabla."),"Aviso de no fecha",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
	}
	
	private void mostrarMensajeSolapamiento()
	{
		JOptionPane.showConfirmDialog(this,new String("Esta actividad se esta solapando con otra. Cambie la hora y duración."),"Aviso solapamiento",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
	}
	private void mostrarMensajeFaltanDatos()
	{
		JOptionPane.showConfirmDialog(this,new String("Termine de asignar todas las sesiones."),"Aviso faltan datos",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
	}
	private JScrollPane getSpProfesores() {
		if (spProfesores == null) {
			spProfesores = new JScrollPane();
			spProfesores.setBounds(357, 89, 375, 224);
			spProfesores.setViewportView(getTableProfesores());
		}
		return spProfesores;
	}
	private JTable getTableProfesores() {
		if (tableProfesores == null) {
			tableProfesores = new JTable();
			tableProfesores.setFocusable(false);
			tableProfesores.setDefaultEditor(Object.class, null);
			tableProfesores.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Object profesor = tableProfesores.getValueAt(tableProfesores.getSelectedRow(),0);
					controler.asignarProfesor(nombre_curso,(String) profesor);
					getBtFinalizar().setEnabled(true);
				}
			});
		}
		return tableProfesores;
	}
	private JButton getBtFinalizar() {
		if (btFinalizar == null) {
			btFinalizar = new JButton("Finalizar configuraci\u00F3n");
			btFinalizar.setEnabled(false);
			btFinalizar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cerrar();
				}
			});
			btFinalizar.setBackground(Color.GREEN);
			btFinalizar.setBounds(543, 331, 189, 21);
		}
		return btFinalizar;
	}
	private void cerrar()
	{
		if(modeloFechas.getRowCount() == 0)
			d.dispose();
		else
			mostrarMensajeFaltanDatos();
	}
}
