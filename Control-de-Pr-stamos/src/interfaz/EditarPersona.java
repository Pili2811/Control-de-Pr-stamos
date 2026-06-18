package interfaz;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import control.SistemaControl;
import logica.Persona;

public class EditarPersona extends JDialog {

	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JTextField txtCorreo;
	private String idPersona;

	public EditarPersona(JFrame parent, String idPersona) {
		this.idPersona = idPersona;

		setTitle("Editar Persona");
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

		btnGuardar.addActionListener(e -> guardarCambios());
		btnCancelar.addActionListener(e -> dispose());

		cargarDatosPersona();
	}

	private void cargarDatosPersona() {
		try {
			Persona persona = SistemaControl.getInstance().obtenerPersona(idPersona);

			txtNombre.setText(persona.getNombre());
			txtTelefono.setText(persona.getTelefono());
			txtCorreo.setText(persona.getCorreo());

		} catch (Exception e) {
			JOptionPane.showMessageDialog(
				this,
				"Error al cargar persona: " + e.getMessage(),
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
			dispose();
		}
	}

	private void guardarCambios() {
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
			SistemaControl.getInstance().modificarPersona(idPersona, nombre, telefono, correo);

			JOptionPane.showMessageDialog(
				this,
				"Persona modificada correctamente."
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