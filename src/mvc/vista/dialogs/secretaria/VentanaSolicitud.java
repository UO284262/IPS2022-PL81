package mvc.vista.dialogs.secretaria;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;

import mvc.controlador.SolicitudControler;
import mvc.modelo.colegiado.ColegiadoDto2;

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
	private JLabel lblAño;
	private JTextField txtTitulacion;
	private JTextField txtAño;
	private JTextField txtCentro;
	private JPanel pnDatosBancarios;
	private JLabel lblCuentaBancaria;
	private JTextField txtCuentaBancaria;
	private SolicitudControler controler;
	private JButton btCancelar;

	
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
		getContentPane().add(getBtCancelar());

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
			lblApellidos.setBounds(10, 63, 59, 13);
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
			lblTelefono.setBounds(10, 151, 59, 13);
		}
		return lblTelefono;
	}
	private JLabel getLblPoblacion() {
		if (lblPoblacion == null) {
			lblPoblacion = new JLabel("Poblacion:");
			lblPoblacion.setBounds(5, 121, 64, 13);
		}
		return lblPoblacion;
	}
	private JLabel getLblTitulacion() {
		if (lblTitulacion == null) {
			lblTitulacion = new JLabel("Titulacion:");
			lblTitulacion.setHorizontalAlignment(SwingConstants.CENTER);
			lblTitulacion.setBounds(10, 29, 55, 13);
		}
		return lblTitulacion;
	}
	private JLabel getLblCentro() {
		if (lblCentro == null) {
			lblCentro = new JLabel("Centro:");
			lblCentro.setHorizontalAlignment(SwingConstants.CENTER);
			lblCentro.setBounds(10, 59, 45, 13);
		}
		return lblCentro;
	}
	public JButton getBtnFinalizar() {
		if (btnFinalizar == null) {
			btnFinalizar = new JButton("Finalizar");
			btnFinalizar.setBackground(Color.GREEN);
			btnFinalizar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(camposCorrectos())
					   añadirColegiado();
					else
					   mostrarError();
				}
			});
			btnFinalizar.setBounds(287, 384, 85, 21);
		}
		return btnFinalizar;
	}
	

	/**
	 * Se comprueba que todos los campos no están vacíos
	 */
	private boolean camposCorrectos() {
		if (!txtNombre.getText().trim().isEmpty()) {
			if (!txtApellidos.getText().trim().isEmpty()) {
				if (!txtDNI.getText().trim().isEmpty()) {
					if (!txtPoblacion.getText().isEmpty()) {
						if (!txtTitulacion.getText().isEmpty()) {
							if (!txtAño.getText().isEmpty()) {
								if (!txtCentro.getText().isEmpty()) {
									if (!txtCuentaBancaria.getText().isEmpty()) {
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
	
//	private boolean dniCorrecto() {
//		if(txtDNI.getText().length()<=9)
//			return true;
//		else
//			return false;
//	}
	
	private void añadirColegiado() {
		if (!controler.colegiadoExistente(txtDNI.getText())){
			controler.validarSolicitud(newColegiado());
			dispose();
			completado();
		}else
		   mostrarErrorColegiadoExistente();
		
	}
	
	private ColegiadoDto2 newColegiado() {
		
		ColegiadoDto2 dto = new ColegiadoDto2();
		dto.apellidos = txtApellidos.getText();
		dto.dni = txtDNI.getText();
		dto.nombre = txtNombre.getText();
		dto.tlfn = Integer.parseInt(txtTelefono.getText());
		dto.poblacion = txtPoblacion.getText();
		dto.titulacion = txtTitulacion.getText();
		dto.año = Integer.parseInt(txtAño.getText());
		dto.centro = txtCentro.getText();
		dto.cuentaBancaria = txtCuentaBancaria.getText();
		dto.fecha = LocalDateTime.now();
		dto.isValid = 0;
		
		return dto;
	}
		
		
	private JTextField getTxtNombre() {
		if (txtNombre == null) {
			txtNombre = new JTextField();
			txtNombre.setBounds(72, 28, 248, 25);
			txtNombre.setColumns(10);
		}
		return txtNombre;
	}
	private JTextField getTxtApellidos() {
		if (txtApellidos == null) {
			txtApellidos = new JTextField();
			txtApellidos.setBounds(72, 57, 247, 25);
			txtApellidos.setColumns(10);
		}
		return txtApellidos;
	}
	private JTextField getTxtDNI() {
		if (txtDNI == null) {
			txtDNI = new JTextField();
			txtDNI.setBounds(72, 86, 248, 25);
			txtDNI.setColumns(10);
		}
		return txtDNI;
	}
	private JTextField getTxtPoblacion() {
		if (txtPoblacion == null) {
			txtPoblacion = new JTextField();
			txtPoblacion.setBounds(72, 115, 248, 25);
			txtPoblacion.setColumns(10);
		}
		return txtPoblacion;
	}
	private JTextField getTxtTelefono() {
		if (txtTelefono == null) {
			txtTelefono = new JTextField();
			txtTelefono.setBounds(72, 142, 248, 25);
			txtTelefono.setColumns(10);
		}
		return txtTelefono;
	}
	private JPanel getPnDatosPersonales() {
		if (pnDatosPersonales == null) {
			pnDatosPersonales = new JPanel();
			pnDatosPersonales.setBorder(new TitledBorder(null, "Datos Personales", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnDatosPersonales.setBounds(10, 27, 368, 177);
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
			pnDatosAcadmicos.setBounds(10, 204, 368, 105);
			pnDatosAcadmicos.setLayout(null);
			pnDatosAcadmicos.add(getLblTitulacion());
			pnDatosAcadmicos.add(getLblCentro());
			pnDatosAcadmicos.add(getLblAño());
			pnDatosAcadmicos.add(getTxtTitulacion());
			pnDatosAcadmicos.add(getTxtAño());
			pnDatosAcadmicos.add(getTxtCentro());
		}
		return pnDatosAcadmicos;
	}
	private JLabel getLblAño() {
		if (lblAño == null) {
			lblAño = new JLabel("A\u00F1o");
			lblAño.setHorizontalAlignment(SwingConstants.CENTER);
			lblAño.setBounds(10, 82, 45, 13);
		}
		return lblAño;
	}
	private JTextField getTxtTitulacion() {
		if (txtTitulacion == null) {
			txtTitulacion = new JTextField();
			txtTitulacion.setBounds(75, 25, 247, 22);
			txtTitulacion.setColumns(10);
		}
		return txtTitulacion;
	}
	private JTextField getTxtAño() {
		if (txtAño == null) {
			txtAño = new JTextField();
			txtAño.setBounds(73, 73, 249, 22);
			txtAño.setColumns(10);
		}
		return txtAño;
	}
	private JTextField getTxtCentro() {
		if (txtCentro == null) {
			txtCentro = new JTextField();
			txtCentro.setBounds(73, 51, 249, 21);
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
			JOptionPane.showMessageDialog(null, "Error al procesar el número de teléfono,"
					+ " revíselo por favor");
		}
	    
	    public void mostrarErrorColegiadoExistente() {
			JOptionPane.showMessageDialog(null, "Ya existe un colegiado con ese "
					+ "dni");
		}
	    
	    public void completado() {
			JOptionPane.showMessageDialog(null, "Se ha registrado correctamente");
		}
	private JButton getBtCancelar() {
		if (btCancelar == null) {
			btCancelar = new JButton("Cancelar");
			btCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btCancelar.setBackground(Color.RED);
			btCancelar.setBounds(192, 384, 85, 21);
		}
		return btCancelar;
	}
}
