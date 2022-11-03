package abel.vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.border.BevelBorder;

import abel.controlador.FormularioPericialControler;

import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
	private JTextField tfMail;
	private JButton btCancelar;
	private FormularioPericialControler controler;

	/**
	 * Create the panel.
	 */
	public AñadirFormularioPericial(FormularioPericialControler controler) {
		this.controler = controler;
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
		add(getTfMail());
		add(getBtCancelar());

	}
	private JLabel getLbAñadirFormulario() {
		if (lbAñadirFormulario == null) {
			lbAñadirFormulario = new JLabel("Solicitud de actividad pericial");
			lbAñadirFormulario.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			lbAñadirFormulario.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lbAñadirFormulario.setHorizontalAlignment(SwingConstants.CENTER);
			lbAñadirFormulario.setBounds(10, 10, 418, 47);
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
			lbNombre.setBounds(10, 80, 139, 13);
		}
		return lbNombre;
	}
	private JLabel getLbTelefono() {
		if (lbTelefono == null) {
			lbTelefono = new JLabel("Telefono solicitante:");
			lbTelefono.setBounds(10, 109, 139, 13);
		}
		return lbTelefono;
	}
	private JTextArea getTaDescripcion() {
		if (taDescripcion == null) {
			taDescripcion = new JTextArea();
			taDescripcion.setFont(new Font("Monospaced", Font.PLAIN, 15));
			taDescripcion.setLineWrap(true);
			taDescripcion.setBounds(10, 244, 418, 154);
		}
		return taDescripcion;
	}
	private JLabel getLbDescripcion() {
		if (lbDescripcion == null) {
			lbDescripcion = new JLabel("Describa brevemente que necesita (menos de 100 caracteres)");
			lbDescripcion.setBounds(10, 221, 418, 13);
		}
		return lbDescripcion;
	}
	private JButton getBtSolicitar() {
		if (btSolicitar == null) {
			btSolicitar = new JButton("Solicitar");
			btSolicitar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(comprobarCampos()) añadirFormulario();
				}
			});
			btSolicitar.setBackground(Color.GREEN);
			btSolicitar.setBounds(343, 429, 85, 21);
		}
		return btSolicitar;
	}
	
	private boolean comprobarCampos()
	{
		if(this.getTaDescripcion().getText().isBlank() || this.getTfMail().getText().isBlank()
				|| this.getTxtTelefono().getText().isBlank() || this.getTxtNombre().getText().isBlank())
		{
			JOptionPane.showConfirmDialog(this,new String("Todos los campos deben de ser rellenados."),
					"Aviso campos vacios",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
			return false;
		}
		return true;
	}
	
	private void añadirFormulario()
	{
		String mail = this.getTfMail().getText();
		String telefono = this.getTxtTelefono().getText();
		String descripcion = this.getTaDescripcion().getText();
		String nombre = this.getTxtNombre().getText();
		String prioridad = this.getRbtUrgente().isSelected() ? "URGENTE" : "NORMAL";
		int numero = controler.añadirFormulario(descripcion, nombre, telefono, mail, prioridad);
		if(numero < 0)
		{
			JOptionPane.showConfirmDialog(this,new String("Revise el formato de los campos."),"Aviso campos incorrectos",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
			mostrarFormatoCampos();
		}
		else
		{
			JOptionPane.showConfirmDialog(this,new String("Su solicitud se ha registrado y se le ha asociado el número " + numero + ". Gracias."),"Confirmar solicitud",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
			resetCampos();
		}
	
	}
	
	private void resetCampos()
	{
		this.getTfMail().setText("");
		this.getTxtTelefono().setText("");
		this.getTaDescripcion().setText("");
		this.getTxtNombre().setText("");
		this.getRbtUrgente().setSelected(false);
	}
	
	private void mostrarFormatoCampos()
	{
		this.getTfMail().setText("ejemplo@formato.com");
		this.getTxtTelefono().setText("000000000");
	}
	
	private JTextField getTxtNombre() {
		if (txtNombre == null) {
			txtNombre = new JTextField();
			txtNombre.setBounds(159, 77, 269, 19);
			txtNombre.setColumns(10);
		}
		return txtNombre;
	}
	private JTextField getTxtTelefono() {
		if (txtTelefono == null) {
			txtTelefono = new JTextField();
			txtTelefono.setBounds(159, 106, 269, 19);
			txtTelefono.setColumns(10);
		}
		return txtTelefono;
	}
	private JLabel getLbMail() {
		if (lbMail == null) {
			lbMail = new JLabel("Mail solicitante:");
			lbMail.setBounds(10, 144, 139, 13);
		}
		return lbMail;
	}
	private JTextField getTfMail() {
		if (tfMail == null) {
			tfMail = new JTextField();
			tfMail.setBounds(159, 141, 269, 19);
			tfMail.setColumns(10);
		}
		return tfMail;
	}
	private JButton getBtCancelar() {
		if (btCancelar == null) {
			btCancelar = new JButton("Cancelar");
			btCancelar.setBackground(Color.RED);
			btCancelar.setBounds(248, 429, 85, 21);
		}
		return btCancelar;
	}
}
