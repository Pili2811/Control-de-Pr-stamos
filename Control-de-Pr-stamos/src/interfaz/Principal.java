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

import javax.swing.JTable;
import javax.swing.JScrollPane;
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
	
	// PERSONAS
	private JTable tablaPersonas;
	private JScrollPane scrollPersonas;

	private JButton btnAgregarPersona;
	private JButton btnEditarPersona;
	private JButton btnBorrarPersona;

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
			new String[] { "Nombre", "Descripción", "Tipo", "Prestado" }
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class
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

		// ==================== PERSONAS ====================
		panelPersonas = new JPanel();
		panelPersonas.setLayout(null);
		scrollPersonas = new JScrollPane();
		scrollPersonas.setBounds(10, 11, 650, 450);
		panelPersonas.add(scrollPersonas);

		tablaPersonas = new JTable();
		tablaPersonas.setModel(new javax.swing.table.DefaultTableModel(
			new Object[][] {},
			new String[] { "ID", "Nombre", "Teléfono", "Correo" }
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class
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

		// ==================== PRÉSTAMOS ====================
		panelPrestamos = new JPanel();
		panelPrestamos.setLayout(null);
		panelPrestamos.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				cargarPrestamos();
			}
		});
		tabbedPane.addTab("Préstamos", null, panelPrestamos, null);

		// ==================== CATEGORÍAS ====================
		panelCategorias = new JPanel();
		panelCategorias.setLayout(null);
		panelCategorias.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				cargarCategorias();
			}
		});
		tabbedPane.addTab("Categorías", null, panelCategorias, null);

		// ==================== TIPOS ====================
		panelTipos = new JPanel();
		panelTipos.setLayout(null);
		panelTipos.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				cargarTipos();
			}
		});
		tabbedPane.addTab("Tipos", null, panelTipos, null);
	}

	// ==================== MÉTODOS ====================

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
			JOptionPane.showMessageDialog(
				frame,
				"Debe seleccionar una persona.",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
			return;
		}

		String id = (String) tablaPersonas.getValueAt(fila, 0);

		EditarPersona dialog = new EditarPersona(frame, id);
		dialog.setVisible(true);
		cargarPersonas();
	}

	private void borrarPersona() {
		int fila = tablaPersonas.getSelectedRow();

		if (fila == -1) {
			JOptionPane.showMessageDialog(
				frame,
				"Debe seleccionar una persona.",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
			return;
		}

		String id = (String) tablaPersonas.getValueAt(fila, 0);
		String nombre = (String) tablaPersonas.getValueAt(fila, 1);

		int respuesta = JOptionPane.showConfirmDialog(
			frame,
			"Se eliminará la persona \"" + nombre + "\".",
			"Confirmar",
			JOptionPane.YES_NO_OPTION
		);

		if (respuesta == JOptionPane.YES_OPTION) {
			try {
				SistemaControl.getInstance().borrarPersona(id);
				cargarPersonas();

			} catch (Exception e) {
				JOptionPane.showMessageDialog(
					frame,
					"Error al borrar persona: " + e.getMessage(),
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
					p.getId(),
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
	private void cargarPrestamos() {}
	private void cargarCategorias() {}
	private void cargarTipos() {}
}