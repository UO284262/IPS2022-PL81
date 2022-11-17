package kike.gui.secretaria;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import kike.modelo.curso.CursoDTO;
import kike.modelo.curso.CursoManager;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class SelectorCurso extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	private JPanel panel;
	private JLabel lblSelectCurso;
	private JPanel panelFinalizar;
	private JButton btnCancelar;
	private JButton btnSiguiente;
	private List<CursoDTO> cursos;
	private TableModel tm;

	private CursoDTO curso;
	private JLabel lblError;
	private JScrollPane scrollPane;
	private JTable table;
	
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		try {
//			DefaultComboBoxModel<CursoManager> modeloCursos = new DefaultComboBoxModel<>();
//			modeloCursos.addElement(new CursoManager("curso1", 1000));
//			modeloCursos.addElement(new CursoManager("curso2", 2000));
//			SelectorCurso dialog = new SelectorCurso(modeloCursos);
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public SelectorCurso(List<CursoDTO> cursos) {
		setModal(true);
		this.cursos = CursoManager.removePastCursos(cursos);
		setTitle("Selector de cursos");
		setResizable(false);
		setBounds(100, 100, 750, 450);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panelSelectorCurso = new JPanel();
			contentPanel.add(panelSelectorCurso);
			panelSelectorCurso.setLayout(new BorderLayout(0, 0));
			panelSelectorCurso.add(getPanel(), BorderLayout.CENTER);
			panelSelectorCurso.add(getPanelFinalizar(), BorderLayout.SOUTH);
		}
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getLblSelectCurso(), BorderLayout.NORTH);
			panel.add(getLblError(), BorderLayout.SOUTH);
			panel.add(getScrollPane(), BorderLayout.CENTER);
		}
		return panel;
	}
	private JLabel getLblSelectCurso() {
		if (lblSelectCurso == null) {
			lblSelectCurso = new JLabel("Seleccione un curso:");
			lblSelectCurso.setFont(new Font("Arial", Font.PLAIN, 14));
		}
		return lblSelectCurso;
	}
	private JPanel getPanelFinalizar() {
		if (panelFinalizar == null) {
			panelFinalizar = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panelFinalizar.getLayout();
			flowLayout.setAlignment(FlowLayout.TRAILING);
			panelFinalizar.add(getBtnCancelar());
			panelFinalizar.add(getBtnSiguiente());
		}
		return panelFinalizar;
	}
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnCancelar.setForeground(Color.WHITE);
			btnCancelar.setBackground(Color.RED);
		}
		return btnCancelar;
	}
	private JButton getBtnSiguiente() {
		if (btnSiguiente == null) {
			btnSiguiente = new JButton("Siguiente");
			btnSiguiente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					accionSiguiente();
				}

				
			});
			btnSiguiente.setForeground(Color.WHITE);
			btnSiguiente.setBackground(Color.GREEN);
		}
		return btnSiguiente;
	}
	
	private void accionSiguiente() {
		int index = getTable().getSelectedRow();		
		if(index < 0) {
			getLblError().setText("Error, seleccione un curso");
		} else {
			getLblError().setText("");
			this.curso = cursos.get(index);
			AperturaCursos ac = new AperturaCursos(this);
			ac.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			ac.setModal(true);
			ac.setLocationRelativeTo(this);
			ac.setVisible(true);
		}		
	}
	
	public CursoDTO getCurso() {
		return curso;
	}
	private JLabel getLblError() {
		if (lblError == null) {
			lblError = new JLabel("");
			lblError.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblError.setForeground(Color.RED);
		}
		return lblError;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	private JTable getTable() {
		if (table == null) {
			tm=new DefaultTableModel(new String[]{"Title", "Price", "Days"}, cursos.size()) ;
			//carga cada uno de los valores de pojos usando PropertyUtils (de apache coommons beanutils)
			for (int i=0; i<cursos.size(); i++) {
				CursoDTO pojo=cursos.get(i);
				tm.setValueAt(pojo.title, i, 0);
				tm.setValueAt(pojo.price, i, 1);
				tm.setValueAt(pojo.days, i, 2);
			}
			table = new JTable();
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setFocusable(false);
			table.setDefaultEditor(Object.class, null);
			table.setModel(tm);
		}
		return table;
	}
}
