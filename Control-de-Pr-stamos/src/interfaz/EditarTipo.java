package interfaz;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import control.SistemaControl;
import logica.Tipo;

public class EditarTipo extends JDialog {

	private JTextField txtNombre;
	private String nombreOriginal;

	public EditarTipo(JFrame parent, String nombre) {
		this.nombreOriginal = nombre;

		setTitle("Editar Tipo");
		setBounds(100, 100, 300, 170);
		setModal(true);
		setLocationRelativeTo(parent);
		getContentPane().setLayout(null);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 25, 80, 14);
		getContentPane().add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(90, 22, 160, 20);
		getContentPane().add(txtNombre);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(40, 80, 90, 23);
		getContentPane().add(btnGuardar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(150, 80, 90, 23);
		getContentPane().add(btnCancelar);

		btnGuardar.addActionListener(e -> guardarCambios());
		btnCancelar.addActionListener(e -> dispose());

		cargarDatos();
	}

	private void cargarDatos() {
		try {
			Tipo tipo = SistemaControl.getInstance().obtenerTipo(nombreOriginal);

			txtNombre.setText(tipo.getNombre());

		} catch (Exception e) {
			JOptionPane.showMessageDialog(
				this,
				e.getMessage(),
				"Error",
				JOptionPane.ERROR_MESSAGE
			);

			dispose();
		}
	}

	private void guardarCambios() {
		try {
			SistemaControl.getInstance().modificarTipo(
				nombreOriginal,
				txtNombre.getText()
			);

			JOptionPane.showMessageDialog(
				this,
				"Tipo modificado correctamente."
			);

			dispose();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(
				this,
				e.getMessage(),
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
		}
	}
}