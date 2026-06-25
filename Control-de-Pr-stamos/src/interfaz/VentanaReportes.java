package interfaz;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;

import control.SistemaControl;

public class VentanaReportes extends JDialog {

	private JTextArea txtReporte;

	public VentanaReportes(JFrame parent) {
		setTitle("Reportes");
		setBounds(100, 100, 700, 500);
		setModal(true);
		setLocationRelativeTo(parent);
		getContentPane().setLayout(null);

		JButton btnUsuarios = new JButton("Por usuarios");
		btnUsuarios.setBounds(20, 20, 140, 30);
		getContentPane().add(btnUsuarios);

		JButton btnItems = new JButton("Por ítems");
		btnItems.setBounds(180, 20, 140, 30);
		getContentPane().add(btnItems);

		JButton btnCategorias = new JButton("Por categorías");
		btnCategorias.setBounds(340, 20, 150, 30);
		getContentPane().add(btnCategorias);

		JButton btnTipos = new JButton("Por tipos");
		btnTipos.setBounds(510, 20, 140, 30);
		getContentPane().add(btnTipos);

		txtReporte = new JTextArea();
		txtReporte.setEditable(false);
		txtReporte.setLineWrap(true);
		txtReporte.setWrapStyleWord(true);

		JScrollPane scroll = new JScrollPane(txtReporte);
		scroll.setBounds(20, 70, 640, 350);
		getContentPane().add(scroll);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setBounds(280, 430, 120, 25);
		getContentPane().add(btnCerrar);

		btnUsuarios.addActionListener(e -> generarReporteUsuarios());
		btnItems.addActionListener(e -> generarReporteItems());
		btnCategorias.addActionListener(e -> generarReporteCategorias());
		btnTipos.addActionListener(e -> generarReporteTipos());
		btnCerrar.addActionListener(e -> dispose());
	}

	private void generarReporteUsuarios() {
		try {
			String reporte = SistemaControl.getInstance().reporteTodosUsuarios();
			txtReporte.setText(reporte);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
		}
	}

	private void generarReporteItems() {
		try {
			String reporte = SistemaControl.getInstance().reporteTodosItems();
			txtReporte.setText(reporte);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
		}
	}

	private void generarReporteCategorias() {
		try {
			String reporte = SistemaControl.getInstance().reporteTodasCategorias();
			txtReporte.setText(reporte);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
		}
	}

	private void generarReporteTipos() {
		try {
			String reporte = SistemaControl.getInstance().reporteTodosTipos();
			txtReporte.setText(reporte);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
		}
	}
}