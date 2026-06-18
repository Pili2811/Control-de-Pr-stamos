package interfaz;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import control.SistemaControl;
import logica.Persona;
import logica.Item;

public class AgregarPrestamo extends JDialog {

	private JComboBox<Persona> comboPersonas;
	private JComboBox<Item> comboItems;

	public AgregarPrestamo(JFrame parent) {
		setTitle("Hacer préstamo");
		setBounds(100, 100, 360, 200);
		setModal(true);
		setLocationRelativeTo(parent);
		getContentPane().setLayout(null);

		JLabel lblPersona = new JLabel("Persona:");
		lblPersona.setBounds(10, 25, 80, 14);
		getContentPane().add(lblPersona);

		comboPersonas = new JComboBox<Persona>();
		comboPersonas.setBounds(100, 21, 210, 22);
		getContentPane().add(comboPersonas);

		JLabel lblItem = new JLabel("Item:");
		lblItem.setBounds(10, 65, 80, 14);
		getContentPane().add(lblItem);

		comboItems = new JComboBox<Item>();
		comboItems.setBounds(100, 61, 210, 22);
		getContentPane().add(comboItems);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(65, 115, 90, 23);
		getContentPane().add(btnGuardar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(175, 115, 100, 23);
		getContentPane().add(btnCancelar);

		btnGuardar.addActionListener(e -> guardarPrestamo());
		btnCancelar.addActionListener(e -> dispose());

		cargarDatos();
	}

	private void cargarDatos() {
		SistemaControl control = SistemaControl.getInstance();

		comboPersonas.removeAllItems();
		comboItems.removeAllItems();

		for (Persona p : control.obtenerListadoPersonas()) {
			comboPersonas.addItem(p);
		}

		for (Item item : control.obtenerListadoItems()) {
			if (!item.isPrestado()) {
				comboItems.addItem(item);
			}
		}
	}

	private void guardarPrestamo() {
		Persona persona = (Persona) comboPersonas.getSelectedItem();
		Item item = (Item) comboItems.getSelectedItem();

		if (persona == null) {
			JOptionPane.showMessageDialog(
				this,
				"Debe seleccionar una persona.",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
			return;
		}

		if (item == null) {
			JOptionPane.showMessageDialog(
				this,
				"Debe seleccionar un item disponible.",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
			return;
		}

		try {
			SistemaControl.getInstance().hacerPrestamo(
				persona.getId(),
				item.getNombre()
			);

			JOptionPane.showMessageDialog(
				this,
				"Préstamo creado correctamente."
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