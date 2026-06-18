package interfaz;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultListModel;

import control.SistemaControl;
import logica.Tipo;
import logica.Categoria;

public class AgregarItem extends JDialog {

	private JTextField txtNombre;
	private JTextField txtDescripcion;
	private JComboBox<Tipo> comboTipo;
	private JList<Categoria> listaCategorias;
	private DefaultListModel<Categoria> modeloCategorias;

	public AgregarItem(JFrame parent) {
		setTitle("Agregar Item");
		setBounds(100, 100, 460, 380);
		setModal(true);
		setLocationRelativeTo(parent);
		getContentPane().setLayout(null);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(20, 20, 100, 20);
		getContentPane().add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(130, 20, 260, 22);
		getContentPane().add(txtNombre);

		JLabel lblDescripcion = new JLabel("Descripción:");
		lblDescripcion.setBounds(20, 60, 100, 20);
		getContentPane().add(lblDescripcion);

		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(130, 60, 260, 22);
		getContentPane().add(txtDescripcion);

		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(20, 100, 100, 20);
		getContentPane().add(lblTipo);

		comboTipo = new JComboBox<Tipo>();
		comboTipo.setBounds(130, 100, 260, 22);
		getContentPane().add(comboTipo);

		JLabel lblCategorias = new JLabel("Categorías:");
		lblCategorias.setBounds(20, 140, 100, 20);
		getContentPane().add(lblCategorias);

		modeloCategorias = new DefaultListModel<Categoria>();
		listaCategorias = new JList<Categoria>(modeloCategorias);
		listaCategorias.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		JScrollPane scrollCategorias = new JScrollPane(listaCategorias);
		scrollCategorias.setBounds(130, 140, 260, 110);
		getContentPane().add(scrollCategorias);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(100, 285, 100, 25);
		getContentPane().add(btnGuardar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(220, 285, 100, 25);
		getContentPane().add(btnCancelar);

		btnGuardar.addActionListener(e -> guardarItem());
		btnCancelar.addActionListener(e -> dispose());

		cargarDatos();
	}

	private void cargarDatos() {
		SistemaControl control = SistemaControl.getInstance();

		comboTipo.removeAllItems();
		modeloCategorias.clear();

		for (Tipo t : control.obtenerListadoTipos()) {
			comboTipo.addItem(t);
		}

		for (Categoria c : control.obtenerListadoCategorias()) {
			modeloCategorias.addElement(c);
		}
	}

	private void guardarItem() {
		String nombre = txtNombre.getText();
		String descripcion = txtDescripcion.getText();
		Tipo tipo = (Tipo) comboTipo.getSelectedItem();

		if (nombre == null || nombre.isBlank()) {
			JOptionPane.showMessageDialog(
				this,
				"El nombre no puede estar vacío.",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
			return;
		}

		if (tipo == null) {
			JOptionPane.showMessageDialog(
				this,
				"Debe seleccionar un tipo.",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
			return;
		}

		try {
			SistemaControl control = SistemaControl.getInstance();

			control.crearItem(nombre, descripcion);
			control.asignarTipoItem(nombre, tipo.getNombre());

			for (Categoria c : listaCategorias.getSelectedValuesList()) {
				control.agregarCategoriaAItem(nombre, c.getNombre());
			}

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