package interfaz;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import control.SistemaControl;
import logica.Item;

public class EditarItem extends JDialog {

	private JTextField txtNombre;
	private JTextField txtDescripcion;
	private String nombreActual;

	public EditarItem(JFrame parent, String nombreItem) {
		this.nombreActual = nombreItem;

		setTitle("Editar Item");
		setBounds(100, 100, 300, 200);
		setModal(true);
		setLocationRelativeTo(parent);
		getContentPane().setLayout(null);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 20, 80, 14);
		getContentPane().add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(100, 17, 150, 20);
		getContentPane().add(txtNombre);

		JLabel lblDescripcion = new JLabel("Descripción:");
		lblDescripcion.setBounds(10, 50, 90, 14);
		getContentPane().add(lblDescripcion);

		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(100, 47, 150, 20);
		getContentPane().add(txtDescripcion);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(40, 100, 90, 23);
		getContentPane().add(btnGuardar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(150, 100, 90, 23);
		getContentPane().add(btnCancelar);

		btnGuardar.addActionListener(e -> guardarCambios());
		btnCancelar.addActionListener(e -> dispose());

		cargarDatosItem();
	}

	private void cargarDatosItem() {
		try {
			Item item = SistemaControl.getInstance().obtenerItem(nombreActual);

			txtNombre.setText(item.getNombre());
			txtDescripcion.setText(item.getDescripcion());

		} catch (Exception e) {
			JOptionPane.showMessageDialog(
				this,
				"Error al cargar item: " + e.getMessage(),
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
			dispose();
		}
	}

	private void guardarCambios() {
		String nuevoNombre = txtNombre.getText();
		String descripcion = txtDescripcion.getText();

		if (nuevoNombre == null || nuevoNombre.isBlank()) {
			JOptionPane.showMessageDialog(
				this,
				"El nombre no puede estar vacío.",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
			return;
		}

		try {
			SistemaControl.getInstance().modificarItem(nombreActual, nuevoNombre, descripcion);

			JOptionPane.showMessageDialog(
				this,
				"Item modificado correctamente."
			);

			dispose();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(
				this,
				"Error: " + e.getMessage(),
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
		}
	}
}