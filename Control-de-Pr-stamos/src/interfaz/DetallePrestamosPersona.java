package interfaz;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import control.SistemaControl;
import logica.Persona;
import logica.Prestamo;
import logica.Item;

public class DetallePrestamosPersona extends JDialog {

	private JTable tablaDetalle;
	private String nombrePersona;

	public DetallePrestamosPersona(JFrame parent, String nombrePersona) {
		this.nombrePersona = nombrePersona;

		setTitle("Préstamos de " + nombrePersona);
		setBounds(100, 100, 650, 350);
		setModal(true);
		setLocationRelativeTo(parent);
		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 460, 250);
		getContentPane().add(scrollPane);

		tablaDetalle = new JTable();
		tablaDetalle.setModel(new javax.swing.table.DefaultTableModel(
			new Object[][] {},
			new String[] { "Código", "Fecha", "Ítems" }
		) {
			Class[] columnTypes = new Class[] {
				String.class, Object.class, String.class
			};

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		scrollPane.setViewportView(tablaDetalle);

		JButton btnFinalizar = new JButton("Finalizar");
		btnFinalizar.setBounds(490, 30, 120, 30);
		getContentPane().add(btnFinalizar);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setBounds(490, 75, 120, 30);
		getContentPane().add(btnCerrar);

		btnFinalizar.addActionListener(e -> finalizarPrestamo());
		btnCerrar.addActionListener(e -> dispose());

		cargarPrestamos();
	}

	private void cargarPrestamos() {
		javax.swing.table.DefaultTableModel model =
			(javax.swing.table.DefaultTableModel) tablaDetalle.getModel();

		model.setRowCount(0);

		try {
			Persona persona = SistemaControl.getInstance().buscarPersonaPorNombre(nombrePersona);

			if (persona != null) {
				for (Prestamo p : persona.getPrestamos()) {
					Object[] fila = new Object[] {
						p.getCodigo(),
						p.getFecha(),
						obtenerNombresItems(p)
					};

					model.addRow(fila);
				}
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(
				this,
				"Error al cargar préstamos: " + e.getMessage(),
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
		}
	}

	private String obtenerNombresItems(Prestamo prestamo) {
		StringBuilder sb = new StringBuilder();

		for (Item item : prestamo.getItems()) {
			if (sb.length() > 0) {
				sb.append(", ");
			}

			sb.append(item.getCodigo())
			  .append(" - ")
			  .append(item.getNombre());
		}

		return sb.toString();
	}

	private void finalizarPrestamo() {
		int fila = tablaDetalle.getSelectedRow();

		if (fila == -1) {
			JOptionPane.showMessageDialog(
				this,
				"Debe seleccionar un préstamo.",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
			return;
		}

		String codigo = (String) tablaDetalle.getValueAt(fila, 0);

		int respuesta = JOptionPane.showConfirmDialog(
			this,
			"Se finalizará el préstamo \"" + codigo + "\".",
			"Confirmar",
			JOptionPane.YES_NO_OPTION
		);

		if (respuesta == JOptionPane.YES_OPTION) {
			try {
				SistemaControl.getInstance().finalizarPrestamo(codigo);
				cargarPrestamos();

			} catch (Exception e) {
				JOptionPane.showMessageDialog(
					this,
					"Error al finalizar préstamo: " + e.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE
				);
			}
		}
	}
}