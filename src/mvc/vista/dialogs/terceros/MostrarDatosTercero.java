package mvc.vista.dialogs.terceros;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import mvc.modelo.tercero.TerceroDTO;
import mvc.vista.dialogs.colegiado.PreInscribeColegiado;

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

public class MostrarDatosTercero extends JDialog {

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
	private JPanel panelNumCol;
	private JLabel lblDni;
	private JLabel lblNumColData;
	private JPanel panelFechaSol;
	private JLabel lblFechaSol;
	private JLabel lblFechaSolData;
	private JPanel panelPrecio;
	private JLabel lblPrecio;
	private JLabel lblPrecioData;
	
	
	private PreInscribeColegiado pic;
	private JPanel panelPosCola;
	private JLabel lblPosCola;
	private JLabel lblPosColaData;

	private boolean hayCola = false;
	private int plazasCola;


	/**
	 * Create the dialog.
	 */
	public MostrarDatosTercero(PreInscribeColegiado pic) {
		this.pic = pic;
		inicializarDatos();
		setMinimumSize(new Dimension(560, 450));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 560, 450);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getPanelPrincipal(), BorderLayout.CENTER);
	}

	private void inicializarDatos() {
		TerceroDTO tdto = pic.getTerceroDTO();
		getLblNombreData().setText(tdto.nombre);
		getLblNumColData().setText(tdto.dni);
		getLblFechaSolData().setText(new Date(System.currentTimeMillis()).toString());
		getLblPrecioData().setText("" + pic.cm.getCurosDTO().price);
		plazasCola = pic.cm.getCurosDTO().plazasSolicitadas - pic.cm.getCurosDTO().plazasDisponibles + 1;
		if(plazasCola > 0) {
			hayCola = true;			
			getPanelCentral().add(getPanelPosCola());
			getLblPosColaData().setText("" + plazasCola);
		}
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
	private JPanel getPanelNumCol() {
		if (panelNumCol == null) {
			panelNumCol = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panelNumCol.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			panelNumCol.setBorder(new EmptyBorder(0, 30, 0, 0));
			panelNumCol.add(getLblDni());
			panelNumCol.add(getLblNumColData());
		}
		return panelNumCol;
	}
	private JLabel getLblDni() {
		if (lblDni == null) {
			lblDni = new JLabel("Dni:");
			lblDni.setFont(new Font("Arial", Font.PLAIN, 15));
			lblDni.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return lblDni;
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
		if(hayCola) {
			pic.cm.inscribirse(pic.getTerceroDTO().dni, plazasCola);
			JOptionPane.showConfirmDialog(null, "Se le ha añadido a la cola correctamente", "Confirmacion", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
		} else {
			pic.cm.inscribirse(pic.getTerceroDTO().dni);
			JOptionPane.showConfirmDialog(null, "Se le ha inscrito al curso correctamente", "Confirmacion", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
		}
		pic.dispose();
		this.dispose();
	}
	private JPanel getPanelPosCola() {
		if (panelPosCola == null) {
			panelPosCola = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panelPosCola.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			panelPosCola.setBorder(new EmptyBorder(0, 30, 0, 0));
			panelPosCola.add(getLblPosCola());
			panelPosCola.add(getLblPosColaData());
		}
		return panelPosCola;
	}
	private JLabel getLblPosCola() {
		if (lblPosCola == null) {
			lblPosCola = new JLabel("Posicion en la cola:");
			lblPosCola.setHorizontalAlignment(SwingConstants.TRAILING);
			lblPosCola.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		return lblPosCola;
	}
	private JLabel getLblPosColaData() {
		if (lblPosColaData == null) {
			lblPosColaData = new JLabel("");
			lblPosColaData.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		return lblPosColaData;
	}
}
