package abel.vista.dialogs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import abel.vista.SolicitarTitulacionColegiado;

public class JustificanteLote extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JScrollPane scrollPane;
	private JPanel panel;
	private JButton btAceptar;
	private JButton btCancelar;
	private SolicitarTitulacionColegiado panelPadre;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			JustificanteLote dialog = new JustificanteLote(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public JustificanteLote(SolicitarTitulacionColegiado panel) {
		setTitle("Contenido del lote");
		this.panelPadre = panel;
		setBounds(100, 100, 403, 399);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		contentPanel.add(getScrollPane(), BorderLayout.CENTER);
		contentPanel.add(getPanel(), BorderLayout.SOUTH);
		this.getTextArea().setText(panelPadre.getJustificante());
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTextArea());
		}
		return scrollPane;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getBtAceptar());
			panel.add(getBtCancelar());
		}
		return panel;
	}
	private JButton getBtAceptar() {
		if (btAceptar == null) {
			btAceptar = new JButton("Aceptar");
			btAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					panelPadre.cargarAFichero();
					mostrarMensajeFicheroCreado();
					cerrar();
				}
			});
		}
		return btAceptar;
	}
	private JButton getBtCancelar() {
		if (btCancelar == null) {
			btCancelar = new JButton("Cancelar");
			btCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cerrar();
				}
			});
		}
		return btCancelar;
	}
	private void cerrar()
	{
		panelPadre.resetear();
		this.dispose();
	}
	private void mostrarMensajeFicheroCreado()
	{
		JOptionPane.showConfirmDialog(this,new String("Se ha creado un fichero llamado consultaTitulacion.csv en la carpeta files."),"Fichero creado",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
	}
	private JTextArea getTextArea() {
		if (textArea == null) {
			textArea = new JTextArea();
			textArea.setEditable(false);
		}
		return textArea;
	}
}
