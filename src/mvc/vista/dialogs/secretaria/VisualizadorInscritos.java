package mvc.vista.dialogs.secretaria;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import mvc.controlador.VisualizarInscritosCursoControler;
import mvc.vista.resources.secretaria.VisualizarInscritosCurso;

public class VisualizadorInscritos extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

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
	public VisualizadorInscritos() {
		setModal(true);
		setTitle("Visualizador de inscritos");
		setBounds(100, 100, 1000, 446);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(new VisualizarInscritosCurso(new VisualizarInscritosCursoControler()));
	}

}
