package kike.gui.colegiado;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import kike.modelo.colegiado.ColegiadoManager;
import kike.modelo.curso.CursoDTOForColegiados;
import kike.modelo.curso.CursoManager;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.JList;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class PreInscribeColegiado extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private JPanel panelCentral;
	private JPanel panelFinalizar;
	private JButton btnCancelar;
	private JButton btnInscribeme;
	private JScrollPane scrollPane;
	private JList<CursoDTOForColegiados> list;
	private ListModel<CursoDTOForColegiados> modeloCursos;
	private JPanel panelSuperior;
	private JLabel lblTitulo;
	private JPanel panelSelectorId;
	private JLabel lblID;
	private JTextField textField;
	private JLabel lblError;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			PreInscribeColegiado dialog = new PreInscribeColegiado();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public PreInscribeColegiado() {
		modeloCursos = CursoManager.getModeloCursosAbiertos();
		setMinimumSize(new Dimension(500, 400));
		setBounds(100, 100, 500, 450);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getPanelCentral(), BorderLayout.CENTER);
	}

	private JPanel getPanelCentral() {
		if (panelCentral == null) {
			panelCentral = new JPanel();
			panelCentral.setLayout(new BorderLayout(0, 0));
			panelCentral.add(getPanelFinalizar(), BorderLayout.SOUTH);
			panelCentral.add(getScrollPane(), BorderLayout.CENTER);
			panelCentral.add(getPanelSuperior(), BorderLayout.NORTH);
		}
		return panelCentral;
	}
	private JPanel getPanelFinalizar() {
		if (panelFinalizar == null) {
			panelFinalizar = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panelFinalizar.getLayout();
			flowLayout.setHgap(15);
			flowLayout.setAlignment(FlowLayout.RIGHT);
			panelFinalizar.add(getLblError());
			panelFinalizar.add(getBtnCancelar());
			panelFinalizar.add(getBtnInscribeme());
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
	private JButton getBtnInscribeme() {
		if (btnInscribeme == null) {
			btnInscribeme = new JButton("Inscribeme");
			btnInscribeme.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CursoDTOForColegiados cc = modeloCursos.getElementAt(getList().getSelectedIndex());
					ColegiadoManager colm = new ColegiadoManager(getTextField().getText());
					if(!colm.validaID()) { //getTextField().getText().isBlank() || 
						getLblError().setText("Error: id invalido.");
					} else {
						getLblError().setText("");
						CursoManager cm = new CursoManager(cc.cdto);
						cm.inscribirse();
						mostrarPreincripcion();
					}
				}
			});
			btnInscribeme.setForeground(Color.WHITE);
			btnInscribeme.setBackground(Color.GREEN);
		}
		return btnInscribeme;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getList());
		}
		return scrollPane;
	}
	private JList<CursoDTOForColegiados> getList() {
		if (list == null) {
			list = new JList<CursoDTOForColegiados>();
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.setModel(modeloCursos);
			list.setSelectedIndex(0);
		}
		return list;
	}
	private JPanel getPanelSuperior() {
		if (panelSuperior == null) {
			panelSuperior = new JPanel();
			panelSuperior.setLayout(new GridLayout(0, 1, 0, 0));
			panelSuperior.add(getLblTitulo_1());
			panelSuperior.add(getPanelSelectorId());
		}
		return panelSuperior;
	}
	private JLabel getLblTitulo_1() {
		if (lblTitulo == null) {
			lblTitulo = new JLabel("Inscripci\u00F3n: Seleccione un curso");
			lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
			lblTitulo.setFont(new Font("Arial Black", Font.PLAIN, 21));
		}
		return lblTitulo;
	}
	private JPanel getPanelSelectorId() {
		if (panelSelectorId == null) {
			panelSelectorId = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panelSelectorId.getLayout();
			flowLayout.setVgap(10);
			flowLayout.setAlignment(FlowLayout.LEFT);
			panelSelectorId.add(getLblID());
			panelSelectorId.add(getTextField());
		}
		return panelSelectorId;
	}
	private JLabel getLblID() {
		if (lblID == null) {
			lblID = new JLabel("N\u00FAmero de Colegiado/Precolegiado:");
			lblID.setFont(new Font("Tahoma", Font.BOLD, 13));
		}
		return lblID;
	}
	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
			textField.setColumns(20);
		}
		return textField;
	}
	private JLabel getLblError() {
		if (lblError == null) {
			lblError = new JLabel("");
			lblError.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblError.setForeground(Color.RED);
			lblError.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return lblError;
	}

	private void mostrarPreincripcion() {
		// TODO Auto-generated method stub
		
	}
}
