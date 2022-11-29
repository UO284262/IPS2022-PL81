package mvc.vista.dialogs.colegiado;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import mvc.controlador.ColectivoDataBase;
import mvc.controlador.ColegiadoDataBase;
import mvc.controlador.InscripcionDataBase;
import mvc.controlador.TerceroDataBase;
import mvc.modelo.colegiado.ColegiadoDTO;
import mvc.modelo.curso.CursoDTO;
import mvc.modelo.curso.CursoManager;
import mvc.modelo.tercero.TerceroDTO;
import mvc.vista.dialogs.terceros.MostrarDatosTercero;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;
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
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

public class PreInscribeColegiado extends JDialog {

	private static final long serialVersionUID = 1L;
	private static final int OPTION_COL_PRECOL = 1;
	private static final int OPTION_TERCEROS = 2;
	
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

	public CursoManager cm;
	private JTable table;

	private DefaultTableModel tm;
	private JLabel lblNewLabel;
	private JComboBox<String> comboBox;
	private DefaultComboBoxModel<String> modeloCB;
	private JSeparator separator;

	private ColegiadoDTO col;
	
	private int option = -1;
	private TerceroDTO tercero;
	
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
		modeloCB = new DefaultComboBoxModel<>();
		modeloCB.addAll(ColectivoDataBase.getColectivos());
		setModal(true);
		setMinimumSize(new Dimension(900, 600));
		setBounds(100, 100, 900, 600);
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
			scrollPane.setBorder(new EmptyBorder(20, 10, 5, 10));
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
			lblTitulo = new JLabel("Inscripci\u00F3n: Seleccione un curso y su Colectivo");
			lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
			lblTitulo.setFont(new Font("Arial Black", Font.PLAIN, 21));
		}
		return lblTitulo;
	}
	private JPanel getPanelSelectorId() {
		if (panelSelectorId == null) {
			panelSelectorId = new JPanel();
			panelSelectorId.setBorder(new CompoundBorder(new EmptyBorder(5, 0, 5, 0), new CompoundBorder(new LineBorder(new Color(0, 0, 0), 2, true), new EmptyBorder(4, 4, 4, 4))));
			GridBagLayout gbl_panelSelectorId = new GridBagLayout();
			gbl_panelSelectorId.columnWidths = new int[]{78, 205, 0, 53, 84, 285, 0};
			gbl_panelSelectorId.rowHeights = new int[]{31, 0};
			gbl_panelSelectorId.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panelSelectorId.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panelSelectorId.setLayout(gbl_panelSelectorId);
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
			gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
			gbc_lblNewLabel.gridx = 0;
			gbc_lblNewLabel.gridy = 0;
			panelSelectorId.add(getLabel_1(), gbc_lblNewLabel);
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.fill = GridBagConstraints.BOTH;
			gbc_comboBox.insets = new Insets(0, 0, 0, 5);
			gbc_comboBox.gridx = 1;
			gbc_comboBox.gridy = 0;
			panelSelectorId.add(getComboBox(), gbc_comboBox);
			GridBagConstraints gbc_separator = new GridBagConstraints();
			gbc_separator.fill = GridBagConstraints.BOTH;
			gbc_separator.insets = new Insets(0, 0, 0, 5);
			gbc_separator.gridx = 3;
			gbc_separator.gridy = 0;
			panelSelectorId.add(getSeparator(), gbc_separator);
			GridBagConstraints gbc_lblID = new GridBagConstraints();
			gbc_lblID.fill = GridBagConstraints.BOTH;
			gbc_lblID.insets = new Insets(0, 0, 0, 5);
			gbc_lblID.gridx = 4;
			gbc_lblID.gridy = 0;
			panelSelectorId.add(getLblID(), gbc_lblID);
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.fill = GridBagConstraints.BOTH;
			gbc_textField.gridx = 5;
			gbc_textField.gridy = 0;
			panelSelectorId.add(getTextField(), gbc_textField);
		}
		return panelSelectorId;
	}
	private JLabel getLblID() {
		if (lblID == null) {
			lblID = new JLabel("DNI:");
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
		
		if(colectivoCorrecto()) {
		
			int index = getTable().getSelectedRow();
			if(index < 0) {
				getLblError().setText("Error: seleccione un curso.");
				return;
			}
			
			CursoDTO cc = cursos.get(index);
			
			if(InscripcionDataBase.isInscrito(getTextField().getText(), cc.title)) {
				getLblError().setText("Error: usted ya se ha inscrito a dicho curso.");
				return;
			}
			
			cm = new CursoManager(cc);			
			
			mostrarDatosInscripcion();

		}
		
	}

	private void mostrarDatosInscripcion() {
		
		if(option == OPTION_COL_PRECOL) {
			MostrarDatosInscripcion ventana = new MostrarDatosInscripcion(this);
			ventana.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			ventana.setModal(true);
			ventana.setLocationRelativeTo(this);
			ventana.setVisible(true);
		} else if (option == OPTION_TERCEROS) {
			MostrarDatosTercero ventana = new MostrarDatosTercero(this);
			ventana.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			ventana.setModal(true);
			ventana.setLocationRelativeTo(this);
			ventana.setVisible(true);
		}
		
	}


	private JTable getTable() {
		if (table == null) {
			//establecerCursos(); 
			table = new JTable();
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setFocusable(false);
			table.setDefaultEditor(Object.class, null);
		}
		return table;
	}

	private void establecerCursos() {
		if(modeloCB.getSelectedItem() == null) {
			getLblError().setText("Error, seleccione un colectivo");
			return;
		}
		
		getLblError().setText("");
		
		cursos = CursoManager.getModeloCursosAbiertosColectivo((String)modeloCB.getSelectedItem());
		
		tm=new DefaultTableModel(new String[]{"Title", "Price", "Days", "Fecha de inicio de inscipcion", "Fecha de fin de inscipcion", "Plazas Disponibles", "Plazas Solicitadas"}, cursos.size()) ;
		//carga cada uno de los valores de pojos usando PropertyUtils (de apache coommons beanutils)
		for (int i=0; i<cursos.size(); i++) {
			CursoDTO pojo=cursos.get(i);
			tm.setValueAt(pojo.title, i, 0);
			tm.setValueAt(pojo.price, i, 1);
			tm.setValueAt(pojo.days, i, 2);
			tm.setValueAt(pojo.fechaInicioInscipcion, i, 3);
			tm.setValueAt(pojo.fechaFinInscipcion, i, 4);
			tm.setValueAt(pojo.plazasDisponibles, i, 5);
			tm.setValueAt(pojo.plazasSolicitadas, i, 6);
		}
		getTable().setModel(tm);
	}

	private JLabel getLabel_1() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Colectivo:");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		}
		return lblNewLabel;
	}
	private JComboBox<String> getComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox<String>();
			comboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					establecerCursos();
				}
			});
			comboBox.setModel(modeloCB);
		}
		return comboBox;
	}
	private JSeparator getSeparator() {
		if (separator == null) {
			separator = new JSeparator();
			separator.setBorder(new EmptyBorder(0, 15, 0, 0));
			separator.setForeground(Color.BLACK);
			separator.setOrientation(SwingConstants.VERTICAL);
		}
		return separator;
	}

	private boolean colectivoCorrecto() {		
		if(modeloCB.getSelectedItem() == null) {
			getLblError().setText("Error, seleccione un colectivo");
			return false;
		} 
		if(getTextField().getText() == null || getTextField().getText().isBlank()) {
			getLblError().setText("Error, introduzca un dni valido");
			return false;
		}
		
		
		getLblError().setText("");
		
		String colectivo = (String) modeloCB.getSelectedItem();
		
		if(colectivo.equals("colegiado")) {
			Optional<ColegiadoDTO> ocol = ColegiadoDataBase.getColegiadoByDNI(getTextField().getText());
			if(ocol.isPresent()) {
				col = ocol.get();
				if(col.tipo == ColegiadoDTO.SOLICITUD_COLEGIADO ) {
					option = OPTION_COL_PRECOL;
					return true;
				}
			}
		} else if(colectivo.equals("pre-colegiado")) {
			Optional<ColegiadoDTO> ocol = ColegiadoDataBase.getColegiadoByDNI(getTextField().getText());
			if(ocol.isPresent()) {
				col = ocol.get();
				if(col.tipo == ColegiadoDTO.SOLICITUD_PRE_COLEGIADO) {
					option = OPTION_COL_PRECOL;
					return true;
				}
			}
		} else {
			Optional<TerceroDTO> otercero = TerceroDataBase.getTerceroByDNI(getTextField().getText());
			if(otercero.isPresent()) {
				tercero = otercero.get();
				if(tercero.colectivo.equals(colectivo)) {
					option = OPTION_TERCEROS;
					return true;
				}
			}
		}
		
		getLblError().setText("DNI incorrecto o colectivo equivocado");
		
		return false;
	}

	public ColegiadoDTO getColegiadoDTO() {
		return col;
	}
	
	public TerceroDTO getTerceroDTO() {
		return tercero;
	}

}
