package main.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import abel.controlador.ActividadFormativaControler;
import abel.vista.AñadirActividadFormativa;

import java.awt.Font;

public class Main {

	private JFrame frame;
	private JPanel panelPrincipal;
	private JPanel panelSecretaria;
	private JPanel panelColegiado;
	private JPanel panelSecBotones;
	private JLabel lblSecretaria;
	private JLabel lblColegiado;
	private JPanel panelColBotones;
	private JPanel panel;

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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 560);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(getPanelPrincipal(), BorderLayout.CENTER);
	}

	private JPanel getPanelPrincipal() {
		if (panelPrincipal == null) {
			panelPrincipal = new JPanel();
			panelPrincipal.setLayout(new GridLayout(0, 2, 0, 0));
			panelPrincipal.add(getPanelSecretaria());
			panelPrincipal.add(getPanelColegiado());
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
		}
		return panelColBotones;
	}
}
