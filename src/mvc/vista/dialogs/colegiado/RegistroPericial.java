package mvc.vista.dialogs.colegiado;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import mvc.controlador.ColegiadoDataBase;
import mvc.controlador.PeritoDataBase;
import mvc.modelo.colegiado.ColegiadoDTO;
import mvc.modelo.perito.PeritoDTO;
import mvc.modelo.perito.PeritoManager;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.CardLayout;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

public class RegistroPericial extends JDialog {

	private static final long serialVersionUID = 1L;

	private static final int INSCRIP = 0;
	private static final int RENOV = 1;
	private static final int RENOV_ANTIGUO = 2;

	private static final String[] TEXTO_CONFIRMACION = new String[]{"inscrito","renovado","inscrito"};

	
	
	private final JPanel PanelSolicitud = new JPanel();
	private JPanel panelSuperior;
	private JLabel lblRenovacininscripcinPericial;
	private JPanel panelSelectorId;
	private JLabel lblNmeroDeColegiado;
	private JTextField textField;
	private JPanel panelFinalizar;
	private JLabel lblError;
	private JButton btnCancelar;
	private JButton btnInscribirrenovar;
	private JPanel panelDatos;
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
	private JPanel panelDni;
	private JLabel lblDni;
	private JLabel lblDniData;
	
	private ColegiadoDTO colegiado;
	private JPanel panelTelefono;
	private JLabel lblTelefono;
	private JLabel lblTelefonoData;
	private JPanel panelCuentaBancaria;
	private JLabel lblCuentabancaria;
	private JLabel lblCuentBancData;
	private JButton btnBuscar;
	private JPanel panelFinalizacion;
	private JLabel lblFinalizarInfo;
	private JPanel panelFF;
	private JButton btnFinalizar;
	private JButton btnBack;

	private JPanel panelPosListaP;
	private JLabel lblPosListaP;
	private JLabel lblPosListaPData;

	private PeritoManager pm;

