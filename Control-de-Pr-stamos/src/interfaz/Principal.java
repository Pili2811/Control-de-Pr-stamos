package interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import control.SistemaControl;

public class Principal {

	private JFrame frame;
	private JTabbedPane tabbedPane;

	// Paneles
	private JPanel panelItems;
	private JPanel panelPersonas;
	private JPanel panelPrestamos;
	private JPanel panelCategorias;
	private JPanel panelTipos;

	// ÍTEMS
	private JTable tablaItems;
	private JScrollPane scrollItems;
	private JButton btnAgregarItem;
	private JButton btnEditarItem;
	private JButton btnBorrarItem;
	private javax.swing.JTextField txtBuscarItem;
	private JButton btnBuscarItem;
	private JButton btnMostrarItems;
	
	// PERSONAS
	private JTable tablaPersonas;
	private JScrollPane scrollPersonas;

	private JButton btnAgregarPersona;
	private JButton btnEditarPersona;
	private JButton btnBorrarPersona;
	private javax.swing.JTextField txtBuscarPersona;
	private JButton btnBuscarPersona;
	private JButton btnMostrarPersonas;
	
	// CATEGORÍAS
	private JTable tablaCategorias;
	private JScrollPane scrollCategorias;

	private JButton btnAgregarCategoria;
	private JButton btnEditarCategoria;
	private JButton btnBorrarCategoria;
	private javax.swing.JTextField txtBuscarCategoria;
	private JButton btnBuscarCategoria;
	private JButton btnMostrarCategorias;
	
	// TIPOS
	private JTable tablaTipos;
	private JScrollPane scrollTipos;

	private JButton btnAgregarTipo;
	private JButton btnEditarTipo;
	private JButton btnBorrarTipo;
	private javax.swing.JTextField txtBuscarTipo;
	private JButton btnBuscarTipo;
	private JButton btnMostrarTipos;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SistemaControl.cargarDatos();
					Principal window = new Principal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	// PRÉSTAMOS
	private JTable tablaPrestamos;
	private JScrollPane scrollPrestamos;

	private JButton btnHacerPrestamo;
	private JButton btnFinalizarPrestamo;
	private JButton btnReportePrestamo;
	private javax.swing.JTextField txtBuscarPrestamo;
	private JButton btnBuscarPrestamo;
	private JButton btnMostrarPrestamos;

	public Principal() {
		initialize();
	}

