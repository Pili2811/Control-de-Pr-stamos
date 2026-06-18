package interfaz;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import control.SistemaControl;

public class AgregarPersona extends JDialog {

	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JTextField txtCorreo;

	public AgregarPersona(JFrame parent) {
		setTitle("Agregar Persona");
		setBounds(100, 100, 330, 230);
		setModal(true);
		setLocationRelativeTo(parent);
		getContentPane().setLayout(null);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 20, 80, 14);
		getContentPane().add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(100, 17, 180, 20);
		getContentPane().add(txtNombre);

		JLabel lblTelefono = new JLabel("Teléfono:");
		lblTelefono.setBounds(10, 55, 80, 14);
		getContentPane().add(lblTelefono);

		txtTelefono = new JTextField();
		txtTelefono.setBounds(100, 52, 180, 20);
		getContentPane().add(txtTelefono);

		JLabel lblCorreo = new JLabel("Correo:");
		lblCorreo.setBounds(10, 90, 80, 14);
		getContentPane().add(lblCorreo);

		txtCorreo = new JTextField();
		txtCorreo.setBounds(100, 87, 180, 20);
		getContentPane().add(txtCorreo);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(55, 140, 90, 23);
		getContentPane().add(btnGuardar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(160, 140, 100, 23);
		getContentPane().add(btnCancelar);

		btnGuardar.addActionListener(e -> guardarPersona());
		btnCancelar.addActionListener(e -> dispose());
	}

	private void guardarPersona() {
		String nombre = txtNombre.getText();
		String telefono = txtTelefono.getText();
		String correo = txtCorreo.getText();

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
			SistemaControl.getInstance().crearPersona(nombre, telefono, correo);

			JOptionPane.showMessageDialog(
				this,
				"Persona agregada correctamente."
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