package rodro.vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;

import rodro.controlador.SolicitudControler;
import rodro.modelo.ColegiadoDto;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.awt.event.ActionEvent;

public class VentanaSolicitud extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblNombre;
	private JLabel lblApellidos;
	private JLabel lblDNI;
	private JLabel lblTelefono;
	private JLabel lblPoblacion;
	private JLabel lblTitulacion;
	private JLabel lblCentro;
	private JButton btnFinalizar;
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JTextField txtDNI;
	private JTextField txtPoblacion;
	private JTextField txtTelefono;
	private JPanel pnDatosPersonales;
	private JPanel pnDatosAcadmicos;
	private JLabel lblA�o;
	private JTextField txtTitulacion;
	private JTextField txtA�o;
	private JTextField txtCentro;
	private JPanel pnDatosBancarios;
	private JLabel lblCuentaBancaria;
	private JTextField txtCuentaBancaria;
	private SolicitudControler controler;

	
	public VentanaSolicitud(SolicitudControler con) {
		setModal(true);
		setTitle("Ventana Solicitud");
		setBounds(200, 200, 414, 451);
		setResizable(false);
		this.controler = con;
		setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		getContentPane().add(getBtnFinalizar());
		getContentPane().add(getPnDatosPersonales());
		getContentPane().add(getPnDatosAcadmicos());
		getContentPane().add(getPnDatosBancarios());

	}
	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre:");
			lblNombre.setBounds(10, 31, 52, 13);
		}
		return lblNombre;
	}
	private JLabel getLblApellidos() {
		if (lblApellidos == null) {
			lblApellidos = new JLabel("Apellidos:");
			lblApellidos.setBounds(10, 63, 52, 13);
		}
		return lblApellidos;
	}
	private JLabel getLblDNI() {
		if (lblDNI == null) {
			lblDNI = new JLabel("DNI");
			lblDNI.setHorizontalAlignment(SwingConstants.CENTER);
			lblDNI.setBounds(10, 89, 45, 13);
		}
		return lblDNI;
	}
	private JLabel getLblTelefono() {
		if (lblTelefono == null) {
			lblTelefono = new JLabel("Telefono:");
			lblTelefono.setBounds(10, 141, 45, 13);
		}
		return lblTelefono;
	}
	private JLabel getLblPoblacion() {
		if (lblPoblacion == null) {
			lblPoblacion = new JLabel("Poblacion:");
			lblPoblacion.setBounds(10, 118, 52, 13);
		}
		return lblPoblacion;
	}
	private JLabel getLblTitulacion() {
		if (lblTitulacion == null) {
			lblTitulacion = new JLabel("Titulacion:");
			lblTitulacion.setHorizontalAlignment(SwingConstants.CENTER);
			lblTitulacion.setBounds(10, 25, 55, 13);
		}
		return lblTitulacion;
	}
	private JLabel getLblCentro() {
		if (lblCentro == null) {
			lblCentro = new JLabel("Centro:");
			lblCentro.setHorizontalAlignment(SwingConstants.CENTER);
			lblCentro.setBounds(10, 47, 45, 13);
		}
		return lblCentro;
	}
	public JButton getBtnFinalizar() {
		if (btnFinalizar == null) {
			btnFinalizar = new JButton("Finalizar");
			btnFinalizar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(camposCorrectos())
					a�adirColegiado();
					mostrarError();
				}
			});
			btnFinalizar.setBounds(287, 384, 85, 21);
		}
		return btnFinalizar;
	}
	

	/**
	 * Se comprueba que todos los campos no est�n vac�os
	 */
	private boolean camposCorrectos() {
		if (!txtNombre.getText().trim().isEmpty()) {
			if (!txtApellidos.getText().trim().isEmpty()) {
				if (!txtDNI.getText().trim().isEmpty()) {
					if (!txtPoblacion.getText().isEmpty()) {
						if (!txtTitulacion.getText().isEmpty()) {
							if (!txtA�o.getText().isEmpty()) {
								if (!txtCentro.getText().isEmpty()) {
									if (!txtCuentaBancaria.getText().isEmpty()) {
										if(dniCorrecto())
										   if(telefonoCorrecto())
											  return true;
										   mostrarErrorTelefono();
									}
									}
								}
							}
						}
					}

				}
			}
	return false;

	}
	
	private boolean telefonoCorrecto() {
			if (txtTelefono.getText().length() == 9) {
				return true;
			}
			return false;
	}
	
	private boolean dniCorrecto() {
		if(txtDNI.getText().length()==9)
			return true;
		else
			return false;
	}
	
	private void a�adirColegiado() {
		if (controler.colegiadoExistente(txtDNI.getText())){
			controler.validarSolicitud(txtDNI.getText(),newColegiado());
		}
		mostrarErrorColegiadoExistente();
		
	}
	
	private ColegiadoDto newColegiado() {
		ColegiadoDto dto = new ColegiadoDto();
		dto.apellidos = txtApellidos.getText();
		dto.dni = txtDNI.getText();
		dto.nombre = txtNombre.getText();
		dto.tlfn = Integer.parseInt(txtTelefono.getText());
		dto.poblacion = txtPoblacion.getText();
		dto.titulacion = txtTitulacion.getText();
		dto.a�o = Integer.parseInt(txtA�o.getText());
		dto.cuentaBancaria = txtCuentaBancaria.getText();
		dto.fecha = LocalDateTime.now();
		dto.isValid = false;
		
		return dto;
	}
		
		
	private JTextField getTxtNombre() {
		if (txtNombre == null) {
			txtNombre = new JTextField();
			txtNombre.setBounds(72, 28, 248, 19);
			txtNombre.setColumns(10);
		}
		return txtNombre;
	}
	private JTextField getTxtApellidos() {
		if (txtApellidos == null) {
			txtApellidos = new JTextField();
			txtApellidos.setBounds(72, 57, 247, 19);
			txtApellidos.setColumns(10);
		}
		return txtApellidos;
	}
	private JTextField getTxtDNI() {
		if (txtDNI == null) {
			txtDNI = new JTextField();
			txtDNI.setBounds(72, 86, 248, 19);
			txtDNI.setColumns(10);
		}
		return txtDNI;
	}
	private JTextField getTxtPoblacion() {
		if (txtPoblacion == null) {
			txtPoblacion = new JTextField();
			txtPoblacion.setBounds(72, 115, 248, 19);
			txtPoblacion.setColumns(10);
		}
		return txtPoblacion;
	}
	private JTextField getTxtTelefono() {
		if (txtTelefono == null) {
			txtTelefono = new JTextField();
			txtTelefono.setBounds(72, 138, 248, 19);
			txtTelefono.setColumns(10);
		}
		return txtTelefono;
	}
	private JPanel getPnDatosPersonales() {
		if (pnDatosPersonales == null) {
			pnDatosPersonales = new JPanel();
			pnDatosPersonales.setBorder(new TitledBorder(null, "Datos Personales", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnDatosPersonales.setBounds(10, 27, 368, 167);
			pnDatosPersonales.setLayout(null);
			pnDatosPersonales.add(getTxtNombre());
			pnDatosPersonales.add(getTxtApellidos());
			pnDatosPersonales.add(getTxtDNI());
			pnDatosPersonales.add(getTxtPoblacion());
			pnDatosPersonales.add(getLblNombre());
			pnDatosPersonales.add(getLblApellidos());
			pnDatosPersonales.add(getLblDNI());
			pnDatosPersonales.add(getLblPoblacion());
			pnDatosPersonales.add(getLblTelefono());
			pnDatosPersonales.add(getTxtTelefono());
		}
		return pnDatosPersonales;
	}
	private JPanel getPnDatosAcadmicos() {
		if (pnDatosAcadmicos == null) {
			pnDatosAcadmicos = new JPanel();
			pnDatosAcadmicos.setBorder(new TitledBorder(null, "Datos Academicos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnDatosAcadmicos.setBounds(10, 204, 368, 96);
			pnDatosAcadmicos.setLayout(null);
			pnDatosAcadmicos.add(getLblTitulacion());
			pnDatosAcadmicos.add(getLblCentro());
			pnDatosAcadmicos.add(getLblA�o());
			pnDatosAcadmicos.add(getTxtTitulacion());
			pnDatosAcadmicos.add(getTxtA�o());
			pnDatosAcadmicos.add(getTxtCentro());
		}
		return pnDatosAcadmicos;
	}
	private JLabel getLblA�o() {
		if (lblA�o == null) {
			lblA�o = new JLabel("A\u00F1o");
			lblA�o.setHorizontalAlignment(SwingConstants.CENTER);
			lblA�o.setBounds(10, 70, 45, 13);
		}
		return lblA�o;
	}
	private JTextField getTxtTitulacion() {
		if (txtTitulacion == null) {
			txtTitulacion = new JTextField();
			txtTitulacion.setBounds(75, 22, 247, 19);
			txtTitulacion.setColumns(10);
		}
		return txtTitulacion;
	}
	private JTextField getTxtA�o() {
		if (txtA�o == null) {
			txtA�o = new JTextField();
			txtA�o.setBounds(73, 67, 249, 19);
			txtA�o.setColumns(10);
		}
		return txtA�o;
	}
	private JTextField getTxtCentro() {
		if (txtCentro == null) {
			txtCentro = new JTextField();
			txtCentro.setBounds(73, 44, 249, 19);
			txtCentro.setColumns(10);
		}
		return txtCentro;
	}
	private JPanel getPnDatosBancarios() {
		if (pnDatosBancarios == null) {
			pnDatosBancarios = new JPanel();
			pnDatosBancarios.setBorder(new TitledBorder(null, "Datos Bancarios", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnDatosBancarios.setBounds(10, 310, 368, 52);
			pnDatosBancarios.setLayout(null);
			pnDatosBancarios.add(getLblCuentaBancaria());
			pnDatosBancarios.add(getTxtCuentaBancaria());
		}
		return pnDatosBancarios;
	}
	private JLabel getLblCuentaBancaria() {
		if (lblCuentaBancaria == null) {
			lblCuentaBancaria = new JLabel("Cuenta Bancaria");
			lblCuentaBancaria.setHorizontalAlignment(SwingConstants.CENTER);
			lblCuentaBancaria.setBounds(10, 29, 113, 13);
		}
		return lblCuentaBancaria;
	}
	private JTextField getTxtCuentaBancaria() {
		if (txtCuentaBancaria == null) {
			txtCuentaBancaria = new JTextField();
			txtCuentaBancaria.setBounds(133, 26, 192, 19);
			txtCuentaBancaria.setColumns(10);
		}
		return txtCuentaBancaria;
	}
	

	    
	    
	    public void mostrarError() {
			JOptionPane.showMessageDialog(null, "Los campos no se han rellenado "
					+ "correctamente");
		}
	    
	    public void mostrarErrorTelefono() {
			JOptionPane.showMessageDialog(null, "Error al procesar el n�mero de tel�fono,"
					+ " rev�selo por favor");
		}
	    
	    public void mostrarErrorColegiadoExistente() {
			JOptionPane.showMessageDialog(null, "Ya existe un colegiado con ese "
					+ "dni");
		}
	    
	    public void completado() {
			JOptionPane.showMessageDialog(null, "Se ha registrado correctamente");
		}

}
