package kike.gui.colegiado;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import kike.gui.colectivo.SelectorColectivos;
import kike.modelo.colegiado.ColegiadoDTO;
import kike.modelo.colegiado.ColegiadoManager;
import kike.modelo.curso.CursoDTO;
import kike.modelo.curso.CursoManager;
import kike.persistence.CursoDataBase;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JTable;

public class PreInscribeColegiado extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private JPanel panelCentral;
	private JPanel panelFinalizar;
	private JButton btnCancelar;
	private JButton btnInscribeme;
	private JScrollPane scrollPane;
	private List<CursoDTO> cursos;
	private JPanel panelSuperior;
	private JLabel lblTitulo;
	private JPanel panelSelectorId;
	private JLabel lblID;
	private JTextField textField;
	private JLabel lblError;

	private ColegiadoManager colm;
	private double precio;
	public CursoManager cm;
	private JTable table;

	private DefaultTableModel tm;
	private String grupo;
	private HashMap<String, Double> colectivos;
	
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
		setModal(true);
		cursos = CursoManager.getModeloCursosAbiertos();
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
					mostrarPreincripcion();
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
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
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
		getLblError().setText("");
		int index = getTable().getSelectedRow();
		if(index < 0) {
			getLblError().setText("Error: seleccione un curso.");
			return;
		}
		CursoDTO cc = cursos.get(index);
		
		if(!selectorCol(cc)) {
			return;
		}
		
		colm = new ColegiadoManager(getTextField().getText());
		if(!colm.validaID()) { 
			getLblError().setText("Error: id invalido.");

		} else if(colm.isInscrito(cc.title)) {  
			getLblError().setText("Error: Este usuario ya ha sido preinscrito.");
		
		} else {			
			cm = new CursoManager(cc);			
			colm.getInfo();
			precio = cc.price;
			
			MostrarDatosInscripcion ventana = new MostrarDatosInscripcion(this);
			ventana.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			ventana.setModal(true);
			ventana.setLocationRelativeTo(this);
			ventana.setVisible(true);
			
		}		
		
	}

	public ColegiadoDTO getColegiadoDTO() {
		return colm.dto;
	}

	public Double getPrecio() {
		return precio;
	}
	private JTable getTable() {
		if (table == null) {
			tm=new DefaultTableModel(new String[]{"Title", "Price", "Days", "Fecha de inicio de inscipcion", "Fecha de fin de inscipcion", "Plazas Disponibles"}, cursos.size()) ;
			//carga cada uno de los valores de pojos usando PropertyUtils (de apache coommons beanutils)
			for (int i=0; i<cursos.size(); i++) {
				CursoDTO pojo=cursos.get(i);
				tm.setValueAt(pojo.title, i, 0);
				tm.setValueAt(pojo.price, i, 1);
				tm.setValueAt(pojo.days, i, 2);
				tm.setValueAt(pojo.fechaInicioInscipcion, i, 3);
				tm.setValueAt(pojo.fechaFinInscipcion, i, 4);
				tm.setValueAt(pojo.plazasDisponibles, i, 5);
			}
			table = new JTable();
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setFocusable(false);
			table.setDefaultEditor(Object.class, null);
			table.setModel(tm);
		}
		return table;
	}

	private boolean selectorCol(CursoDTO curso) {
		colectivos = CursoDataBase.getColectivos(curso);
		if(!colectivos.isEmpty()) {
			int respuesta = JOptionPane.showConfirmDialog(null, "<html>Existend descuentos por colectivos para este curso<br>"
					+ "�Deseas seleccionar un colectivo para un posible descuento?<html>", "Confirmacion", JOptionPane.YES_NO_CANCEL_OPTION);
			if(respuesta == JOptionPane.NO_OPTION)
				grupo = null;
			else if(respuesta == JOptionPane.YES_OPTION) {
				seleccionaColectivo();
			} else {
				return false;
			}
		} else {
			grupo = null;
		}
		return true;
	}
	
	private void seleccionaColectivo() {
		SelectorColectivos ventana = new SelectorColectivos(this, colectivos);
		ventana.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		ventana.setModal(true);
		ventana.setLocationRelativeTo(this);
		ventana.setVisible(true);
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	
	public double getDescuento() {
		if(grupo == null) {
			return 0;
		}
		
		return colectivos.get(grupo)/100;
	}
}
