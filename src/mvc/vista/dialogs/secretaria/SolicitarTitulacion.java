package mvc.vista.dialogs.secretaria;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import mvc.controlador.SolicitarTitulacionControler;
import mvc.vista.resources.secretaria.SolicitarTitulacionColegiado;

public class SolicitarTitulacion extends JDialog {

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
			SolicitarTitulacion dialog = new SolicitarTitulacion();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SolicitarTitulacion() {
		setModal(true);
		setTitle("Solicitador de titulaciones");
		setBounds(100, 100, 970, 495);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(new SolicitarTitulacionColegiado(new SolicitarTitulacionControler()));
	}

}
