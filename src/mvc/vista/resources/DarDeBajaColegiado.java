package mvc.vista.resources;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import mvc.vista.dialogs.DarDeBaja;
import mvc.controlador.DarDeBajaColegiadoControler;

public class DarDeBajaColegiado extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lbTitulo;
	private DarDeBajaColegiadoControler controler;
	private JLabel lbLastCancelado;
	private JButton btCancelar;
	private DarDeBaja dialog;
	private JLabel lbDni;
	private JTextField textField;
	private JButton btBaja;

	/**
	 * Create the panel.
	 */
	public DarDeBajaColegiado(DarDeBajaColegiadoControler controler,DarDeBaja dialog) {
		this.dialog = dialog;
		this.controler = controler;
		setLayout(null);
		add(getLbTitulo());
		add(getLbLastCancelado());
		add(getBtCancelar());
		add(getLbDni());
		add(getTextField());
		add(getBtBaja());

	}
	private JLabel getLbTitulo() {
		if (lbTitulo == null) {
			lbTitulo = new JLabel("Darse de baja");
			lbTitulo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			lbTitulo.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lbTitulo.setHorizontalAlignment(SwingConstants.CENTER);
			lbTitulo.setBounds(10, 10, 430, 47);
		}
		return lbTitulo;
	}
	private JLabel getLbLastCancelado() {
		if (lbLastCancelado == null) {
			lbLastCancelado = new JLabel("");
			lbLastCancelado.setBounds(10, 277, 430, 27);
		}
		return lbLastCancelado;
	}
	private JButton getBtCancelar() {
		if (btCancelar == null) {
			btCancelar = new JButton("Cancelar");
			btCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dialog.cerrar();
					controler.finalizar();
				}
			});
			btCancelar.setBackground(Color.RED);
			btCancelar.setBounds(355, 93, 85, 21);
		}
		return btCancelar;
	}
	private JLabel getLbDni() {
		if (lbDni == null) {
			lbDni = new JLabel("Introduzca su dni:");
			lbDni.setBounds(10, 67, 151, 13);
		}
		return lbDni;
	}
	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setBounds(164, 64, 276, 19);
			textField.setColumns(10);
		}
		return textField;
	}
	private JButton getBtBaja() {
		if (btBaja == null) {
			btBaja = new JButton("Darse de baja");
			btBaja.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					darDeBaja();
				}
			});
			btBaja.setBackground(Color.GREEN);
			btBaja.setBounds(215, 93, 129, 21);
		}
		return btBaja;
	}
	private void darDeBaja() {
		String colegiado = this.getTextField().getText();
		double amount = controler.getRecibosPendientes(colegiado);
		if(colegiado != null && controler.darDeBaja(colegiado)) {
			if(this.mostrarMensajeConfirmacion(amount)) {
				this.getBtBaja().setEnabled(false);
				controler.finalizar();
			}
			else {
				controler.cancelar();
			}
		}
		else {
			this.mostrarMensajeNoColegiado();
			controler.cancelar();
		}
	}
	
	private boolean mostrarMensajeConfirmacion(double amount) {
		return JOptionPane.showConfirmDialog(this,
				new String("Está a punto de darse de baja como colegiado. Con esta acción perderá los privilegios propios de un colegiado. Dejará de tener voz y voto en las juntas, dejará de tener acceso al título de perito"
						+ " y dejará de optar a cuotas exclusivas en nuestros cursos. Al darse de baja no será necesario pagar las cuotas del año siguiente, si teniendo que hacerlo con las del año en curso. Además deberá abonar el"
						+ " dinero que debe al colegio siendo este: " + amount + "€. ¿Está seguro?"),"Confirmacion baja",
				JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION;
	}
	
	private boolean mostrarMensajeNoColegiado() {
		return JOptionPane.showConfirmDialog(this,
				new String("No existe ningún colegiado con ese DNI asociado, revise la información introducida"),"Aviso no colegiado",
				JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION;
	}
}
