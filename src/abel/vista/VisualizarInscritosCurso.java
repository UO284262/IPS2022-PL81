package abel.vista;


import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import abel.controlador.VisualizarInscritosCursoControler;
import abel.modelo.DataBaseManagement;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class VisualizarInscritosCurso extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lbTitulo;
	private JComboBox<String> cbActividades;
	private JList<String> ltApuntados;
	private JScrollPane spApuntados;
	private DefaultComboBoxModel<String> modeloActividades = new DefaultComboBoxModel<String>();
	private DefaultListModel<String> modeloApuntados = new DefaultListModel<String>();
	private JLabel lbAvisoNadieApuntado;
	private VisualizarInscritosCursoControler controler;
	private JLabel lbAvisoActividades;

	/**
	 * Create the frame.
	 */
	public VisualizarInscritosCurso(VisualizarInscritosCursoControler controler) {
		this.controler = controler;
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		add(getLbTitulo());
		add(getComboBox());
		add(getSpActividades());
		add(getLbAvisoNadieApuntado());
		add(getLbAvisoActividades());
	}

	private void setModelActividades() {
		List<String> actividades = controler.getModeloActividades();
		if(actividades == null)
		{
			this.getLbAvisoActividades().setVisible(true);
		}
		else
		{
			this.modeloActividades.addAll(actividades);
		}
	}

	private JLabel getLbTitulo() {
		if (lbTitulo == null) {
			lbTitulo = new JLabel("Visualizar inscritos a una actividad formativa");
			lbTitulo.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lbTitulo.setHorizontalAlignment(SwingConstants.CENTER);
			lbTitulo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			lbTitulo.setBackground(Color.WHITE);
			lbTitulo.setBounds(10, 10, 580, 36);
		}
		return lbTitulo;
	}
	private JComboBox<String> getComboBox() {
		if (cbActividades == null) {
			cbActividades = new JComboBox<String>();
			cbActividades.setModel(modeloActividades);
			setModelActividades();
			cbActividades.setSelectedIndex(0);
			cbActividades.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getLbAvisoNadieApuntado().setVisible(false);
					cargarApuntadosA(getComboBox().getSelectedItem());
				}
			});
			cbActividades.setBounds(10, 70, 224, 21);
		}
		return cbActividades;
	}
	
	private void cargarApuntadosA(Object actividad)
	{
		String nombre = (String) actividad;
		List<String> apuntados = DataBaseManagement.getInscritosEn(nombre);
		if(apuntados == null)
		{
			this.getLbAvisoNadieApuntado().setVisible(true);
		}
		else
		{
			modeloApuntados.addAll(apuntados);
		}
	}
	
	private JScrollPane getSpActividades() {
		if (spApuntados == null) {
			spApuntados = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			spApuntados.setBounds(244, 70, 346, 183);
			spApuntados.setViewportView(getLtApuntados());
		}
		return spApuntados;
	}
	
	private JList<String> getLtApuntados() {
		if (ltApuntados == null) {
			ltApuntados = new JList<String>();
			ltApuntados.setFocusable(false);
			ltApuntados.setVisibleRowCount(4);
			ltApuntados.setModel(modeloApuntados);
			ltApuntados.setLayoutOrientation(JList.VERTICAL_WRAP);
		}
		return ltApuntados;
	}
	private JLabel getLbAvisoNadieApuntado() {
		if (lbAvisoNadieApuntado == null) {
			lbAvisoNadieApuntado = new JLabel("No hay nadie apuntado");
			lbAvisoNadieApuntado.setVisible(false);
			lbAvisoNadieApuntado.setHorizontalAlignment(SwingConstants.CENTER);
			lbAvisoNadieApuntado.setBounds(10, 240, 144, 13);
		}
		return lbAvisoNadieApuntado;
	}
	private JLabel getLbAvisoActividades() {
		if (lbAvisoActividades == null) {
			lbAvisoActividades = new JLabel("No hay actividades todav\u00EDa");
			lbAvisoActividades.setVisible(false);
			lbAvisoActividades.setBounds(10, 101, 144, 13);
		}
		return lbAvisoActividades;
	}
}
