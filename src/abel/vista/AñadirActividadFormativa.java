package abel.vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.border.BevelBorder;

import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JMonthChooser;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import abel.controlador.ActividadFormativaControler;
import abel.vista.dialogs.ConfiguradorCurso;
import exception.TypeConvertException;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

public class A�adirActividadFormativa extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ActividadFormativaControler controler;
	
	private JLabel lbTitulo;
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
	private JList<String> ltDays;
	private JLabel lbDiasA�adidos;
	private DefaultListModel<String> modeloDias = new DefaultListModel<String>();
	private JLabel lbException;
	private JLabel lbEuros;
	private JScrollPane scrollPane;
	private JButton btConfigurar;

	/**
	 * Create the panel.
	 */
	public A�adirActividadFormativa(ActividadFormativaControler controler) {
		this.controler = controler;
		setLayout(null);
		add(getLbTitulo());
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
		add(getLbDiasA�adidos());
		add(getLbException());
		add(getLbEuros());
		add(getScrollPane());
		add(getBtConfigurar());

	}
	private JLabel getLbTitulo() {
		if (lbTitulo == null) {
			lbTitulo = new JLabel("A\u00F1adir actividades formativas");
			lbTitulo.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lbTitulo.setHorizontalAlignment(SwingConstants.CENTER);
			lbTitulo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			lbTitulo.setBackground(Color.WHITE);
			lbTitulo.setBounds(10, 10, 470, 36);
		}
		return lbTitulo;
	}
	private JButton getBtCancelar() {
		if (btCancelar == null) {
			btCancelar = new JButton("Reiniciar");
			btCancelar.setFocusPainted(false);
			btCancelar.setBackground(Color.RED);
			btCancelar.setForeground(Color.BLACK);
			btCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					resetearCampos();
					getBtA�adir().setEnabled(false);
					getBtConfigurar().setEnabled(true);
				}
			});
			btCancelar.setBounds(395, 269, 85, 21);
		}
		return btCancelar;
	}
	
	public void resetearCampos()
	{
		this.getTfNombre().setText("");
		this.getTfPrecio().setText("");
		this.getLbAvisoDia1().setVisible(false);
		modeloDias = new DefaultListModel<String>();
		this.getLtDays().setModel(modeloDias);
		this.getLbException().setVisible(false);
		this.getLbException().setText("");
		this.getLbMensajeDenegar().setVisible(false);
		controler.resetear();
	}
	
	private JButton getBtA�adir() {
		if (btA�adir == null) {
			btA�adir = new JButton("A\u00F1adir");
			btA�adir.setEnabled(false);
			btA�adir.setForeground(Color.BLACK);
			btA�adir.setFocusPainted(false);
			btA�adir.setBackground(Color.GREEN);
			btA�adir.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					getLbMensajeConfirmacion().setVisible(false);
					getLbMensajeDenegar().setVisible(false);
				}
			});
			btA�adir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controler.finalizar();
					getLbMensajeConfirmacion().setText("A�adido correctamente");
					resetearCampos();
					getBtA�adir().setEnabled(false);
					getBtConfigurar().setEnabled(true);
				}
			});
			btA�adir.setBounds(300, 269, 85, 21);
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
			tfNombre.setBounds(66, 61, 186, 19);
			tfNombre.setColumns(10);
		}
		return tfNombre;
	}
	private JLabel getLbPrecio() {
		if (lbPrecio == null) {
			lbPrecio = new JLabel("Precio:");
			lbPrecio.setBounds(10, 97, 45, 13);
		}
		return lbPrecio;
	}
	private JTextField getTfPrecio() {
		if (tfPrecio == null) {
			tfPrecio = new JTextField();
			tfPrecio.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					getLbException().setVisible(false);
					getLbException().setText("");
				}
			});
			tfPrecio.setBounds(66, 94, 60, 19);
			tfPrecio.setColumns(10);
		}
		return tfPrecio;
	}
	private JDayChooser getDayChooser() {
		if (dayChooser == null) {
			dayChooser = new JDayChooser();
			dayChooser.setBounds(258, 61, 222, 156);
			dayChooser.add(getMonthChooser(), BorderLayout.NORTH);
		}
		return dayChooser;
	}
	private JMonthChooser getMonthChooser() {
		if (monthChooser == null) {
			monthChooser = new JMonthChooser();
			monthChooser.getComboBox().addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					getLbAvisoDia1().setVisible(false);
				}
			});
			monthChooser.add(getBtA�adirDia(), BorderLayout.CENTER);
		}
		return monthChooser;
	}
	
	private boolean validarActividad()
	{
		try {
			return controler.validarActividad(this.getTfNombre().getText(), this.getTfPrecio().getText());
		}
		catch(TypeConvertException tfe)
		{
			this.getLbException().setText(tfe.getMessage());;
			this.getLbException().setVisible(true);
		}
		return false;
	}
	
	private void a�adirDia()
	{
		String c = controler.a�adirDia(this.getDayChooser().getDay(), this.getMonthChooser().getMonth());
		if(c == null)
		{
			getLbAvisoDia1().setVisible(true);
		}
		else
		{
			getLbAvisoDia1().setVisible(false);
			modeloDias.addElement(c);
		}
	}
	
	private JButton getBtA�adirDia() {
		if (btA�adirDia == null) {
			btA�adirDia = new JButton("A\u00F1adir d\u00EDa");
			btA�adirDia.setFocusPainted(false);
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
			lbAvisoDia1.setBounds(258, 227, 180, 13);
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
			lbMensajeDenegar = new JLabel("Actividad no a\u00F1adida. Compruebe los datos.");
			lbMensajeDenegar.setVisible(false);
			lbMensajeDenegar.setForeground(Color.RED);
			lbMensajeDenegar.setBounds(10, 250, 310, 13);
		}
		return lbMensajeDenegar;
	}
	private JList<String> getLtDays() {
		if (ltDays == null) {
			ltDays = new JList<String>();
			ltDays.setFocusable(false);
			ltDays.setVisibleRowCount(4);
			ltDays.setModel(modeloDias);
			ltDays.setLayoutOrientation(JList.VERTICAL_WRAP);
		}
		return ltDays;
	}
	private JLabel getLbDiasA�adidos() {
		if (lbDiasA�adidos == null) {
			lbDiasA�adidos = new JLabel("D\u00EDas a\u00F1adidos:");
			lbDiasA�adidos.setBounds(10, 141, 85, 13);
		}
		return lbDiasA�adidos;
	}
	private JLabel getLbException() {
		if (lbException == null) {
			lbException = new JLabel("");
			lbException.setVisible(false);
			lbException.setForeground(Color.RED);
			lbException.setBounds(66, 123, 182, 13);
		}
		return lbException;
	}
	private JLabel getLbEuros() {
		if (lbEuros == null) {
			lbEuros = new JLabel("\u20AC");
			lbEuros.setBounds(133, 97, 45, 13);
		}
		return lbEuros;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollPane.setBounds(10, 159, 242, 87);
			scrollPane.setViewportView(getLtDays());
		}
		return scrollPane;
	}
	
	private JButton getBtConfigurar() {
		if (btConfigurar == null) {
			btConfigurar = new JButton("Configurar");
			btConfigurar.setSize(107, 21);
			btConfigurar.setLocation(183, 269);
			btConfigurar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(a�adirCurso()) abrirConfigurador();
				}
			});
			btConfigurar.setBackground(Color.YELLOW);
		}
		return btConfigurar;
	}
	public void configurado() {
		this.getBtA�adir().setEnabled(true);
		this.getBtConfigurar().setEnabled(false);
		this.getLbMensajeConfirmacion().setText("Configurada correctamente");
		this.getLbMensajeConfirmacion().setVisible(true);
	}
	private void abrirConfigurador() {
		ConfiguradorCurso cc = new ConfiguradorCurso(this.getTfNombre().getText(),controler.getDb(),this);
		cc.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		cc.setModal(true);
		cc.setLocationRelativeTo(this);
		cc.setVisible(true);	
	}
	private boolean a�adirCurso() {
		if(validarActividad())
		{
			return true;
		}
		else
		{
			getLbMensajeDenegar().setVisible(true);
			return false;
		}
	}
}