package abel.vista.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import abel.controlador.ConfigurarActividadControler;
import abel.vista.ConfigurarCurso;

public class ConfiguradorCurso extends JDialog {

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
			ConfiguradorCurso dialog = new ConfiguradorCurso("");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ConfiguradorCurso(String curso) {
		setModal(true);
		setUndecorated(true);
		setTitle("Condigurador de cursos");
		setBounds(100, 100, 754, 400);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(new ConfigurarCurso(new ConfigurarActividadControler(),curso,this));
	}

}