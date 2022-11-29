package mvc.vista.resources.secretaria;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import mvc.controlador.CanceladorCursoControler;
import mvc.modelo.curso.ActividadFormativaDTO;
import mvc.vista.dialogs.secretaria.CanceladorCurso;

public class CancelarCursoFormativo extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lbTitulo;
	private JScrollPane scrollPane;
	private JButton btCancelar;
	private JTable table;
	private DefaultTableModel modeloTabla = new DefaultTableModel();
	private CanceladorCursoControler controler;
	private int selectedRow;
	private JLabel lbLastCancelado;
	private JButton btCerrar;
	private CanceladorCurso dialog;

	/**
	 * Create the panel.
	 */
	public CancelarCursoFormativo(CanceladorCursoControler controler,CanceladorCurso dialog) {
		this.dialog = dialog;
		this.controler = controler;
		setLayout(null);
		add(getLbTitulo());
		add(getScrollPane());
		add(getBtCancelar());
		add(getLbLastCancelado());
		add(getBtCerrar());
		cargarTablaActividades();

	}
	private JLabel getLbTitulo() {
		if (lbTitulo == null) {
			lbTitulo = new JLabel("Cancelar actividad formativa");
			lbTitulo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			lbTitulo.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lbTitulo.setHorizontalAlignment(SwingConstants.CENTER);
			lbTitulo.setBounds(10, 10, 430, 47);
		}
		return lbTitulo;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 67, 430, 200);
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	private JButton getBtCancelar() {
		if (btCancelar == null) {
			btCancelar = new JButton("Cancelar curso");
			btCancelar.setBackground(Color.ORANGE);
			btCancelar.setEnabled(false);
			btCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String curso = (String) table.getValueAt(selectedRow, 0);
					cancelarCurso(curso);
					btCancelar.setEnabled(false);
				}
			});
			btCancelar.setBounds(216, 311, 129, 21);
		}
		return btCancelar;
	}
	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					getBtCancelar().setEnabled(true);
					selectedRow = table.getSelectedRow();
				}
			});
			table.setModel(modeloTabla);
			table.setFocusable(false);
			table.setDefaultEditor(Object.class, null);
		}
		return table;
	}
	private void cargarTablaActividades() {
		Object[] columns = {"Nombre","Numero plazas","Numero inscritos","Fin inscripcion"};
		this.modeloTabla.setColumnIdentifiers(columns);
		List<ActividadFormativaDTO> actividades = this.controler.getCurrentCursos();
		for(ActividadFormativaDTO af : actividades)
		{
			Object[] data = {af.title, af.numeroPlazas, af.numeroInscritos , af.fin_inscripcion.toString()};
			this.modeloTabla.addRow(data);
		}
	}
	private void cancelarCurso(String curso) {
		if(JOptionPane.showConfirmDialog
				(this,new String("Está seguro de que quiere cancelar el curso " + curso + ". Esto es una acción irreversible y desabilitará dicho curso."),"Cancelar curso",
						JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
			controler.cancelarCurso(curso);
			this.getLbLastCancelado().setText("El curso " + curso + " fue cancelado con éxito.");
			this.modeloTabla.removeRow(selectedRow);
			this.selectedRow = 0;
		}
	}
	private JLabel getLbLastCancelado() {
		if (lbLastCancelado == null) {
			lbLastCancelado = new JLabel("");
			lbLastCancelado.setBounds(10, 277, 430, 27);
		}
		return lbLastCancelado;
	}
	private JButton getBtCerrar() {
		if (btCerrar == null) {
			btCerrar = new JButton("Cerrar");
			btCerrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dialog.cerrar();
					controler.finalizar();
				}
			});
			btCerrar.setBackground(Color.RED);
			btCerrar.setBounds(355, 311, 85, 21);
		}
		return btCerrar;
	}
}
