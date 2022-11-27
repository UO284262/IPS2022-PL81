package abel.vista;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import abel.controlador.VisualizarInscritosCursoControler;
import abel.modelo.ActividadFormativaDTO;
import abel.modelo.ApuntadoDTO;

public class VisualizarInscritosCurso extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lbTitulo;
	private JScrollPane spApuntados;
	private VisualizarInscritosCursoControler controler;
	private JLabel lbTotalInscripcion;
	private JTextField tfIngresos;
	private JTable table;
	private DefaultTableModel modeloActividades = new DefaultTableModel();
	private JScrollPane scrollPane;
	private JLabel lbActividadFormativa;
	private JTextField tfActividadFormativa;
	private JTable table_1;
	private DefaultTableModel modeloApuntados = new DefaultTableModel();

	/**
	 * Create the frame.
	 */
	public VisualizarInscritosCurso(VisualizarInscritosCursoControler controler) {
		this.controler = controler;
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		add(getLbTitulo());
		add(getSpActividades());
		add(getLbTotalInscripcion());
		add(getTfIngresos());
		add(getScrollPane());
		add(getLbActividadFormativa());
		add(getTfActividadFormativa());
		setModelActividades();
	}

	private void setModelActividades() {
		List<ActividadFormativaDTO> actividades = controler.getModeloActividades();
		if(actividades == null || actividades.size() == 0)
		{
			mostrarMensajeNoActividades();
		}
		else
		{
			cargarTablaActividades(actividades);
			this.getTable().setModel(modeloActividades);
		}
	}
	
	private void cargarTablaActividades(List<ActividadFormativaDTO> actividades)
	{
		Object[] columns = {"Nombre","Precio (€)","Fecha orientativa","Está abierto"};
		modeloActividades.setColumnIdentifiers(columns);
		for(ActividadFormativaDTO af : actividades)
		{
			Object[] data = {af.title, af.price, af.fecha_orientativa.toString() , af.is_open ? "SI" : "NO"};
			modeloActividades.addRow(data);
		}
	}

	private JLabel getLbTitulo() {
		if (lbTitulo == null) {
			lbTitulo = new JLabel("Visualizar inscritos a una actividad formativa");
			lbTitulo.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lbTitulo.setHorizontalAlignment(SwingConstants.CENTER);
			lbTitulo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			lbTitulo.setBackground(Color.WHITE);
			lbTitulo.setBounds(10, 10, 969, 36);
		}
		return lbTitulo;
	}
	
	private void cargarIngresosPara(Object actividad)
	{
		String nombre = (String) actividad;
		getTfIngresos().setText(controler.getIngresosFor(nombre) + "");
	}
	
	private boolean cargarApuntadosA(Object actividad)
	{
		String nombre = (String) actividad;
		
		List<ApuntadoDTO> apuntados = controler.getListaApuntados(nombre);
		if(apuntados == null)
		{
			mostrarMensajeNoActividad();
			return false;
		}
		else if(apuntados.size() == 0)
		{
			mostrarMensajeNadieApuntado();
			return false;
		}
		else
		{
			modeloApuntados = new DefaultTableModel();
			cargarTablaApuntados(apuntados);
			cargarIngresosPara(actividad);
			this.getTable_1().setModel(modeloApuntados);
			return true;
		}
	}
	
	private void cargarTablaApuntados(List<ApuntadoDTO> colegiados)
	{
		Object[] columns = {"Nombre","Apellidos","Fecha inscripción","Estado", "Abonado (€)","Colectivo"};
		modeloApuntados.setColumnIdentifiers(columns);
		for(ApuntadoDTO ci : colegiados)
		{
			Object[] data = {ci.nombre, ci.apellidos, ci.fecha_inscripcion.toString() , ci.estado , ci.cantidad_abonada, ci.colectivo};
			modeloApuntados.addRow(data);
		}
	}
	
	private void mostrarMensajeNadieApuntado()
	{
		JOptionPane.showConfirmDialog(this,new String("No hay nadie inscrito a esta actividad formativa."),"Aviso de no inscritos",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
	}
	
	private void mostrarMensajeNoActividad()
	{
		JOptionPane.showConfirmDialog(this,new String("No existe esa actividad formativa."),"Aviso actividad formativa",JOptionPane.OK_CANCEL_OPTION,JOptionPane.ERROR_MESSAGE);
	}
	
	private void mostrarMensajeNoActividades()
	{
		JOptionPane.showConfirmDialog(this,new String("No hay ninguna actividad formativa planificada."),"Aviso de falta de actividades",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
	}
	
	private JScrollPane getSpActividades() {
		if (spApuntados == null) {
			spApuntados = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			spApuntados.setBounds(479, 93, 500, 302);
			spApuntados.setViewportView(getTable_1());
		}
		return spApuntados;
	}
	private JLabel getLbTotalInscripcion() {
		if (lbTotalInscripcion == null) {
			lbTotalInscripcion = new JLabel("Total de ingresos (\u20AC):");
			lbTotalInscripcion.setBounds(479, 59, 149, 13);
		}
		return lbTotalInscripcion;
	}
	private JTextField getTfIngresos() {
		if (tfIngresos == null) {
			tfIngresos = new JTextField();
			tfIngresos.setEditable(false);
			tfIngresos.setBounds(610, 56, 104, 19);
			tfIngresos.setColumns(10);
		}
		return tfIngresos;
	}
	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Object acf = table.getValueAt(table.getSelectedRow(),0);
					boolean bool = cargarApuntadosA(table.getValueAt(table.getSelectedRow(),0));
					if(bool) getTfActividadFormativa().setText((String) acf);
				}
			});
			table.setFocusable(false);
			table.setDefaultEditor(Object.class, null);
		}
		return table;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPane.setBounds(10, 93, 433, 302);
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	private JLabel getLbActividadFormativa() {
		if (lbActividadFormativa == null) {
			lbActividadFormativa = new JLabel("Actividad formativa:");
			lbActividadFormativa.setBounds(10, 59, 125, 13);
		}
		return lbActividadFormativa;
	}
	private JTextField getTfActividadFormativa() {
		if (tfActividadFormativa == null) {
			tfActividadFormativa = new JTextField();
			tfActividadFormativa.setEditable(false);
			tfActividadFormativa.setBounds(143, 56, 96, 19);
			tfActividadFormativa.setColumns(10);
		}
		return tfActividadFormativa;
	}
	private JTable getTable_1() {
		if (table_1 == null) {
			table_1 = new JTable();
			table_1.setFocusable(false);
			table_1.setDefaultEditor(Object.class, null);
		}
		return table_1;
	}
}
