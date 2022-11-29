package mvc.vista.dialogs.secretaria;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import mvc.controlador.ActividadFormativaControler;
import mvc.vista.resources.secretaria.A�adirActividadFormativa;

public class PlanificadorCurso extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private ActividadFormativaControler controler = new ActividadFormativaControler();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			PlanificadorCurso dialog = new PlanificadorCurso();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public PlanificadorCurso() {
		setModal(true);
		setTitle("Planificador de cursos");
		setBounds(100, 100, 505, 335);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(new A�adirActividadFormativa(controler));
	}
	
	@Override
	public void dispose() {
		controler.cancelar();
		super.dispose();
	}

}