	private void editarItem() {
		int fila = tablaItems.getSelectedRow();

		if (fila == -1) {
			JOptionPane.showMessageDialog(
				frame,
				"Debe seleccionar un item.",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
			return;
		}

		String nombre = (String) tablaItems.getValueAt(fila, 0);

		EditarItem dialog = new EditarItem(frame, nombre);
		dialog.setVisible(true);
		cargarItems();
	}
	
	
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Gestión de Préstamos");
		frame.setResizable(false);
		frame.setBounds(100, 100, 900, 580);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		// ==================== ÍTEMS ====================
		panelItems = new JPanel();
		panelItems.setLayout(null);
		panelItems.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				cargarItems();
			}
		});
		tabbedPane.addTab("Ítems", null, panelItems, null);

		scrollItems = new JScrollPane();
		scrollItems.setBounds(10, 11, 650, 450);
		panelItems.add(scrollItems);

		tablaItems = new JTable();
		tablaItems.setModel(new javax.swing.table.DefaultTableModel(
			new Object[][] {},
			new String[] { "Nombre", "Descripción", "Tipo", "Categorías", "Prestado" }
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class, String.class
			};

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		scrollItems.setViewportView(tablaItems);

		btnAgregarItem = new JButton("Agregar");
		btnAgregarItem.setBounds(680, 20, 150, 30);
		
		panelItems.add(btnAgregarItem);
		btnAgregarItem.addActionListener(e -> {
			AgregarItem dialog = new AgregarItem(frame);
			dialog.setVisible(true);
			cargarItems();
		});

		btnEditarItem = new JButton("Editar");
		btnEditarItem.setBounds(680, 60, 150, 30);
		panelItems.add(btnEditarItem);
		
		btnEditarItem.addActionListener(e -> editarItem());

		btnBorrarItem = new JButton("Borrar");
		btnBorrarItem.setBounds(680, 100, 150, 30);
		panelItems.add(btnBorrarItem);

		btnBorrarItem.addActionListener(e -> borrarItem());
		txtBuscarItem = new javax.swing.JTextField();
		txtBuscarItem.setBounds(680, 150, 150, 25);
		panelItems.add(txtBuscarItem);

		btnBuscarItem = new JButton("Buscar");
		btnBuscarItem.setBounds(680, 185, 150, 30);
		panelItems.add(btnBuscarItem);

		btnMostrarItems = new JButton("Mostrar todos");
		btnMostrarItems.setBounds(680, 225, 150, 30);
		panelItems.add(btnMostrarItems);

		btnBuscarItem.addActionListener(e -> buscarItem());

		btnMostrarItems.addActionListener(e -> {
			txtBuscarItem.setText("");
			cargarItems();
		});
		txtBuscarItem = new javax.swing.JTextField();
		txtBuscarItem.setBounds(680, 150, 150, 25);
		panelItems.add(txtBuscarItem);

		btnBuscarItem = new JButton("Buscar");
		btnBuscarItem.setBounds(680, 185, 150, 30);
		panelItems.add(btnBuscarItem);

		btnMostrarItems = new JButton("Mostrar todos");
		btnMostrarItems.setBounds(680, 225, 150, 30);
		panelItems.add(btnMostrarItems);

		btnBuscarItem.addActionListener(e -> buscarItem());

		btnMostrarItems.addActionListener(e -> {
			txtBuscarItem.setText("");
			cargarItems();
		});
		

		// ==================== PERSONAS ====================
		panelPersonas = new JPanel();
		panelPersonas.setLayout(null);
		scrollPersonas = new JScrollPane();
		scrollPersonas.setBounds(10, 11, 650, 450);
		panelPersonas.add(scrollPersonas);

		tablaPersonas = new JTable();
		tablaPersonas.setModel(new javax.swing.table.DefaultTableModel(
			new Object[][] {},
			new String[] { "Nombre", "Teléfono", "Correo" }
		) {
			Class[] columnTypes = new Class[] {
			 String.class, String.class, String.class
			};

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		scrollPersonas.setViewportView(tablaPersonas);

		btnAgregarPersona = new JButton("Agregar");
		btnAgregarPersona.setBounds(680, 20, 150, 30);
		panelPersonas.add(btnAgregarPersona);

		btnEditarPersona = new JButton("Editar");
		btnEditarPersona.setBounds(680, 60, 150, 30);
		panelPersonas.add(btnEditarPersona);

		btnBorrarPersona = new JButton("Borrar");
		btnBorrarPersona.setBounds(680, 100, 150, 30);
		panelPersonas.add(btnBorrarPersona);

		btnAgregarPersona.addActionListener(e -> {
			AgregarPersona dialog = new AgregarPersona(frame);
			dialog.setVisible(true);
			cargarPersonas();
		});

		btnEditarPersona.addActionListener(e -> editarPersona());

		btnBorrarPersona.addActionListener(e -> borrarPersona());
		panelPersonas.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				cargarPersonas();
			}
		});
		tabbedPane.addTab("Personas", null, panelPersonas, null);
		txtBuscarPersona = new javax.swing.JTextField();
		txtBuscarPersona.setBounds(680, 150, 150, 25);
		panelPersonas.add(txtBuscarPersona);

		btnBuscarPersona = new JButton("Buscar");
		btnBuscarPersona.setBounds(680, 185, 150, 30);
		panelPersonas.add(btnBuscarPersona);

		btnMostrarPersonas = new JButton("Mostrar todos");
		btnMostrarPersonas.setBounds(680, 225, 150, 30);
		panelPersonas.add(btnMostrarPersonas);

		btnBuscarPersona.addActionListener(e -> buscarPersona());

		btnMostrarPersonas.addActionListener(e -> {
			txtBuscarPersona.setText("");
			cargarPersonas();
		});

		// ==================== PRÉSTAMOS ====================
		panelPrestamos = new JPanel();
		panelPrestamos.setLayout(null);
		scrollPrestamos = new JScrollPane();
		scrollPrestamos.setBounds(10, 11, 650, 450);
		panelPrestamos.add(scrollPrestamos);

		tablaPrestamos = new JTable();
		tablaPrestamos.setModel(new javax.swing.table.DefaultTableModel(
			new Object[][] {},
			new String[] { "Persona", "Cantidad préstamos", "Cantidad ítems" }
		) {
			Class[] columnTypes = new Class[] {
					String.class, Integer.class, Integer.class
			};

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		scrollPrestamos.setViewportView(tablaPrestamos);

		btnHacerPrestamo = new JButton("Hacer préstamo");
		btnHacerPrestamo.setBounds(680, 20, 150, 30);
		panelPrestamos.add(btnHacerPrestamo);

		btnFinalizarPrestamo = new JButton("Finalizar");
		btnFinalizarPrestamo.setBounds(680, 60, 150, 30);
		panelPrestamos.add(btnFinalizarPrestamo);

		btnReportePrestamo = new JButton("Ver préstamos");
		btnReportePrestamo.setBounds(680, 100, 150, 30);
		panelPrestamos.add(btnReportePrestamo);

		btnHacerPrestamo.addActionListener(e -> {
			AgregarPrestamo dialog = new AgregarPrestamo(frame);
			dialog.setVisible(true);
			cargarPrestamos();
		});

		btnFinalizarPrestamo.addActionListener(e -> finalizarPrestamo());

		btnReportePrestamo.addActionListener(e -> verPrestamosPersona());
		panelPrestamos.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				cargarPrestamos();
			}
		});
		tabbedPane.addTab("Préstamos", null, panelPrestamos, null);
		txtBuscarPrestamo = new javax.swing.JTextField();
		txtBuscarPrestamo.setBounds(680, 150, 150, 25);
		panelPrestamos.add(txtBuscarPrestamo);

		btnBuscarPrestamo = new JButton("Buscar");
		btnBuscarPrestamo.setBounds(680, 185, 150, 30);
		panelPrestamos.add(btnBuscarPrestamo);

		btnMostrarPrestamos = new JButton("Mostrar todos");
		btnMostrarPrestamos.setBounds(680, 225, 150, 30);
		panelPrestamos.add(btnMostrarPrestamos);

		btnBuscarPrestamo.addActionListener(e -> buscarPrestamo());

		btnMostrarPrestamos.addActionListener(e -> {
			txtBuscarPrestamo.setText("");
			cargarPrestamos();
		});

		// ==================== CATEGORÍAS ====================
		panelCategorias = new JPanel();
		panelCategorias.setLayout(null);

		scrollCategorias = new JScrollPane();
		scrollCategorias.setBounds(10, 11, 650, 450);
		panelCategorias.add(scrollCategorias);

		tablaCategorias = new JTable();
		tablaCategorias.setModel(new javax.swing.table.DefaultTableModel(
			new Object[][] {},
			new String[] { "Nombre" }
		));
		scrollCategorias.setViewportView(tablaCategorias);

		btnAgregarCategoria = new JButton("Agregar");
		btnAgregarCategoria.setBounds(680, 20, 150, 30);
		panelCategorias.add(btnAgregarCategoria);

		btnEditarCategoria = new JButton("Editar");
		btnEditarCategoria.setBounds(680, 60, 150, 30);
		panelCategorias.add(btnEditarCategoria);

		btnBorrarCategoria = new JButton("Borrar");
		btnBorrarCategoria.setBounds(680, 100, 150, 30);
		panelCategorias.add(btnBorrarCategoria);

		btnAgregarCategoria.addActionListener(e -> {
			AgregarCategoria dialog = new AgregarCategoria(frame);
			dialog.setVisible(true);
			cargarCategorias();
		});

		btnEditarCategoria.addActionListener(e -> editarCategoria());

		btnBorrarCategoria.addActionListener(e -> borrarCategoria());

		panelCategorias.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				cargarCategorias();
			}
		});

		tabbedPane.addTab("Categorías", null, panelCategorias, null);
		txtBuscarCategoria = new javax.swing.JTextField();
		txtBuscarCategoria.setBounds(680, 150, 150, 25);
		panelCategorias.add(txtBuscarCategoria);

		btnBuscarCategoria = new JButton("Buscar");
		btnBuscarCategoria.setBounds(680, 185, 150, 30);
		panelCategorias.add(btnBuscarCategoria);

		btnMostrarCategorias = new JButton("Mostrar todos");
		btnMostrarCategorias.setBounds(680, 225, 150, 30);
		panelCategorias.add(btnMostrarCategorias);

		btnBuscarCategoria.addActionListener(e -> buscarCategoria());

		btnMostrarCategorias.addActionListener(e -> {
			txtBuscarCategoria.setText("");
			cargarCategorias();
		});

		// ==================== TIPOS ====================
		panelTipos = new JPanel();
		panelTipos.setLayout(null);

		scrollTipos = new JScrollPane();
		scrollTipos.setBounds(10, 11, 650, 450);
		panelTipos.add(scrollTipos);

		tablaTipos = new JTable();
		tablaTipos.setModel(new javax.swing.table.DefaultTableModel(
			new Object[][] {},
			new String[] { "Nombre" }
		));
		scrollTipos.setViewportView(tablaTipos);

		btnAgregarTipo = new JButton("Agregar");
		btnAgregarTipo.setBounds(680, 20, 150, 30);
		panelTipos.add(btnAgregarTipo);

		btnEditarTipo = new JButton("Editar");
		btnEditarTipo.setBounds(680, 60, 150, 30);
		panelTipos.add(btnEditarTipo);

		btnBorrarTipo = new JButton("Borrar");
		btnBorrarTipo.setBounds(680, 100, 150, 30);
		panelTipos.add(btnBorrarTipo);

		btnAgregarTipo.addActionListener(e -> {
			AgregarTipo dialog = new AgregarTipo(frame);
			dialog.setVisible(true);
			cargarTipos();
		});

		btnEditarTipo.addActionListener(e -> editarTipo());

		btnBorrarTipo.addActionListener(e -> borrarTipo());

		panelTipos.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				cargarTipos();
			}
		});

		tabbedPane.addTab("Tipos", null, panelTipos, null);
		txtBuscarTipo = new javax.swing.JTextField();
		txtBuscarTipo.setBounds(680, 150, 150, 25);
		panelTipos.add(txtBuscarTipo);

		btnBuscarTipo = new JButton("Buscar");
		btnBuscarTipo.setBounds(680, 185, 150, 30);
		panelTipos.add(btnBuscarTipo);

		btnMostrarTipos = new JButton("Mostrar todos");
		btnMostrarTipos.setBounds(680, 225, 150, 30);
		panelTipos.add(btnMostrarTipos);

		btnBuscarTipo.addActionListener(e -> buscarTipo());

		btnMostrarTipos.addActionListener(e -> {
			txtBuscarTipo.setText("");
			cargarTipos();
		});
	}

	// ==================== MÉTODOS ====================

	private void buscarPersona() {
		String texto = txtBuscarPersona.getText().toLowerCase();

		javax.swing.table.DefaultTableModel model =
			(javax.swing.table.DefaultTableModel) tablaPersonas.getModel();

		model.setRowCount(0);

		for (logica.Persona p : SistemaControl.getInstance().obtenerListadoPersonas()) {
			if (p.getNombre().toLowerCase().contains(texto)
				|| p.getTelefono().toLowerCase().contains(texto)
				|| p.getCorreo().toLowerCase().contains(texto)) {

				model.addRow(new Object[] {
					p.getId(),
					p.getNombre(),
					p.getTelefono(),
					p.getCorreo()
				});
			}
		}
	}

	private void buscarPrestamo() {
		String texto = txtBuscarPrestamo.getText().toLowerCase();

		javax.swing.table.DefaultTableModel model =
			(javax.swing.table.DefaultTableModel) tablaPrestamos.getModel();

		model.setRowCount(0);

		for (logica.Persona persona : SistemaControl.getInstance().obtenerListadoPersonas()) {
			if (!persona.getPrestamos().isEmpty()
				&& persona.getNombre().toLowerCase().contains(texto)) {

				int cantidadItems = 0;

				for (logica.Prestamo p : persona.getPrestamos()) {
					cantidadItems += p.getItems().size();
				}

				model.addRow(new Object[] {
					persona.getNombre(),
					persona.getPrestamos().size(),
					cantidadItems
				});
			}
		}
	}

	private void buscarCategoria() {
		String texto = txtBuscarCategoria.getText().toLowerCase();

		javax.swing.table.DefaultTableModel model =
			(javax.swing.table.DefaultTableModel) tablaCategorias.getModel();

		model.setRowCount(0);

		for (logica.Categoria c : SistemaControl.getInstance().obtenerListadoCategorias()) {
			if (c.getNombre().toLowerCase().contains(texto)) {
				model.addRow(new Object[] {
					c.getNombre()
				});
			}
		}
	}

	private void buscarTipo() {
		String texto = txtBuscarTipo.getText().toLowerCase();

		javax.swing.table.DefaultTableModel model =
			(javax.swing.table.DefaultTableModel) tablaTipos.getModel();

		model.setRowCount(0);

		for (logica.Tipo t : SistemaControl.getInstance().obtenerListadoTipos()) {
			if (t.getNombre().toLowerCase().contains(texto)) {
				model.addRow(new Object[] {
					t.getNombre()
				});
			}
		}
	}
	
	private void buscarItem() {
		String texto = txtBuscarItem.getText();

		javax.swing.table.DefaultTableModel model =
			(javax.swing.table.DefaultTableModel) tablaItems.getModel();

		model.setRowCount(0);

		try {
			for (logica.Item it : SistemaControl.getInstance().obtenerListadoItems()) {

				if (it.getNombre().toLowerCase().contains(texto.toLowerCase())
					|| it.getDescripcion().toLowerCase().contains(texto.toLowerCase())
					|| it.getTipo().getNombre().toLowerCase().contains(texto.toLowerCase())
					|| obtenerNombresCategorias(it).toLowerCase().contains(texto.toLowerCase())) {

					Object[] fila = new Object[] {
						it.getNombre(),
						it.getDescripcion(),
						it.getTipo().getNombre(),
						obtenerNombresCategorias(it),
						it.isPrestado() ? "Sí" : "No"
					};

					model.addRow(fila);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
				frame,
				"Error al buscar item: " + e.getMessage(),
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
		}
	}
	
	private String obtenerNombresCategorias(logica.Item item) {
		StringBuilder sb = new StringBuilder();

		for (logica.Categoria c : item.getCategorias()) {
			if (sb.length() > 0) {
				sb.append(", ");
			}
			sb.append(c.getNombre());
		}

		return sb.toString();
	}
	private void cargarItems() {
		SistemaControl control = SistemaControl.getInstance();

		javax.swing.table.DefaultTableModel model =
			(javax.swing.table.DefaultTableModel) tablaItems.getModel();

		model.setRowCount(0);

		try {
			for (logica.Item it : control.obtenerListadoItems()) {
				Object[] fila = new Object[] {
					it.getNombre(),
					it.getDescripcion(),
					it.getTipo().getNombre(),
					obtenerNombresCategorias(it),
					it.isPrestado() ? "Sí" : "No"
				};
				model.addRow(fila);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
				frame,
				"Error al cargar items: " + e.toString(),
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
		}
	}
	private void borrarItem() {
		int fila = tablaItems.getSelectedRow();

		if (fila == -1) {
			JOptionPane.showMessageDialog(
				frame,
				"Debe seleccionar un item.",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
			return;
		}

		String nombre = (String) tablaItems.getValueAt(fila, 0);

		int confirm = JOptionPane.showConfirmDialog(
			frame,
			"¿Eliminar el item " + nombre + "?",
			"Confirmar",
			JOptionPane.YES_NO_OPTION
		);

		if (confirm == JOptionPane.YES_OPTION) {
			try {
				SistemaControl.getInstance().borrarItem(nombre);
				cargarItems();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(
					frame,
					"Error: " + ex.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE
				);
			}
		}
	}
	private void editarPersona() {
		int fila = tablaPersonas.getSelectedRow();

		if (fila == -1) {
			JOptionPane.showMessageDialog(frame, "Debe seleccionar una persona.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		String nombre = (String) tablaPersonas.getValueAt(fila, 0);
		logica.Persona persona = SistemaControl.getInstance().buscarPersonaPorNombre(nombre);

		EditarPersona dialog = new EditarPersona(frame, persona.getId());
		dialog.setVisible(true);
		cargarPersonas();
	}

	private void borrarPersona() {
		int fila = tablaPersonas.getSelectedRow();

		if (fila == -1) {
			JOptionPane.showMessageDialog(frame, "Debe seleccionar una persona.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		String nombre = (String) tablaPersonas.getValueAt(fila, 0);
		logica.Persona persona = SistemaControl.getInstance().buscarPersonaPorNombre(nombre);

		int respuesta = JOptionPane.showConfirmDialog(
			frame,
			"Se eliminará la persona \"" + nombre + "\".",
			"Confirmar",
			JOptionPane.YES_NO_OPTION
		);

		if (respuesta == JOptionPane.YES_OPTION) {
			try {
				SistemaControl.getInstance().borrarPersona(persona.getId());
				cargarPersonas();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(frame, "Error al borrar persona: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void finalizarPrestamo() {
		int fila = tablaPrestamos.getSelectedRow();

		if (fila == -1) {
			JOptionPane.showMessageDialog(
				frame,
				"Debe seleccionar un préstamo.",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
			return;
		}

		String codigo = (String) tablaPrestamos.getValueAt(fila, 0);

		int respuesta = JOptionPane.showConfirmDialog(
			frame,
			"Se finalizará el préstamo \"" + codigo + "\".",
			"Confirmar",
			JOptionPane.YES_NO_OPTION
		);

		if (respuesta == JOptionPane.YES_OPTION) {
			try {
				SistemaControl.getInstance().finalizarPrestamo(codigo);
				cargarPrestamos();
				cargarItems();

			} catch (Exception e) {
				JOptionPane.showMessageDialog(
					frame,
					"Error al finalizar préstamo: " + e.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE
				);
			}
		}
	}
	
	private void verPrestamosPersona() {
		int fila = tablaPrestamos.getSelectedRow();

		if (fila == -1) {
			JOptionPane.showMessageDialog(
				frame,
				"Debe seleccionar una persona.",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
			return;
		}

		String nombrePersona = (String) tablaPrestamos.getValueAt(fila, 0);

		DetallePrestamosPersona dialog = new DetallePrestamosPersona(frame, nombrePersona);
		dialog.setVisible(true);

		cargarPrestamos();
		cargarItems();
	}

	
	
	private void borrarCategoria() {
		int fila = tablaCategorias.getSelectedRow();

		if (fila == -1) {
			JOptionPane.showMessageDialog(
				frame,
				"Debe seleccionar una categoría.",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
			return;
		}

		String nombre = (String) tablaCategorias.getValueAt(fila, 0);

		int respuesta = JOptionPane.showConfirmDialog(
			frame,
			"Se eliminará la categoría \"" + nombre + "\".",
			"Confirmar",
			JOptionPane.YES_NO_OPTION
		);

		if (respuesta == JOptionPane.YES_OPTION) {
			try {
				SistemaControl.getInstance().borrarCategoria(nombre);

				cargarCategorias();

			} catch (Exception e) {
				JOptionPane.showMessageDialog(
					frame,
					"Error al borrar categoría: " + e.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE
				);
			}
		}
	}
	
	private void editarCategoria() {
		int fila = tablaCategorias.getSelectedRow();

		if (fila == -1) {
			JOptionPane.showMessageDialog(
				frame,
				"Debe seleccionar una categoría.",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
			return;
		}

		String nombre = (String) tablaCategorias.getValueAt(fila, 0);

		EditarCategoria dialog = new EditarCategoria(frame, nombre);
		dialog.setVisible(true);

		cargarCategorias();
	}
	
	private void editarTipo() {
		int fila = tablaTipos.getSelectedRow();

		if (fila == -1) {
			JOptionPane.showMessageDialog(
				frame,
				"Debe seleccionar un tipo.",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
			return;
		}

		String nombre = (String) tablaTipos.getValueAt(fila, 0);

		EditarTipo dialog = new EditarTipo(frame, nombre);
		dialog.setVisible(true);

		cargarTipos();
		cargarItems();
	}

	private void borrarTipo() {
		int fila = tablaTipos.getSelectedRow();

		if (fila == -1) {
			JOptionPane.showMessageDialog(
				frame,
				"Debe seleccionar un tipo.",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
			return;
		}

		String nombre = (String) tablaTipos.getValueAt(fila, 0);

		int respuesta = JOptionPane.showConfirmDialog(
			frame,
			"Se eliminará el tipo \"" + nombre + "\".",
			"Confirmar",
			JOptionPane.YES_NO_OPTION
		);

		if (respuesta == JOptionPane.YES_OPTION) {
			try {
				SistemaControl.getInstance().borrarTipo(nombre);

				cargarTipos();
				cargarItems();

			} catch (Exception e) {
				JOptionPane.showMessageDialog(
					frame,
					"Error al borrar tipo: " + e.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE
				);
			}
		}
	}
	
	
	private void cargarPersonas() {
		SistemaControl control = SistemaControl.getInstance();

		javax.swing.table.DefaultTableModel model =
			(javax.swing.table.DefaultTableModel) tablaPersonas.getModel();

		model.setRowCount(0);

		try {
			for (logica.Persona p : control.obtenerListadoPersonas()) {
				Object[] fila = new Object[] {
					p.getNombre(),
					p.getTelefono(),
					p.getCorreo()
				};
				model.addRow(fila);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
				frame,
				"Error al cargar personas: " + e.toString(),
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
		}
	}
	private void cargarPrestamos() {
		SistemaControl control = SistemaControl.getInstance();

		javax.swing.table.DefaultTableModel model =
			(javax.swing.table.DefaultTableModel) tablaPrestamos.getModel();

		model.setRowCount(0);

		try {
			for (logica.Persona persona : control.obtenerListadoPersonas()) {
				if (!persona.getPrestamos().isEmpty()) {

					int cantidadItems = 0;

					for (logica.Prestamo p : persona.getPrestamos()) {
						cantidadItems += p.getItems().size();
					}

					Object[] fila = new Object[] {
						persona.getNombre(),
						persona.getPrestamos().size(),
						cantidadItems
					};

					model.addRow(fila);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
				frame,
				"Error al cargar préstamos: " + e.toString(),
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
		}
	}
	private void cargarCategorias() {
		SistemaControl control = SistemaControl.getInstance();

		javax.swing.table.DefaultTableModel model =
			(javax.swing.table.DefaultTableModel) tablaCategorias.getModel();

		model.setRowCount(0);

		try {
			for (logica.Categoria c : control.obtenerListadoCategorias()) {

				Object[] fila = new Object[] {
					c.getNombre()
				};

				model.addRow(fila);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(
				frame,
				"Error al cargar categorías: " + e.getMessage(),
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
		}
	}
	private void cargarTipos() {
		SistemaControl control = SistemaControl.getInstance();

		javax.swing.table.DefaultTableModel model =
			(javax.swing.table.DefaultTableModel) tablaTipos.getModel();

		model.setRowCount(0);

		try {
			for (logica.Tipo t : control.obtenerListadoTipos()) {
				Object[] fila = new Object[] {
					t.getNombre()
				};

				model.addRow(fila);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(
				frame,
				"Error al cargar tipos: " + e.getMessage(),
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
		}
	}
}