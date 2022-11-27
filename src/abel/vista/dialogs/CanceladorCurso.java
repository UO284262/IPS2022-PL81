package abel.vista.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import abel.controlador.CanceladorCursoControler;
import abel.vista.CancelarCursoFormativo;

public class CanceladorCurso extends JDialog {

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
			CanceladorCurso dialog = new CanceladorCurso();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CanceladorCurso() {
		setModal(true);
		setUndecorated(true);
		setTitle("Condigurador de cursos");
		setBounds(100, 100, 452, 346);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(new CancelarCursoFormativo(new CanceladorCursoControler(),this));
	}
	
	public void cerrar() {
		this.dispose();
	}

}