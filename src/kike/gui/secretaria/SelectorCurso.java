package kike.gui.secretaria;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import kike.modelo.curso.CursoDTO;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

public class SelectorCurso extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	private JPanel panel;
	private JLabel lblSelectCurso;
	private JComboBox<CursoDTO> comboBox;
	private JPanel panelFinalizar;
	private JButton btnCancelar;
	private JButton btnSiguiente;
	private DefaultComboBoxModel<CursoDTO> modeloCursos;

	private CursoDTO curso;
	private JLabel lblError;
	
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
	public SelectorCurso(DefaultComboBoxModel<CursoDTO> modeloCursos) {
		setModal(true);
		this.modeloCursos = modeloCursos;
		setTitle("Selector de cursos");
		setResizable(false);
		setBounds(100, 100, 500, 450);
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
			panel.setLayout(null);
			panel.add(getLblSelectCurso());
			panel.add(getComboBox());
			panel.add(getLblError());
		}
		return panel;
	}
	private JLabel getLblSelectCurso() {
		if (lblSelectCurso == null) {
			lblSelectCurso = new JLabel("Seleccione un curso:");
			lblSelectCurso.setFont(new Font("Arial", Font.PLAIN, 14));
			lblSelectCurso.setBounds(37, 95, 151, 39);
		}
		return lblSelectCurso;
	}
	private JComboBox<CursoDTO> getComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox<CursoDTO>();
			comboBox.setModel(modeloCursos);
			comboBox.setBounds(37, 145, 399, 39);
		}
		return comboBox;
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
		this.curso = modeloCursos.getElementAt(getComboBox().getSelectedIndex());
		
		if(curso == null) {
			getLblError().setText("Error, seleccione un curso");
		} else {
			getLblError().setText("");
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
			lblError.setBounds(37, 215, 399, 39);
		}
		return lblError;
	}
}
