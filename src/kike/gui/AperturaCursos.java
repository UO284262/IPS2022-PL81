package kike.gui;

import javax.swing.JPanel;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JSpinner;

public class AperturaCursos extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel panelPrincipal;
	private JPanel panelFechas;
	private JLabel lblChooseInitDate;
	private JDateChooser FechaInicio;
	private JLabel lblChooseEndDate;
	private JDateChooser FechaFin;
	private JPanel panelPlazar;
	private JPanel panelTitulo;
	private JLabel lblNombreCurso;
	private JLabel lblChoosePlazas;
	private JSpinner spinner;
	private JPanel panelFinalizar;

	/**
	 * Create the panel.
	 */
	public AperturaCursos() {
		setBorder(new EmptyBorder(20, 20, 20, 20));
		setLayout(new BorderLayout(0, 0));
		add(getPanelPrincipal(), BorderLayout.CENTER);
		add(getPanelTitulo(), BorderLayout.NORTH);
		add(getPanelFinalizar(), BorderLayout.SOUTH);

	}
	private JPanel getPanelPrincipal() {
		if (panelPrincipal == null) {
			panelPrincipal = new JPanel();
			panelPrincipal.setBorder(new EmptyBorder(30, 30, 30, 30));
			panelPrincipal.setLayout(new GridLayout(0, 1, 0, 15));
			panelPrincipal.add(getPanelFechas_1());
			panelPrincipal.add(getPanelPlazar());
		}
		return panelPrincipal;
	}
	private JPanel getPanelFechas_1() {
		if (panelFechas == null) {
			panelFechas = new JPanel();
			panelFechas.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0), 3, true), new EmptyBorder(20, 10, 20, 10)));
			panelFechas.setLayout(new GridLayout(0, 2, 0, 20));
			panelFechas.add(getLblChooseInitDate_1());
			panelFechas.add(getFechaInicio_1());
			panelFechas.add(getLblChooseEndDate_1());
			panelFechas.add(getFechaFin_1());
		}
		return panelFechas;
	}
	private JLabel getLblChooseInitDate_1() {
		if (lblChooseInitDate == null) {
			lblChooseInitDate = new JLabel("Fecha inicial para la inscipci\u00F3n:");
			lblChooseInitDate.setHorizontalAlignment(SwingConstants.CENTER);
			lblChooseInitDate.setFont(new Font("Arial", Font.PLAIN, 12));
		}
		return lblChooseInitDate;
	}
	private JDateChooser getFechaInicio_1() {
		if (FechaInicio == null) {
			FechaInicio = new JDateChooser();
		}
		return FechaInicio;
	}
	private JLabel getLblChooseEndDate_1() {
		if (lblChooseEndDate == null) {
			lblChooseEndDate = new JLabel("Fecha tope para la inscripci\u00F3n:");
			lblChooseEndDate.setHorizontalAlignment(SwingConstants.CENTER);
			lblChooseEndDate.setFont(new Font("Arial", Font.PLAIN, 12));
		}
		return lblChooseEndDate;
	}
	private JDateChooser getFechaFin_1() {
		if (FechaFin == null) {
			FechaFin = new JDateChooser();
		}
		return FechaFin;
	}
	private JPanel getPanelPlazar() {
		if (panelPlazar == null) {
			panelPlazar = new JPanel();
			panelPlazar.setLayout(null);
			panelPlazar.add(getLblChooseEndDate_1_1());
			panelPlazar.add(getSpinner());
		}
		return panelPlazar;
	}
	private JPanel getPanelTitulo() {
		if (panelTitulo == null) {
			panelTitulo = new JPanel();
			panelTitulo.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0), 2, true), new EmptyBorder(5, 5, 5, 5)));
			panelTitulo.setLayout(new GridLayout(1, 0, 0, 0));
			panelTitulo.add(getLblNombreCurso());
		}
		return panelTitulo;
	}
	private JLabel getLblNombreCurso() {
		if (lblNombreCurso == null) {
			lblNombreCurso = new JLabel("a");
			lblNombreCurso.setHorizontalTextPosition(SwingConstants.CENTER);
			lblNombreCurso.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblNombreCurso;
	}
	private JLabel getLblChooseEndDate_1_1() {
		if (lblChoosePlazas == null) {
			lblChoosePlazas = new JLabel("Numero de plazas:");
			lblChoosePlazas.setHorizontalTextPosition(SwingConstants.RIGHT);
			lblChoosePlazas.setBounds(79, 11, 111, 27);
			lblChoosePlazas.setHorizontalAlignment(SwingConstants.RIGHT);
			lblChoosePlazas.setFont(new Font("Arial", Font.PLAIN, 12));
		}
		return lblChoosePlazas;
	}
	private JSpinner getSpinner() {
		if (spinner == null) {
			spinner = new JSpinner();
			spinner.setBounds(201, 11, 102, 27);
		}
		return spinner;
	}
	private JPanel getPanelFinalizar() {
		if (panelFinalizar == null) {
			panelFinalizar = new JPanel();
		}
		return panelFinalizar;
	}
}