package kike.gui.colectivo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import kike.gui.colegiado.PreInscribeColegiado;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.border.CompoundBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SelectorColectivos extends JDialog {
	private static final long serialVersionUID = 1L;

	private JPanel panelFinalizar;
	private JButton btnCancelar;
	private JButton btnFin;
	private JLabel lblNewLabel;
	private JScrollPane scrollPane;
	private JTable table;
	private PreInscribeColegiado preCol;
	private HashMap<String,Double> colectivos;
	

	public SelectorColectivos(PreInscribeColegiado preInscribeColegiado, HashMap<String,Double> colectivos) {
		this.preCol = preInscribeColegiado;
		this.colectivos = colectivos;
		
		setUndecorated(true);
		setResizable(false);
		setBounds(100, 100, 500, 350);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getPanelFinalizar(), BorderLayout.SOUTH);
		getContentPane().add(getLblNewLabel(), BorderLayout.NORTH);
		getContentPane().add(getScrollPane(), BorderLayout.CENTER);
	}

	private JPanel getPanelFinalizar() {
		if (panelFinalizar == null) {
			panelFinalizar = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panelFinalizar.getLayout();
			flowLayout.setHgap(30);
			panelFinalizar.add(getBtnCancelar());
			panelFinalizar.add(getBtnFin());
		}
		return panelFinalizar;
	}
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Sin Colectivo");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					preCol.setGrupo(null);
					dispose();
				}
			});
			btnCancelar.setForeground(Color.WHITE);
			btnCancelar.setBackground(Color.RED);
		}
		return btnCancelar;
	}
	private JButton getBtnFin() {
		if (btnFin == null) {
			btnFin = new JButton("Finalizar");
			btnFin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(getTable().getSelectedRow() < 0) {
						preCol.setGrupo(null);
					} else {					
						preCol.setGrupo((String)getTable().getValueAt(getTable().getSelectedRow(), 0));
					}
					dispose();
				}
			});
			btnFin.setForeground(Color.WHITE);
			btnFin.setBackground(Color.GREEN);
		}
		return btnFin;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Selector de Colectivos");
			lblNewLabel.setBorder(new CompoundBorder(new LineBorder(new Color(192, 192, 192), 2), new EmptyBorder(5, 0, 5, 0)));
			lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 24));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblNewLabel;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			TableModel tm=new DefaultTableModel(new String[]{"Nombre del Colectivo", "% de descuento" }, colectivos.size()) ;
			int i = 0;
			for (String clave : colectivos.keySet()) {
				tm.setValueAt(clave, i, 0);
				tm.setValueAt(colectivos.get(clave), i, 1);
				i++;
			}
			table.setFocusable(false);
			table.setDefaultEditor(Object.class, null);
			table.setModel(tm);
		}
		return table;
	}
}
