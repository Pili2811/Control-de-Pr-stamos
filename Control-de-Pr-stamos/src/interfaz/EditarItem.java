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
import logica.Item;
import logica.Tipo;
import logica.Categoria;

public class EditarItem extends JDialog {

	private JTextField txtNombre;
	private JTextField txtDescripcion;
	private JComboBox<Tipo> comboTipo;
	private JList<Categoria> listaCategorias;
	private DefaultListModel<Categoria> modeloCategorias;

	private String nombreActual;

	public EditarItem(JFrame parent, String nombreItem) {
		this.nombreActual = nombreItem;

		setTitle("Editar Item");
		setBounds(100, 100, 420, 330);
		setModal(true);
		setLocationRelativeTo(parent);
		getContentPane().setLayout(null);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 20, 90, 14);
		getContentPane().add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(110, 17, 250, 20);
		getContentPane().add(txtNombre);

		JLabel lblDescripcion = new JLabel("Descripción:");
		lblDescripcion.setBounds(10, 55, 90, 14);
		getContentPane().add(lblDescripcion);

		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(110, 52, 250, 20);
		getContentPane().add(txtDescripcion);

		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(10, 90, 90, 14);
		getContentPane().add(lblTipo);

		comboTipo = new JComboBox<Tipo>();
		comboTipo.setBounds(110, 86, 250, 22);
		getContentPane().add(comboTipo);

		JLabel lblCategorias = new JLabel("Categorías:");
		lblCategorias.setBounds(10, 125, 90, 14);
		getContentPane().add(lblCategorias);

		modeloCategorias = new DefaultListModel<Categoria>();
		listaCategorias = new JList<Categoria>(modeloCategorias);
		listaCategorias.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		JScrollPane scrollCategorias = new JScrollPane(listaCategorias);
		scrollCategorias.setBounds(110, 125, 250, 90);
		getContentPane().add(scrollCategorias);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(80, 240, 100, 23);
		getContentPane().add(btnGuardar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(200, 240, 100, 23);
		getContentPane().add(btnCancelar);

		btnGuardar.addActionListener(e -> guardarCambios());
		btnCancelar.addActionListener(e -> dispose());

		cargarDatos();
	}

	private void cargarDatos() {
		try {
			SistemaControl control = SistemaControl.getInstance();

			Item item = control.obtenerItem(nombreActual);

			txtNombre.setText(item.getNombre());
			txtDescripcion.setText(item.getDescripcion());

			comboTipo.removeAllItems();
			modeloCategorias.clear();

			for (Tipo t : control.obtenerListadoTipos()) {
				comboTipo.addItem(t);

				if (item.getTipo() != null && t.getNombre().equalsIgnoreCase(item.getTipo().getNombre())) {
					comboTipo.setSelectedItem(t);
				}
			}

			for (Categoria c : control.obtenerListadoCategorias()) {
				modeloCategorias.addElement(c);
			}

			for (int i = 0; i < modeloCategorias.size(); i++) {
				Categoria categoriaLista = modeloCategorias.getElementAt(i);

				for (Categoria categoriaItem : item.getCategorias()) {
					if (categoriaLista.getNombre().equalsIgnoreCase(categoriaItem.getNombre())) {
						listaCategorias.addSelectionInterval(i, i);
					}
				}
			}

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
		String nuevoNombre = txtNombre.getText().trim();
		String descripcion = txtDescripcion.getText().trim();
		Tipo tipo = (Tipo) comboTipo.getSelectedItem();

		if (nuevoNombre.isBlank()) {
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

			Item item = control.obtenerItem(nombreActual);

			for (Categoria c : new java.util.ArrayList<Categoria>(item.getCategorias())) {
				control.quitarCategoriaDeItem(nombreActual, c.getNombre());
			}

			control.modificarItem(nombreActual, nuevoNombre, descripcion);
			control.asignarTipoItem(nombreActual, tipo.getNombre());

			for (Categoria c : listaCategorias.getSelectedValuesList()) {
				control.agregarCategoriaAItem(nombreActual, c.getNombre());
			}

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