package interfaz;
import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import control.SistemaControl;

public class AgregarItem extends JDialog {

	private JTextField txtNombre;
	private JTextField txtDescripcion;

	public AgregarItem(JFrame parent) {
		setTitle("Agregar Item");
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
		lblDescripcion.setBounds(10, 50, 80, 14);
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

		// EVENTOS

		btnGuardar.addActionListener(e -> guardarItem());

		btnCancelar.addActionListener(e -> dispose());
	}

	private void guardarItem() {
		String nombre = txtNombre.getText();
		String descripcion = txtDescripcion.getText();

		if (nombre == null || nombre.isBlank()) {
			JOptionPane.showMessageDialog(
				this,
				"El nombre no puede estar vacío.",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
			return;
		}

		try {
			SistemaControl.getInstance().crearItem(nombre, descripcion);

			JOptionPane.showMessageDialog(
				this,
				"Item agregado correctamente."
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