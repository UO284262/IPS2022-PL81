package abel.vista.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import abel.controlador.ComprobarTitulacionControler;
import abel.vista.ComprobarTitulacionColegiado;

public class ComprobadorTitulacion extends JDialog {

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
			ComprobadorTitulacion dialog = new ComprobadorTitulacion();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ComprobadorTitulacion() {
		setModal(true);
		setTitle("Solicitador de titulaciones");
		setBounds(100, 100, 704, 459);
		setResizable(false);
		setUndecorated(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(new ComprobarTitulacionColegiado(new ComprobarTitulacionControler(),this));
	}

}