	private int opcion;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegistroPericial dialog = new RegistroPericial();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegistroPericial() {
		setMinimumSize(new Dimension(500, 480));
		setBounds(100, 100, 500, 480);
		getContentPane().setLayout(new CardLayout(0, 0));
		PanelSolicitud.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(PanelSolicitud, "solicitud");
		PanelSolicitud.setLayout(new BorderLayout(0, 0));
		PanelSolicitud.add(getPanelSuperior(), BorderLayout.NORTH);
		PanelSolicitud.add(getPanelFinalizar(), BorderLayout.SOUTH);
		PanelSolicitud.add(getPanelDatos(), BorderLayout.CENTER);
		getContentPane().add(getPanelFinalizacion(), "Finalizacion");
	}
	private JPanel getPanelSuperior() {
		if (panelSuperior == null) {
			panelSuperior = new JPanel();
			panelSuperior.setLayout(new GridLayout(0, 1, 0, 0));
			panelSuperior.add(getLblRenovacininscripcinPericial());
			panelSuperior.add(getPanelSelectorId());
		}
		return panelSuperior;
	}
	private JLabel getLblRenovacininscripcinPericial() {
		if (lblRenovacininscripcinPericial == null) {
			lblRenovacininscripcinPericial = new JLabel("Renovaci\u00F3n/Inscripci\u00F3n de peritos");
			lblRenovacininscripcinPericial.setHorizontalAlignment(SwingConstants.CENTER);
			lblRenovacininscripcinPericial.setFont(new Font("Arial Black", Font.PLAIN, 21));
		}
		return lblRenovacininscripcinPericial;
	}
	private JPanel getPanelSelectorId() {
		if (panelSelectorId == null) {
			panelSelectorId = new JPanel();
			panelSelectorId.add(getLblNmeroDeColegiado());
			panelSelectorId.add(getTextField());
			panelSelectorId.add(getBtnBuscar());
		}
		return panelSelectorId;
	}
	private JLabel getLblNmeroDeColegiado() {
		if (lblNmeroDeColegiado == null) {
			lblNmeroDeColegiado = new JLabel("N\u00FAmero de Colegiado:");
			lblNmeroDeColegiado.setFont(new Font("Tahoma", Font.BOLD, 13));
		}
		return lblNmeroDeColegiado;
	}
	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
			textField.setColumns(20);
		}
		return textField;
	}
	private JPanel getPanelFinalizar() {
		if (panelFinalizar == null) {
			panelFinalizar = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panelFinalizar.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			panelFinalizar.add(getLblError());
			panelFinalizar.add(getBtnCancelar());
			panelFinalizar.add(getBtnInscribirrenovar());
		}
		return panelFinalizar;
	}
	private JLabel getLblError() {
		if (lblError == null) {
			lblError = new JLabel("");
			lblError.setHorizontalAlignment(SwingConstants.LEFT);
			lblError.setForeground(Color.RED);
			lblError.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return lblError;
	}
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.setForeground(Color.WHITE);
			btnCancelar.setBackground(Color.RED);
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancelar;
	}
	private JButton getBtnInscribirrenovar() {
		if (btnInscribirrenovar == null) {
			btnInscribirrenovar = new JButton("Inscribir/Renovar");
			btnInscribirrenovar.setEnabled(false);
			btnInscribirrenovar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					inscribeRenueva();
				}
			});
			btnInscribirrenovar.setForeground(Color.WHITE);
			btnInscribirrenovar.setBackground(Color.GREEN);
		}
		return btnInscribirrenovar;
	}

	private JPanel getPanelDatos() {
		if (panelDatos == null) {
			panelDatos = new JPanel();
			panelDatos.setBorder(new EmptyBorder(10, 0, 10, 0));
			panelDatos.setLayout(new GridLayout(0, 1, 0, 0));
			panelDatos.add(getPanelNombre());
			panelDatos.add(getPanelApellidos());
			panelDatos.add(getPanelNumCol());
			panelDatos.add(getPanelDni());
			panelDatos.add(getPanelFechaSol());
			panelDatos.add(getPanelTelefono());
			panelDatos.add(getPanelCuentaBancaria());
			panelDatos.add(getPanelPosListaP());
			getPanelPosListaP().setVisible(false);
		}
		return panelDatos;
	}
	private JPanel getPanelNombre() {
		if (panelNombre == null) {
			panelNombre = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panelNombre.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			panelNombre.setBorder(new EmptyBorder(0, 30, 0, 0));
			panelNombre.add(getLblNombre());
			panelNombre.add(getLblNombreData());
		}
		return panelNombre;
	}
	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre:");
			lblNombre.setHorizontalAlignment(SwingConstants.TRAILING);
			lblNombre.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		return lblNombre;
	}
	private JLabel getLblNombreData() {
		if (lblNombreData == null) {
			lblNombreData = new JLabel();
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
			lblApellidos.setHorizontalAlignment(SwingConstants.TRAILING);
			lblApellidos.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		return lblApellidos;
	}
	private JLabel getLblApellidosData() {
		if (lblApellidosData == null) {
			lblApellidosData = new JLabel();
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
			lblNumCol.setHorizontalAlignment(SwingConstants.TRAILING);
			lblNumCol.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		return lblNumCol;
	}
	private JLabel getLblNumColData() {
		if (lblNumColData == null) {
			lblNumColData = new JLabel();
			lblNumColData.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		return lblNumColData;
	}
	private JPanel getPanelFechaSol() {
		if (panelFechaSol == null) {
			panelFechaSol = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panelFechaSol.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			panelFechaSol.setBorder(new EmptyBorder(0, 30, 0, 0));
			panelFechaSol.add(getLblFechaSol());
			panelFechaSol.add(getLblFechaSolData());
		}
		return panelFechaSol;
	}
	private JLabel getLblFechaSol() {
		if (lblFechaSol == null) {
			lblFechaSol = new JLabel("Fecha de solicitud:");
			lblFechaSol.setHorizontalAlignment(SwingConstants.TRAILING);
			lblFechaSol.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		return lblFechaSol;
	}
	private JLabel getLblFechaSolData() {
		if (lblFechaSolData == null) {
			lblFechaSolData = new JLabel("2022-11-08");
			lblFechaSolData.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		return lblFechaSolData;
	}
	private JPanel getPanelDni() {
		if (panelDni == null) {
			panelDni = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panelDni.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			panelDni.setBorder(new EmptyBorder(0, 30, 0, 0));
			panelDni.add(getLblDni());
			panelDni.add(getLblDniData());
		}
		return panelDni;
	}
	private JLabel getLblDni() {
		if (lblDni == null) {
			lblDni = new JLabel("Dni:");
			lblDni.setHorizontalAlignment(SwingConstants.TRAILING);
			lblDni.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		return lblDni;
	}
	private JLabel getLblDniData() {
		if (lblDniData == null) {
			lblDniData = new JLabel();
			lblDniData.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		return lblDniData;
	}
	private JPanel getPanelTelefono() {
		if (panelTelefono == null) {
			panelTelefono = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panelTelefono.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			panelTelefono.setBorder(new EmptyBorder(0, 30, 0, 0));
			panelTelefono.add(getLblTelefono());
			panelTelefono.add(getLblTelefonoData());
		}
		return panelTelefono;
	}
	private JLabel getLblTelefono() {
		if (lblTelefono == null) {
			lblTelefono = new JLabel("Telefono:");
			lblTelefono.setHorizontalAlignment(SwingConstants.TRAILING);
			lblTelefono.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		return lblTelefono;
	}
	private JLabel getLblTelefonoData() {
		if (lblTelefonoData == null) {
			lblTelefonoData = new JLabel();
			lblTelefonoData.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		return lblTelefonoData;
	}
	private JPanel getPanelCuentaBancaria() {
		if (panelCuentaBancaria == null) {
			panelCuentaBancaria = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panelCuentaBancaria.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			panelCuentaBancaria.setBorder(new EmptyBorder(0, 30, 0, 0));
			panelCuentaBancaria.add(getLblCuentabancaria());
			panelCuentaBancaria.add(getLblCuentBancData());
		}
		return panelCuentaBancaria;
	}
	private JLabel getLblCuentabancaria() {
		if (lblCuentabancaria == null) {
			lblCuentabancaria = new JLabel("Cuenta bancaria:");
			lblCuentabancaria.setHorizontalAlignment(SwingConstants.TRAILING);
			lblCuentabancaria.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		return lblCuentabancaria;
	}
	private JLabel getLblCuentBancData() {
		if (lblCuentBancData == null) {
			lblCuentBancData = new JLabel();
			lblCuentBancData.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		return lblCuentBancData;
	}
	
	private void inscribeRenueva() {
		PeritoDTO p = PeritoDataBase.findByColegiadoId(colegiado.id_colegiado);
		if(p == null) {
			pm = new PeritoManager(colegiado.id_colegiado);
			p = pm.inicializarDatos();
			getLblFinalizarInfo().setText("Inscripcion de perito");
			opcion = INSCRIP;
			
		} else {
			pm = new PeritoManager(p);
			
			if(p.activo == false) {
				getLblFinalizarInfo().setText("Inscripcion de perito");
				opcion = RENOV_ANTIGUO;
			} else {
				getLblFinalizarInfo().setText("Renovacion de perito");
				opcion = RENOV;
			}			
		}
		
		getLblPosListaPData().setText("" + p.pos_Lista);
		
		getPanelPosListaP().setVisible(true);
		
		PanelSolicitud.remove(getPanelDatos());
		getPanelFinalizacion().add(getPanelDatos(), BorderLayout.CENTER);
						
		((CardLayout)getContentPane().getLayout() ).show(getContentPane(),"Finalizacion");
		
		validate();
		repaint();
	}

	private void mostrarDatos() {
		String id = getTextField().getText().trim();
		
		if(id.isBlank()) {
			getLblError().setText("Por favor, introduzca un id Valido");
			getLblNumColData().setText("");
			getLblDniData().setText("");
			getLblApellidosData().setText("");
			getLblNombreData().setText("");
			getLblCuentBancData().setText("");
			getLblTelefonoData().setText("");
			getBtnInscribirrenovar().setEnabled(false);
			return;
		}
		
		colegiado = ColegiadoDataBase.findById(id);
		
		comprobarColegiado();	
		
	}

	private void comprobarColegiado() {
		if(colegiado != null) {
			getLblError().setText("");
			
			getLblNumColData().setText(colegiado.id_colegiado);
			getLblDniData().setText(colegiado.dni);
			getLblApellidosData().setText(colegiado.apellidos);
			getLblNombreData().setText(colegiado.nombre);
			getLblCuentBancData().setText(colegiado.cuentaBancaria);
			getLblTelefonoData().setText(colegiado.tlfn);
			
			getBtnInscribirrenovar().setEnabled(true);
			
		} else {
			getLblError().setText("No se ha encontrado al colegiado");
			
			getLblNumColData().setText("");
			getLblDniData().setText("");
			getLblApellidosData().setText("");
			getLblNombreData().setText("");
			getLblCuentBancData().setText("");
			getLblTelefonoData().setText("");
			getBtnInscribirrenovar().setEnabled(false);
		}
	}
	
	private JButton getBtnBuscar() {
		if (btnBuscar == null) {
			btnBuscar = new JButton("Validar");
			btnBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mostrarDatos();
				}
			});
		}
		return btnBuscar;
	}
	private JPanel getPanelFinalizacion() {
		if (panelFinalizacion == null) {
			panelFinalizacion = new JPanel();
			panelFinalizacion.setLayout(new BorderLayout(0, 0));
			panelFinalizacion.add(getLblFinalizarInfo(), BorderLayout.NORTH);
			panelFinalizacion.add(getPanelFF(), BorderLayout.SOUTH);
		}
		return panelFinalizacion;
	}
	private JLabel getLblFinalizarInfo() {
		if (lblFinalizarInfo == null) {
			lblFinalizarInfo = new JLabel();
			lblFinalizarInfo.setBorder(new CompoundBorder(new EmptyBorder(10, 0, 5, 0), new LineBorder(new Color(0, 0, 0), 2, true)));
			lblFinalizarInfo.setHorizontalAlignment(SwingConstants.CENTER);
			lblFinalizarInfo.setFont(new Font("Arial", Font.BOLD, 21));
		}
		return lblFinalizarInfo;
	}
	private JPanel getPanelFF() {
		if (panelFF == null) {
			panelFF = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panelFF.getLayout();
			flowLayout.setHgap(10);
			flowLayout.setAlignment(FlowLayout.RIGHT);
			panelFF.add(getBtnBack());
			panelFF.add(getBtnFinalizar());
		}
		return panelFF;
	}
	private JButton getBtnFinalizar() {
		if (btnFinalizar == null) {
			btnFinalizar = new JButton("Finalizar");
			btnFinalizar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					finalizar();
					JOptionPane.showConfirmDialog(null, "Se le ha "+ TEXTO_CONFIRMACION[opcion] +" correctamente", "Confirmacion", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
			});
			btnFinalizar.setForeground(Color.WHITE);
			btnFinalizar.setBackground(Color.GREEN);
		}
		return btnFinalizar;
	}
	private JButton getBtnBack() {
		if (btnBack == null) {
			btnBack = new JButton("Atras");
			btnBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					back();
				}
			});
			btnBack.setForeground(Color.WHITE);
			btnBack.setBackground(Color.RED);
		}
		return btnBack;
	}

	private void back() {
		colegiado = null;
		comprobarColegiado();
		getLblError().setText("");
		getTextField().setText("");
		
		getPanelPosListaP().setVisible(false);
		
		getPanelFinalizacion().remove(getPanelDatos());
		PanelSolicitud.add(getPanelDatos(), BorderLayout.CENTER);
		
		((CardLayout)getContentPane().getLayout() ).show(getContentPane(),"solicitud");
		
		validate();
		repaint();
	}
	
	private JPanel getPanelPosListaP() {
		if (panelPosListaP == null) {
			panelPosListaP = new JPanel();
			FlowLayout flowLayout_1 = (FlowLayout) panelPosListaP.getLayout();
			flowLayout_1.setAlignment(FlowLayout.LEFT);
			FlowLayout flowLayout = (FlowLayout) panelCuentaBancaria.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			panelPosListaP.setBorder(new EmptyBorder(0, 30, 0, 0));
			panelPosListaP.add(getLblPosListaP());
			panelPosListaP.add(getLblPosListaPData());
		}
		return panelPosListaP;
	}
	
	private JLabel getLblPosListaP() {
		if (lblPosListaP == null) {
			lblPosListaP = new JLabel("Posicion en la lista pericial:");
			lblPosListaP.setHorizontalAlignment(SwingConstants.TRAILING);
			lblPosListaP.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		return lblPosListaP;
	}
	
	private JLabel getLblPosListaPData() {
		if (lblPosListaPData == null) {
			lblPosListaPData = new JLabel();
			lblPosListaPData.setHorizontalAlignment(SwingConstants.TRAILING);
			lblPosListaPData.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		return lblPosListaPData;
	}

	private void finalizar() {
		if(opcion==INSCRIP) {
			pm.inscribir();
			return;
		}else if(opcion==RENOV) {
			pm.renovar();
			return;
		} else if(opcion == RENOV_ANTIGUO) {
			pm.renovarAntiguo();
			return;
		}
		
	}
}
