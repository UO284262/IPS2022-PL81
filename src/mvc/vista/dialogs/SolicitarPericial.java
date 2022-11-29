package mvc.vista.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import mvc.controlador.FormularioPericialControler;
import mvc.vista.resources.AñadirFormularioPericial;

public class SolicitarPericial extends JDialog {

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
			SolicitarPericial dialog = new SolicitarPericial();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SolicitarPericial() {
		setModal(true);
		setTitle("Solicitador de periciales");
		setBounds(100, 100, 450, 500);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(new AñadirFormularioPericial(new FormularioPericialControler()));
	}

}