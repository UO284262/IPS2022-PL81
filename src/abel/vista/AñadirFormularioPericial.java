package abel.vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.border.BevelBorder;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextField;

public class AñadirFormularioPericial extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lbAñadirFormulario;
	private JRadioButton rbtUrgente;
	private JLabel lbNombre;
	private JLabel lbTelefono;
	private JTextArea taDescripcion;
	private JLabel lbDescripcion;
	private JButton btSolicitar;
	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JLabel lbMail;
	private JTextField textField;
	private JButton btCancelar;

	/**
	 * Create the panel.
	 */
	public AñadirFormularioPericial() {
		setLayout(null);
		add(getLbAñadirFormulario());
		add(getRbtUrgente());
		add(getLbNombre());
		add(getLbTelefono());
		add(getTaDescripcion());
		add(getLbDescripcion());
		add(getBtSolicitar());
		add(getTxtNombre());
		add(getTxtTelefono());
		add(getLbMail());
		add(getTextField());
		add(getBtCancelar());

	}
	private JLabel getLbAñadirFormulario() {
		if (lbAñadirFormulario == null) {
			lbAñadirFormulario = new JLabel("Solicitud de actividad pericial");
			lbAñadirFormulario.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			lbAñadirFormulario.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lbAñadirFormulario.setHorizontalAlignment(SwingConstants.CENTER);
			lbAñadirFormulario.setBounds(10, 10, 324, 47);
		}
		return lbAñadirFormulario;
	}
	private JRadioButton getRbtUrgente() {
		if (rbtUrgente == null) {
			rbtUrgente = new JRadioButton("Urgente");
			rbtUrgente.setBounds(10, 429, 103, 21);
		}
		return rbtUrgente;
	}
	private JLabel getLbNombre() {
		if (lbNombre == null) {
			lbNombre = new JLabel("Nombre solicitante:");
			lbNombre.setBounds(10, 80, 122, 13);
		}
		return lbNombre;
	}
	private JLabel getLbTelefono() {
		if (lbTelefono == null) {
			lbTelefono = new JLabel("Telefono solicitante:");
			lbTelefono.setBounds(10, 109, 103, 13);
		}
		return lbTelefono;
	}
	private JTextArea getTaDescripcion() {
		if (taDescripcion == null) {
			taDescripcion = new JTextArea();
			taDescripcion.setBounds(10, 244, 324, 154);
		}
		return taDescripcion;
	}
	private JLabel getLbDescripcion() {
		if (lbDescripcion == null) {
			lbDescripcion = new JLabel("Describa brevemente que necesita (menos de 100 caracteres)");
			lbDescripcion.setBounds(10, 221, 293, 13);
		}
		return lbDescripcion;
	}
	private JButton getBtSolicitar() {
		if (btSolicitar == null) {
			btSolicitar = new JButton("Solicitar");
			btSolicitar.setBounds(249, 429, 85, 21);
		}
		return btSolicitar;
	}
	private JTextField getTxtNombre() {
		if (txtNombre == null) {
			txtNombre = new JTextField();
			txtNombre.setBounds(111, 77, 223, 19);
			txtNombre.setColumns(10);
		}
		return txtNombre;
	}
	private JTextField getTxtTelefono() {
		if (txtTelefono == null) {
			txtTelefono = new JTextField();
			txtTelefono.setBounds(111, 106, 223, 19);
			txtTelefono.setColumns(10);
		}
		return txtTelefono;
	}
	private JLabel getLbMail() {
		if (lbMail == null) {
			lbMail = new JLabel("Mail solicitante:");
			lbMail.setBounds(10, 144, 103, 13);
		}
		return lbMail;
	}
	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setBounds(111, 141, 223, 19);
			textField.setColumns(10);
		}
		return textField;
	}
	private JButton getBtCancelar() {
		if (btCancelar == null) {
			btCancelar = new JButton("Cancelar");
			btCancelar.setBounds(154, 429, 85, 21);
		}
		return btCancelar;
	}
}
