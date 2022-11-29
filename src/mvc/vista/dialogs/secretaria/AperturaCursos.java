package mvc.vista.dialogs.secretaria;

import javax.swing.JPanel;
import com.toedter.calendar.JDateChooser;

import mvc.modelo.curso.CursoManager;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.awt.event.ActionEvent;
import javax.swing.SpinnerNumberModel;

public class AperturaCursos extends JDialog {
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
	private JButton btnFinalizar;
	private JButton btnCancelar;
	
	private SelectorCurso sc;
	private JLabel lblErrores;

	/**
	 * Create the panel.
	 */
	public AperturaCursos(SelectorCurso sc) {
		setModal(true);
		this.sc = sc;
		
		setResizable(false);
		setUndecorated(true);
				
		setBounds(100, 100, 500, 450);
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getPanelPrincipal(), BorderLayout.CENTER);
		getContentPane().add(getPanelTitulo(), BorderLayout.NORTH);
		getContentPane().add(getPanelFinalizar(), BorderLayout.SOUTH);

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
			panelPlazar.add(getLblErrores());
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
			lblNombreCurso = new JLabel();
			lblNombreCurso.setText(sc.getCurso().title);
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
			spinner.setModel(new SpinnerNumberModel(1, 1, null, 1));
			spinner.setBounds(201, 11, 102, 27);
		}
		return spinner;
	}
	private JPanel getPanelFinalizar() {
		if (panelFinalizar == null) {
			panelFinalizar = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panelFinalizar.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			panelFinalizar.add(getBtnCancelar());
			panelFinalizar.add(getBtnFinalizar());
		}
		return panelFinalizar;
	}
	private JButton getBtnFinalizar() {
		if (btnFinalizar == null) {
			btnFinalizar = new JButton("Finalizar");
			btnFinalizar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(fechasValidads()) {
						abrirCurso();
						sc.dispose();
						dispose();
					}
				}
			});
			btnFinalizar.setForeground(Color.WHITE);
			btnFinalizar.setBackground(Color.GREEN);
		}
		return btnFinalizar;
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
	
	private boolean fechasValidads() {
		if(getFechaInicio_1().getDate() == null || getFechaFin_1().getDate() == null) {
			
			getLblErrores().setText("Por favor, introduzca una fecha de inicio y otra de fin");
			return false;
		}
		
		Date inicio = new Date(getFechaInicio_1().getDate().getTime());
		Date fin = new Date(getFechaFin_1().getDate().getTime());
		
		if(fin.compareTo(CursoManager.getPrimerDiaCurso(sc.getCurso())) > 0) {
			getLblErrores().setText("<html>Por favor, introduzca una fecha de fin anterior <br> a la del primer día del curso</html>");
			return false;
		}
		
		if(inicio.compareTo(fin) < 0 && inicio.compareTo(new Date(System.currentTimeMillis())) >= 0) {
			getLblErrores().setText("");
			return true;
		}
		
		getLblErrores().setText("<html>Las fechas han de ser posteriores al día actual. <br>La fecha de fin ha de ser posterior o igual a la de inicio.</html>");
		return false;
	}

	private void abrirCurso() {
		sc.getCurso().plazasDisponibles = (int) getSpinner().getValue();
		sc.getCurso().fechaInicioInscipcion = new Date(getFechaInicio_1().getDate().getTime());
		sc.getCurso().fechaFinInscipcion = new Date(getFechaFin_1().getDate().getTime());
		
		new CursoManager(sc.getCurso()).abrirCurso();
		
		JOptionPane.showConfirmDialog(null, "Se ha abierto el curso correctamente", "Confirmacion", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);

	}
	private JLabel getLblErrores() {
		if (lblErrores == null) {
			lblErrores = new JLabel("");
			lblErrores.setHorizontalAlignment(SwingConstants.CENTER);
			lblErrores.setForeground(Color.RED);
			lblErrores.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblErrores.setBounds(10, 73, 420, 73);
		}
		return lblErrores;
	}
}
