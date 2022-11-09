package main.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatLightLaf;

import abel.vista.dialogs.PlanificadorCurso;
import abel.vista.dialogs.SolicitarPericial;
import abel.vista.dialogs.SolicitarTitulacion;
import abel.vista.dialogs.VisualizadorInscritos;
import kike.gui.colegiado.PreInscribeColegiado;

import kike.gui.colegiado.RegistroPericial;
import kike.gui.secretaria.SelectorCurso;
import kike.modelo.curso.CursoDTO;
import kike.persistence.CursoDataBase;
import rodro.controlador.EmitirRecibosControler;
import rodro.controlador.SolicitudControler;
import rodro.vista.VentanaSolicitud;

import java.util.List;

public class Main {

	private JFrame frame;
	private JPanel panelPrincipal;
	private JPanel panelSecretaria;
	private JPanel panelColegiado;
	private JPanel panelSecBotones;
	private JLabel lblSecretaria;
	private JLabel lblColegiado;
	private JPanel panelColBotones;
	private JButton btnAbrirCurso;
	private JButton btAñadirCurso;
	private JButton btnInscribirColegiado;
	private JButton btVisualizarInscritos;
	private JButton btEmitirRecibos;
	private JButton btSolicitudColegiado;
	private JButton btnRIPerito;
	private JButton btSolicitarTitulacion;
	private JPanel panelExterno;
	private JLabel lbExterno;
	private JPanel panelExternoBotones;
	private JButton btPericial;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		setLookAndFeel();
		initialize();
	}
	
	private void setLookAndFeel()
	{
		UIManager.put( "Component.focusWidth", 1 );
		UIManager.put( "Button.arc", 10 );
		UIManager.put( "Component.arc", 999 );
		UIManager.put( "ProgressBar.arc", 999 );
		UIManager.put( "TextComponent.arc", 999 );
		UIManager.put("Component.arrowType", "chevron");
		
		FlatLightLaf.setup();
		try {
			UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarculaLaf");
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 560);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(getPanelPrincipal(), BorderLayout.CENTER);
		frame.setLocationRelativeTo(null);
	}

	private JPanel getPanelPrincipal() {
		if (panelPrincipal == null) {
			panelPrincipal = new JPanel();
			panelPrincipal.setLayout(new GridLayout(0, 2, 0, 0));
			panelPrincipal.add(getPanelSecretaria());
			panelPrincipal.add(getPanelColegiado());
			panelPrincipal.add(getPanelExterno());
		}
		return panelPrincipal;
	}
	private JPanel getPanelSecretaria() {
		if (panelSecretaria == null) {
			panelSecretaria = new JPanel();
			panelSecretaria.setLayout(new BorderLayout(0, 0));
			panelSecretaria.add(getPanelSecBotones(), BorderLayout.CENTER);
			panelSecretaria.add(getLblSecretaria(), BorderLayout.NORTH);
		}
		return panelSecretaria;
	}
	private JPanel getPanelColegiado() {
		if (panelColegiado == null) {
			panelColegiado = new JPanel();
			panelColegiado.setLayout(new BorderLayout(0, 0));
			panelColegiado.add(getLblColegiado(), BorderLayout.NORTH);
			panelColegiado.add(getPanelColBotones(), BorderLayout.CENTER);
		}
		return panelColegiado;
	}
	private JPanel getPanelSecBotones() {
		if (panelSecBotones == null) {
			panelSecBotones = new JPanel();
			panelSecBotones.setLayout(new GridLayout(0, 1, 5, 5));
			panelSecBotones.add(getBtAñadirCurso());
			panelSecBotones.add(getBtnAbrirCurso());
			panelSecBotones.add(getBtSolicitarTitulacion());
			panelSecBotones.add(getBtVisualizarInscritos());
			panelSecBotones.add(getBtEmitirRecibos());
		}
		return panelSecBotones;
	}
	private JLabel getLblSecretaria() {
		if (lblSecretaria == null) {
			lblSecretaria = new JLabel("Secretar\u00EDa");
			lblSecretaria.setFont(new Font("Arial", Font.BOLD, 23));
			lblSecretaria.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblSecretaria;
	}
	private JLabel getLblColegiado() {
		if (lblColegiado == null) {
			lblColegiado = new JLabel("Colegiado");
			lblColegiado.setHorizontalAlignment(SwingConstants.CENTER);
			lblColegiado.setFont(new Font("Arial", Font.BOLD, 23));
		}
		return lblColegiado;
	}
	private JPanel getPanelColBotones() {
		if (panelColBotones == null) {
			panelColBotones = new JPanel();
			panelColBotones.setLayout(new GridLayout(0, 1, 0, 0));
			panelColBotones.add(getBtSolicitudColegiado());
			panelColBotones.add(getBtnInscribirColegiado());
			panelColBotones.add(getBtnRIPerito());
		}
		return panelColBotones;
	}
	private JButton getBtnAbrirCurso() {
		if (btnAbrirCurso == null) {
			btnAbrirCurso = new JButton("AbrirCurso");
			btnAbrirCurso.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List<CursoDTO> cursosSinAbrir = CursoDataBase.getCursosSinAbrir();
					SelectorCurso dialog = new SelectorCurso(cursosSinAbrir);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setLocationRelativeTo(frame);
					dialog.setVisible(true);
				}				
			});
		}
		return btnAbrirCurso;
	}
	private JButton getBtAñadirCurso() {
		if (btAñadirCurso == null) {
			btAñadirCurso = new JButton("A\u00F1adirCurso");
			btAñadirCurso.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					PlanificadorCurso dialog = new PlanificadorCurso();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setLocationRelativeTo(frame);
					dialog.setVisible(true);
				}
			});
		}
		return btAñadirCurso;
	}

	private JButton getBtnInscribirColegiado() {
		if (btnInscribirColegiado == null) {
			btnInscribirColegiado = new JButton("Inscribirse a un curso");
			btnInscribirColegiado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					PreInscribeColegiado dialog = new PreInscribeColegiado();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setLocationRelativeTo(frame);
					dialog.setVisible(true);
				}
			});
		}
		return btnInscribirColegiado;
	}

	private JButton getBtVisualizarInscritos() {
		if (btVisualizarInscritos == null) {
			btVisualizarInscritos = new JButton("Visualizar Inscritos");
			btVisualizarInscritos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VisualizadorInscritos dialog = new VisualizadorInscritos();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setLocationRelativeTo(frame);
					dialog.setVisible(true);
				}
			});
		}
		return btVisualizarInscritos;

	}
	private JButton getBtEmitirRecibos() {
		if (btEmitirRecibos == null) {
			btEmitirRecibos = new JButton("Emitir Recibos");
			btEmitirRecibos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					enviarRecibos();
				}
			});
		}
		return btEmitirRecibos;
	}
	private void enviarRecibos() {
		EmitirRecibosControler con = new EmitirRecibosControler();
		if(con.validarRecibos())
			JOptionPane.showMessageDialog(null, "Se han enviado los recibos");
		else
			JOptionPane.showMessageDialog(null, "No se han podido enviar recibos ya que no hay disponibles");
	}
	private JButton getBtSolicitudColegiado() {
		if (btSolicitudColegiado == null) {
			btSolicitudColegiado = new JButton("SolicitudColegiado");
			btSolicitudColegiado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SolicitudControler con = new SolicitudControler();
					VentanaSolicitud dialog = new VentanaSolicitud(con);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setLocationRelativeTo(frame);
					dialog.setVisible(true);
				}
			});
		}
		return btSolicitudColegiado;
	}

	private JButton getBtnRIPerito() {
		if (btnRIPerito == null) {
			btnRIPerito = new JButton("Renovacion/inscripcion peritos");
			btnRIPerito.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					RegistroPericial dialog = new RegistroPericial();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setLocationRelativeTo(frame);
					dialog.setVisible(true);
				}
			});
		}
		return btnRIPerito;
	}

	private JButton getBtSolicitarTitulacion() {
		if (btSolicitarTitulacion == null) {
			btSolicitarTitulacion = new JButton("Solicitar titulacion");
			btSolicitarTitulacion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SolicitarTitulacion dialog = new SolicitarTitulacion();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setLocationRelativeTo(frame);
					dialog.setVisible(true);
				}
			});
		}
		return btSolicitarTitulacion;
	}
	private JPanel getPanelExterno() {
		if (panelExterno == null) {
			panelExterno = new JPanel();
			panelExterno.setLayout(new BorderLayout(0, 0));
			panelExterno.add(getLbExterno(), BorderLayout.NORTH);
			panelExterno.add(getPanelExternoBotones(), BorderLayout.CENTER);
		}
		return panelExterno;
	}
	private JLabel getLbExterno() {
		if (lbExterno == null) {
			lbExterno = new JLabel("Externo");
			lbExterno.setHorizontalAlignment(SwingConstants.CENTER);
			lbExterno.setFont(new Font("Arial", Font.BOLD, 23));
		}
		return lbExterno;
	}
	private JPanel getPanelExternoBotones() {
		if (panelExternoBotones == null) {
			panelExternoBotones = new JPanel();
			panelExternoBotones.setLayout(new GridLayout(0, 1, 0, 0));
			panelExternoBotones.add(getBtPericial());
		}
		return panelExternoBotones;
	}
	private JButton getBtPericial() {
		if (btPericial == null) {
			btPericial = new JButton("Solicitar Pericial");
			btPericial.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SolicitarPericial dialog = new SolicitarPericial();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setLocationRelativeTo(frame);
					dialog.setVisible(true);
				}
			});
		}
		return btPericial;
	}
}
