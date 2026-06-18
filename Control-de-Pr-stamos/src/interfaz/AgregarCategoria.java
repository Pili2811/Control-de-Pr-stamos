package interfaz;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import control.SistemaControl;

public class AgregarCategoria extends JDialog {

	private JTextField txtNombre;

	public AgregarCategoria(JFrame parent) {

		setTitle("Agregar Categoría");
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

		btnGuardar.addActionListener(e -> guardarCategoria());
		btnCancelar.addActionListener(e -> dispose());
	}

	private void guardarCategoria() {

		try {
			SistemaControl.getInstance().crearCategoria(
				txtNombre.getText()
			);

			JOptionPane.showMessageDialog(
				this,
				"Categoría agregada correctamente."
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