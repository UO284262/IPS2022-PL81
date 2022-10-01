package abel.vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JMonthChooser;

import abel.controlador.ActividadFormativaControler;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class A�adirActividadFormativa extends JPanel {
	
	private ActividadFormativaControler controler;
	
	private JLabel lblNewLabel;
	private JButton btCancelar;
	private JButton btA�adir;
	private JLabel lbNombre;
	private JTextField tfNombre;
	private JLabel lbPrecio;
	private JTextField tfPrecio;
	private JDayChooser dayChooser;
	private JMonthChooser monthChooser;
	private JButton btA�adirDia;
	private JLabel lbAvisoDia1;
	private JLabel lbMensajeConfirmacion;
	private JLabel lbMensajeDenegar;

	/**
	 * Create the panel.
	 */
	public A�adirActividadFormativa(ActividadFormativaControler controler) {
		this.controler = controler;
		setLayout(null);
		add(getLblNewLabel());
		add(getBtCancelar());
		add(getBtA�adir());
		add(getLbNombre());
		add(getTfNombre());
		add(getLbPrecio());
		add(getTfPrecio());
		add(getDayChooser());
		add(getLbAvisoDia1());
		add(getLbMensajeConfirmacion());
		add(getLbMensajeDenegar());

	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("A\u00F1adir actividades formativas");
			lblNewLabel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			lblNewLabel.setBackground(Color.WHITE);
			lblNewLabel.setBounds(10, 10, 430, 36);
		}
		return lblNewLabel;
	}
	private JButton getBtCancelar() {
		if (btCancelar == null) {
			btCancelar = new JButton("Cancelar");
			btCancelar.setBounds(355, 269, 85, 21);
		}
		return btCancelar;
	}
	private JButton getBtA�adir() {
		if (btA�adir == null) {
			btA�adir = new JButton("A\u00F1adir");
			btA�adir.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					getLbMensajeConfirmacion().setVisible(false);
					getLbMensajeDenegar().setVisible(false);
				}
			});
			btA�adir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					validarActividad();
				}
			});
			btA�adir.setBounds(260, 269, 85, 21);
		}
		return btA�adir;
	}
	private JLabel getLbNombre() {
		if (lbNombre == null) {
			lbNombre = new JLabel("Nombre:");
			lbNombre.setBounds(10, 56, 99, 28);
		}
		return lbNombre;
	}
	private JTextField getTfNombre() {
		if (tfNombre == null) {
			tfNombre = new JTextField();
			tfNombre.setText("Nombre de la actividad formativa");
			tfNombre.setBounds(85, 61, 163, 19);
			tfNombre.setColumns(10);
		}
		return tfNombre;
	}
	private JLabel getLbPrecio() {
		if (lbPrecio == null) {
			lbPrecio = new JLabel("Precio:");
			lbPrecio.setBounds(10, 106, 45, 13);
		}
		return lbPrecio;
	}
	private JTextField getTfPrecio() {
		if (tfPrecio == null) {
			tfPrecio = new JTextField();
			tfPrecio.setText("Precio en n\u00FAmeros");
			tfPrecio.setBounds(85, 103, 99, 19);
			tfPrecio.setColumns(10);
		}
		return tfPrecio;
	}
	private JDayChooser getDayChooser() {
		if (dayChooser == null) {
			dayChooser = new JDayChooser();
			dayChooser.getDayPanel().addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					getLbAvisoDia1().setVisible(false);
				}
			});
			dayChooser.setBounds(258, 61, 182, 133);
			dayChooser.add(getMonthChooser(), BorderLayout.NORTH);
		}
		return dayChooser;
	}
	private JMonthChooser getMonthChooser() {
		if (monthChooser == null) {
			monthChooser.setLayout(new BorderLayout(0, 0));
			monthChooser = new JMonthChooser();
			monthChooser.getComboBox().addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					getLbAvisoDia1().setVisible(false);
				}
			});
			monthChooser.add(getBtA�adirDia(), BorderLayout.EAST);
		}
		return monthChooser;
	}
	
	private void validarActividad()
	{
		if(controler.validarActividad(this.getTfNombre().getText(), this.getTfPrecio().getText()))
		{
			this.getDayChooser().resetKeyboardActions();
			this.getMonthChooser().resetKeyboardActions();
			this.getLbMensajeConfirmacion().setVisible(true);
		}
		else
		{
			this.getLbMensajeDenegar().setVisible(true);
		}
	}
	
	private void a�adirDia()
	{
		if(!controler.a�adirDia(this.getDayChooser().getDay(), this.getMonthChooser().getMonth()))
		{
			getLbAvisoDia1().setVisible(true);
		}
	}
	
	private JButton getBtA�adirDia() {
		if (btA�adirDia == null) {
			btA�adirDia = new JButton("A\u00F1adir d\u00EDa");
			btA�adirDia.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					a�adirDia();
				}
			});
			btA�adirDia.setFont(new Font("Tahoma", Font.PLAIN, 10));
		}
		return btA�adirDia;
	}
	private JLabel getLbAvisoDia1() {
		if (lbAvisoDia1 == null) {
			lbAvisoDia1 = new JLabel("Ese d\u00EDa ya ha sido a\u00F1adido");
			lbAvisoDia1.setVisible(false);
			lbAvisoDia1.setForeground(Color.RED);
			lbAvisoDia1.setBounds(260, 204, 180, 13);
		}
		return lbAvisoDia1;
	}
	private JLabel getLbMensajeConfirmacion() {
		if (lbMensajeConfirmacion == null) {
			lbMensajeConfirmacion = new JLabel("Actividad a\u00F1adida correctamente");
			lbMensajeConfirmacion.setVisible(false);
			lbMensajeConfirmacion.setBounds(10, 273, 193, 13);
		}
		return lbMensajeConfirmacion;
	}
	private JLabel getLbMensajeDenegar() {
		if (lbMensajeDenegar == null) {
			lbMensajeDenegar = new JLabel("Actividad no a\u00F1adida");
			lbMensajeDenegar.setVisible(false);
			lbMensajeDenegar.setForeground(Color.RED);
			lbMensajeDenegar.setBounds(10, 250, 145, 13);
		}
		return lbMensajeDenegar;
	}
}
