package kike.gui.colegiado;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import kike.modelo.colegiado.ColegiadoDTO;

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.awt.event.ActionEvent;

public class MostrarDatosInscripcion extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private JPanel panelPrincipal;
	private JPanel panelFinalizar;
	private JButton btnCancelar;
	private JButton btnFinalizar;
	private JLabel lblTitulo;
	private JPanel panelCentral;
	private JPanel panelNombre;
	private JLabel lblNombre;
	private JLabel lblNombreData;
	private JPanel panelApellidos;
	private JLabel lblApellidos;
	private JLabel lblApellidosData;
	private JPanel panelNumCol;
	private JLabel lblNumCol;
	private JLabel lblNumColData;
	private JPanel panelFechaSol;
	private JLabel lblFechaSol;
	private JLabel lblFechaSolData;
	private JPanel panelPrecio;
	private JLabel lblPrecio;
	private JLabel lblPrecioData;
	
	
	private PreInscribeColegiado pic;


	/**
	 * Create the dialog.
	 */
	public MostrarDatosInscripcion(PreInscribeColegiado pic) {
		this.pic = pic;
		inicializarDatos();
		setMinimumSize(new Dimension(450, 450));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 450, 450);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getPanelPrincipal(), BorderLayout.CENTER);
	}

	private void inicializarDatos() {
		ColegiadoDTO cdto = pic.getColegiadoDTO();
		getLblNombreData().setText(cdto.nombre);
		getLblApellidosData().setText(cdto.apellidos);
		getLblNumColData().setText(cdto.id_colegiado);
		getLblFechaSolData().setText(new Date(System.currentTimeMillis()).toString());
		getLblPrecioData().setText(pic.getPrecio());
	}

	private JPanel getPanelPrincipal() {
		if (panelPrincipal == null) {
			panelPrincipal = new JPanel();
			panelPrincipal.setLayout(new BorderLayout(0, 0));
			panelPrincipal.add(getPanelFinalizar(), BorderLayout.SOUTH);
			panelPrincipal.add(getLblTitulo(), BorderLayout.NORTH);
			panelPrincipal.add(getPanelCentral(), BorderLayout.CENTER);
		}
		return panelPrincipal;
	}
	private JPanel getPanelFinalizar() {
		if (panelFinalizar == null) {
			panelFinalizar = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panelFinalizar.getLayout();
			flowLayout.setAlignment(FlowLayout.TRAILING);
			panelFinalizar.add(getBtnCancelar());
			panelFinalizar.add(getBtnFinalizar());
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
	private JButton getBtnFinalizar() {
		if (btnFinalizar == null) {
			btnFinalizar = new JButton("Finalizar Inscripci\u00F3n");
			btnFinalizar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					inscribirCurso();
				}
			});
			btnFinalizar.setForeground(Color.WHITE);
			btnFinalizar.setBackground(Color.GREEN);
		}
		return btnFinalizar;
	}
	private JLabel getLblTitulo() {
		if (lblTitulo == null) {
			lblTitulo = new JLabel("Justificante de la inscripci\u00F3n");
			lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
			lblTitulo.setFont(new Font("Arial Black", Font.PLAIN, 24));
		}
		return lblTitulo;
	}
	private JPanel getPanelCentral() {
		if (panelCentral == null) {
			panelCentral = new JPanel();
			panelCentral.setBorder(new EmptyBorder(20, 0, 20, 0));
			panelCentral.setLayout(new GridLayout(0, 1, 0, 0));
			panelCentral.add(getPanelNombre());
			panelCentral.add(getPanelApellidos());
			panelCentral.add(getPanelNumCol());
			panelCentral.add(getPanelFechaSol());
			panelCentral.add(getPanelPrecio());
		}
		return panelCentral;
	}
	private JPanel getPanelNombre() {
		if (panelNombre == null) {
			panelNombre = new JPanel();
			panelNombre.setBorder(new EmptyBorder(0, 30, 0, 0));
			FlowLayout flowLayout = (FlowLayout) panelNombre.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			panelNombre.add(getLblNombre());
			panelNombre.add(getLblNombreData());
		}
		return panelNombre;
	}
	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre:");
			lblNombre.setFont(new Font("Arial", Font.PLAIN, 15));
			lblNombre.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return lblNombre;
	}
	private JLabel getLblNombreData() {
		if (lblNombreData == null) {
			lblNombreData = new JLabel("");
			lblNombreData.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		return lblNombreData;
	}
	private JPanel getPanelApellidos() {
		if (panelApellidos == null) {
			panelApellidos = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panelApellidos.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			panelApellidos.setBorder(new EmptyBorder(0, 30, 0, 0));
			panelApellidos.add(getLblApellidos());
			panelApellidos.add(getLblApellidosData());
		}
		return panelApellidos;
	}
	private JLabel getLblApellidos() {
		if (lblApellidos == null) {
			lblApellidos = new JLabel("Apellidos:");
			lblApellidos.setFont(new Font("Arial", Font.PLAIN, 15));
			lblApellidos.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return lblApellidos;
	}
	private JLabel getLblApellidosData() {
		if (lblApellidosData == null) {
			lblApellidosData = new JLabel("");
			lblApellidosData.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		return lblApellidosData;
	}
	private JPanel getPanelNumCol() {
		if (panelNumCol == null) {
			panelNumCol = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panelNumCol.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			panelNumCol.setBorder(new EmptyBorder(0, 30, 0, 0));
			panelNumCol.add(getLblNumCol());
			panelNumCol.add(getLblNumColData());
		}
		return panelNumCol;
	}
	private JLabel getLblNumCol() {
		if (lblNumCol == null) {
			lblNumCol = new JLabel("Numero de Colegiado:");
			lblNumCol.setFont(new Font("Arial", Font.PLAIN, 15));
			lblNumCol.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return lblNumCol;
	}
	private JLabel getLblNumColData() {
		if (lblNumColData == null) {
			lblNumColData = new JLabel("");
			lblNumColData.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		return lblNumColData;
	}
	private JPanel getPanelFechaSol() {
		if (panelFechaSol == null) {
			panelFechaSol = new JPanel();
			FlowLayout fl_panelFechaSol = (FlowLayout) panelFechaSol.getLayout();
			fl_panelFechaSol.setAlignment(FlowLayout.LEFT);
			panelFechaSol.setBorder(new EmptyBorder(0, 30, 0, 0));
			panelFechaSol.add(getLblFechaSol());
			panelFechaSol.add(getLblFechaSolData());
		}
		return panelFechaSol;
	}
	private JLabel getLblFechaSol() {
		if (lblFechaSol == null) {
			lblFechaSol = new JLabel("Fecha de solicitud:");
			lblFechaSol.setFont(new Font("Arial", Font.PLAIN, 15));
			lblFechaSol.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return lblFechaSol;
	}
	private JLabel getLblFechaSolData() {
		if (lblFechaSolData == null) {
			lblFechaSolData = new JLabel("");
			lblFechaSolData.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		return lblFechaSolData;
	}
	private JPanel getPanelPrecio() {
		if (panelPrecio == null) {
			panelPrecio = new JPanel();
			FlowLayout fl_panelPrecio = (FlowLayout) panelPrecio.getLayout();
			fl_panelPrecio.setAlignment(FlowLayout.LEFT);
			panelPrecio.setBorder(new EmptyBorder(0, 30, 0, 0));
			panelPrecio.add(getLblPrecio());
			panelPrecio.add(getLblPrecioData());
		}
		return panelPrecio;
	}
	private JLabel getLblPrecio() {
		if (lblPrecio == null) {
			lblPrecio = new JLabel("Cantidad a abonar:");
			lblPrecio.setFont(new Font("Arial", Font.PLAIN, 15));
			lblPrecio.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return lblPrecio;
	}
	private JLabel getLblPrecioData() {
		if (lblPrecioData == null) {
			lblPrecioData = new JLabel("");
			lblPrecioData.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		return lblPrecioData;
	}

	private void inscribirCurso() {
		pic.cm.inscribirse(getLblNumColData().getText());
		JOptionPane.showConfirmDialog(null, "Se le ha enscrito al curso correctamente", "Confirmacion", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
		pic.dispose();
		this.dispose();
	}
}
