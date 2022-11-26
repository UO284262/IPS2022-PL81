package abel.vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
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
import kike.persistence.ColectivoDataBase;
import kike.persistence.CursoDataBase;
import kike.persistence.dto.ColectivoCursoDTO;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;

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
	private JSpinner spDescuento;
	private JComboBox<String> cbColectivo;
	private JButton btAñadirDescuento;
	private DefaultComboBoxModel<String> modeloColectivos;
	private List<ColectivoCursoDTO> colectivos;
	private JButton btCancelar;
	private JScrollPane spFechasAsignadas;
	private JScrollPane spColectivosAsignados;
	private DefaultListModel<String> modeloFechasAsignadas = new DefaultListModel<String>();
	private DefaultListModel<String> modeloColectivosAsignadas = new DefaultListModel<String>();
	private JList<String> ltFechasAsignadas;
	private JList<String> ltColectivosAsignados;
	private JScrollPane spProfesoresAsignados;
	private DefaultListModel<String> modeloProfesoresAsignados = new DefaultListModel<String>();
	private JList<String> ltProfesoresAsignados;
	private JCheckBox chCancelable;
	private JSpinner spinner;
	private JSeparator separator;
	private JSeparator separator_1;
	private JSeparator separator_2;
	private JSeparator separator_1_1;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private AñadirActividadFormativa af;

	/**
	 * Create the panel.
	 */
	public ConfigurarCurso(ConfigurarActividadControler controler, String nombre_curso, JDialog d,AñadirActividadFormativa af) {
		this.af = af;
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
		add(getSpDescuento());
		add(getCbColectivo());
		add(getBtAñadirDescuento());
		add(getBtCancelar());
		add(getSpFechasAsignadas());
		add(getSpColectivosAsignados());
		add(getSpProfesoresAsignados());
		add(getChCancelable());
		add(getSpinner());
		add(getSeparator());
		add(getSeparator_1());
		add(getSeparator_2());
		add(getSeparator_1_1());
		add(getLblNewLabel());
		add(getLblNewLabel_1());
		add(getLblNewLabel_2());
		add(getLblNewLabel_3());
		add(getLblNewLabel_4());
		cargarFechas();
		cargarProfesores();
		cargarColectivos();
	}
	private JLabel getLbTitulo() {
		if (lbTitulo == null) {
			lbTitulo = new JLabel("Configurar actividad formativa: " + nombre_curso);
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
			spFechas.setBounds(10, 67, 144, 132);
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
	private void cargarColectivos()
	{
		this.modeloColectivos = new DefaultComboBoxModel<String>();
		this.modeloColectivos.addAll(ColectivoDataBase.getColectivos());
		this.getCbColectivo().setModel(modeloColectivos);
		this.getCbColectivo().setSelectedIndex(0);
		this.colectivos = new ArrayList<>();
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
			lbHora.setBounds(178, 91, 80, 13);
		}
		return lbHora;
	}
	private JSpinner getSpHora() {
		if (spHora == null) {
			spHora = new JSpinner();
			spHora.setModel(new SpinnerNumberModel(0, 0, 24, 1));
			spHora.setBounds(254, 88, 62, 20);
		}
		return spHora;
	}
	private JSpinner getSpMinutos() {
		if (spMinutos == null) {
			spMinutos = new JSpinner();
			spMinutos.setModel(new SpinnerNumberModel(0, 0, 59, 5));
			spMinutos.setBounds(326, 88, 71, 20);
		}
		return spMinutos;
	}
	private JLabel getLbDuracion() {
		if (lbDuracion == null) {
			lbDuracion = new JLabel("Duracion: ");
			lbDuracion.setBounds(178, 128, 82, 13);
		}
		return lbDuracion;
	}
	private JSpinner getSpDuracion() {
		if (spDuracion == null) {
			spDuracion = new JSpinner();
			spDuracion.setModel(new SpinnerNumberModel(5, 5, null, 5));
			spDuracion.setBounds(326, 125, 71, 20);
		}
		return spDuracion;
	}
	private JButton getBtAñadir() {
		if (btAñadir == null) {
			btAñadir = new JButton("A\u00F1adir sesi\u00F3n");
			btAñadir.setForeground(Color.WHITE);
			btAñadir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int row = getTableFechas().getSelectedRow();
					if(row < 0) mostrarMensajeElegirFecha();
					else añadirSesion(row);
				}
			});
			btAñadir.setBackground(new Color(154, 205, 50));
			btAñadir.setBounds(178, 166, 219, 20);
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
			modeloFechasAsignadas.addElement(String.format("%s - %s:%s - %s(min)",(Date) date,this.getSpHora().getValue(), this.getSpMinutos().getValue(),(int) this.getSpDuracion().getValue()));
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
			spProfesores.setBounds(440, 89, 292, 125);
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
					modeloProfesoresAsignados.addElement(String.format("%s - %s",(String) profesor, tableProfesores.getValueAt(tableProfesores.getSelectedRow(),1)));
					if(finalizar()) getBtFinalizar().setEnabled(true);
				}
			});
		}
		return tableProfesores;
	}
	private boolean finalizar() {
		if(modeloProfesoresAsignados.size() > 0 && this.modeloColectivosAsignadas.size() > 0 && this.modeloFechas.getRowCount() == 0) {
			return true;
		}
		return false;
	}
	private JButton getBtFinalizar() {
		if (btFinalizar == null) {
			btFinalizar = new JButton("Finalizar configuraci\u00F3n");
			btFinalizar.setForeground(Color.WHITE);
			btFinalizar.setEnabled(false);
			btFinalizar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					af.configurado();
					cerrar();
					controler.finalizar();
				}
			});
			btFinalizar.setBackground(new Color(154, 205, 50));
			btFinalizar.setBounds(543, 383, 189, 21);
		}
		return btFinalizar;
	}
	private void cerrar()
	{
		CursoDataBase.inscribirColectivos(colectivos,controler.getConn());
		d.dispose();
	}
	private void cerrarSioSi() {
		d.dispose();
	}
	private JSpinner getSpDescuento() {
		if (spDescuento == null) {
			spDescuento = new JSpinner();
			spDescuento.setModel(new SpinnerNumberModel(new Double(0), new Double(0), null, new Double(1)));
			spDescuento.setBounds(178, 272, 219, 20);
		}
		return spDescuento;
	}
	private JComboBox<String> getCbColectivo() {
		if (cbColectivo == null) {
			cbColectivo = new JComboBox<String>();
			cbColectivo.setBounds(178, 241, 219, 21);
		}
		return cbColectivo;
	}
	private JButton getBtAñadirDescuento() {
		if (btAñadirDescuento == null) {
			btAñadirDescuento = new JButton("Asignar descuento por colectivo");
			btAñadirDescuento.setForeground(Color.WHITE);
			btAñadirDescuento.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					añadirDescuento();
				}
			});
			btAñadirDescuento.setBackground(new Color(154, 205, 50));
			btAñadirDescuento.setBounds(178, 302, 219, 21);
		}
		return btAñadirDescuento;
	}
	private void añadirDescuento()
	{
		if(this.getCbColectivo().getSelectedIndex() >= 0) {
			ColectivoCursoDTO c = new ColectivoCursoDTO();
			c.nombre_colectivo = (String) this.getCbColectivo().getSelectedItem();
			c.precio = (double) this.getSpDescuento().getValue();
			c.nombre_curso = nombre_curso;
			colectivos.add(c);
			this.modeloColectivos.removeElement(c.nombre_colectivo);
			this.modeloColectivosAsignadas.addElement(String.format("%s - %s€",c.nombre_colectivo,c.precio));
		}
		
	}
	private JButton getBtCancelar() {
		if (btCancelar == null) {
			btCancelar = new JButton("Cancelar");
			btCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controler.cancelar();
					cerrarSioSi();
				}
			});
			btCancelar.setBackground(Color.RED);
			btCancelar.setForeground(Color.BLACK);
			btCancelar.setBounds(448, 383, 85, 21);
		}
		return btCancelar;
	}
	private JScrollPane getSpFechasAsignadas() {
		if (spFechasAsignadas == null) {
			spFechasAsignadas = new JScrollPane();
			spFechasAsignadas.setBounds(10, 231, 144, 173);
			spFechasAsignadas.setViewportView(getLtFechasAsignadas());
		}
		return spFechasAsignadas;
	}
	private JScrollPane getSpColectivosAsignados() {
		if (spColectivosAsignados == null) {
			spColectivosAsignados = new JScrollPane();
			spColectivosAsignados.setBounds(178, 333, 219, 71);
			spColectivosAsignados.setViewportView(getLtColectivosAsignados());
		}
		return spColectivosAsignados;
	}
	private JList<String> getLtFechasAsignadas() {
		if (ltFechasAsignadas == null) {
			ltFechasAsignadas = new JList<String>();
			ltFechasAsignadas.setModel(modeloFechasAsignadas);
		}
		return ltFechasAsignadas;
	}
	private JList<String> getLtColectivosAsignados() {
		if (ltColectivosAsignados == null) {
			ltColectivosAsignados = new JList<String>();
			ltColectivosAsignados.setModel(modeloColectivosAsignadas);
		}
		return ltColectivosAsignados;
	}
	private JScrollPane getSpProfesoresAsignados() {
		if (spProfesoresAsignados == null) {
			spProfesoresAsignados = new JScrollPane();
			spProfesoresAsignados.setBounds(440, 243, 292, 90);
			spProfesoresAsignados.setViewportView(getLtProfesoresAsignados());
		}
		return spProfesoresAsignados;
	}
	private JList<String> getLtProfesoresAsignados() {
		if (ltProfesoresAsignados == null) {
			ltProfesoresAsignados = new JList<String>();
			ltProfesoresAsignados.setModel(modeloProfesoresAsignados);
		}
		return ltProfesoresAsignados;
	}
	private JCheckBox getChCancelable() {
		if (chCancelable == null) {
			chCancelable = new JCheckBox("Cancelable");
			chCancelable.setBounds(441, 352, 111, 21);
		}
		return chCancelable;
	}
	private JSpinner getSpinner() {
		if (spinner == null) {
			spinner = new JSpinner();
			spinner.setBounds(588, 353, 144, 20);
		}
		return spinner;
	}
	private JSeparator getSeparator() {
		if (separator == null) {
			separator = new JSeparator();
			separator.setOrientation(SwingConstants.VERTICAL);
			separator.setBounds(407, 67, 8, 337);
		}
		return separator;
	}
	private JSeparator getSeparator_1() {
		if (separator_1 == null) {
			separator_1 = new JSeparator();
			separator_1.setBounds(178, 196, 219, 13);
		}
		return separator_1;
	}
	private JSeparator getSeparator_2() {
		if (separator_2 == null) {
			separator_2 = new JSeparator();
			separator_2.setOrientation(SwingConstants.VERTICAL);
			separator_2.setBounds(164, 67, 8, 337);
		}
		return separator_2;
	}
	private JSeparator getSeparator_1_1() {
		if (separator_1_1 == null) {
			separator_1_1 = new JSeparator();
			separator_1_1.setBounds(440, 343, 292, 13);
		}
		return separator_1_1;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("A\u00F1adir precios a colectivos: ");
			lblNewLabel.setBounds(178, 208, 219, 13);
		}
		return lblNewLabel;
	}
	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("Sesiones: ");
			lblNewLabel_1.setBounds(10, 209, 144, 13);
		}
		return lblNewLabel_1;
	}
	private JLabel getLblNewLabel_2() {
		if (lblNewLabel_2 == null) {
			lblNewLabel_2 = new JLabel("A\u00F1adir sesiones: ");
			lblNewLabel_2.setBounds(178, 67, 219, 13);
		}
		return lblNewLabel_2;
	}
	private JLabel getLblNewLabel_3() {
		if (lblNewLabel_3 == null) {
			lblNewLabel_3 = new JLabel("A\u00F1adir profesorado: ");
			lblNewLabel_3.setBounds(440, 69, 292, 13);
		}
		return lblNewLabel_3;
	}
	private JLabel getLblNewLabel_4() {
		if (lblNewLabel_4 == null) {
			lblNewLabel_4 = new JLabel("Profesorado a\u00F1adido: ");
			lblNewLabel_4.setBounds(440, 224, 292, 13);
		}
		return lblNewLabel_4;
	}
}
