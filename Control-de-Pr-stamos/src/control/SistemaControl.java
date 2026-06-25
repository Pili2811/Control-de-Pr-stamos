package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import logica.Alerta;
import logica.Categoria;
import logica.Item;
import logica.Persona;
import logica.Prestamo;
import logica.Tipo;

public class SistemaControl implements Serializable {
	private static final String ARCHIVO_DATOS = "datos.dat";
	private static final String TIPO_GENERICO = "Generico";
	private static SistemaControl instance = null;
	private List<Item> items;
	private List<Persona> personas;
	private List<Prestamo> prestamos;
	private List<Tipo> tipos;
	private List<Categoria> categorias;
	private SistemaControl() {
		this.items = new ArrayList<Item>();
		this.personas = new ArrayList<Persona>();
		this.prestamos = new ArrayList<Prestamo>();
		this.tipos = new ArrayList<Tipo>();
		this.categorias = new ArrayList<Categoria>();
		this.tipos.add(new Tipo(TIPO_GENERICO));
	}
	public static SistemaControl getInstance() {
		if (instance == null)
			instance = new SistemaControl();
		return instance;
	}

	// VERIFICACIONES 

	private void verificarItemExistente(String codigo) throws Exception {
		if (buscarItem(codigo) == null)
			throw new Exception("Item no encontrado.");
	}

	private void verificarItemNoExistente(String codigo) throws Exception {
		if (buscarItem(codigo) != null)
			throw new Exception("Ya existe un item con ese código.");
	}
	private void verificarPersonaExistente(String id) throws Exception {
		if (buscarPersona(id) == null)
			throw new Exception("Persona no encontrada.");
	}
	private void verificarCategoriaExistente(String nombre) throws Exception {
		if (buscarCategoria(nombre) == null)
			throw new Exception("Categoria no encontrada.");
	}
	private void verificarCategoriaNoExistente(String nombre) throws Exception {
		if (buscarCategoria(nombre) != null)
			throw new Exception("Ya existe una categoria con ese nombre.");
	}
	private void verificarTipoExistente(String nombre) throws Exception {
		if (buscarTipo(nombre) == null)
			throw new Exception("Tipo no encontrado.");
	}
	private void verificarTipoNoExistente(String nombre) throws Exception {
		if (buscarTipo(nombre) != null)
			throw new Exception("Ya existe un tipo con ese nombre.");
	}
	private void verificarPrestamoExistente(String codigo) throws Exception {
		if (buscarPrestamo(codigo) == null)
			throw new Exception("Prestamo no encontrado.");
	}

	//  ITEMS
	
	public Item buscarItem(String codigo) {
		if (codigo == null)
			return null;
	
		for (Item it : items) {
			if (it.getCodigo().equalsIgnoreCase(codigo))
				return it;
		}
	
		return null;
	}
	
	public Item obtenerItem(String codigo) throws Exception {
		verificarItemExistente(codigo);
		return buscarItem(codigo);
	}
	
	public List<Item> obtenerListadoItems() {
		return items;
	}
	
	public void crearItem(String codigo, String nombre, String descripcion) throws Exception {
		if (codigo == null || codigo.isBlank())
			throw new Exception("El código no puede ser vacío.");
	
		if (nombre == null || nombre.isBlank())
			throw new Exception("El nombre no puede ser vacío.");
	
		verificarItemNoExistente(codigo);
	
		Item item = new Item(codigo, nombre, descripcion, obtenerTipoGenerico());
		items.add(item);
		guardarDatos();
	}
	
	public void modificarItem(String codigo, String nuevoNombre, String descripcion) throws Exception {
		Item item = obtenerItem(codigo);
	
		if (nuevoNombre == null || nuevoNombre.isBlank())
			throw new Exception("El nombre no puede ser vacío.");
	
		item.setNombre(nuevoNombre);
		item.setDescripcion(descripcion);
		guardarDatos();
	}
	
	public void borrarItem(String codigo) throws Exception {
		Item item = obtenerItem(codigo);
	
		if (item.isPrestado())
			throw new Exception("El item se encuentra en un préstamo.");
	
		item.desvincular();
		items.remove(item);
		guardarDatos();
	}
	
	public void asignarTipoItem(String codigoItem, String nombreTipo) throws Exception {
		Item item = obtenerItem(codigoItem);
		Tipo tipo = obtenerTipo(nombreTipo);
	
		item.setTipo(tipo);
		guardarDatos();
	}
	
	public void agregarCategoriaAItem(String codigoItem, String nombreCategoria) throws Exception {
		Item item = obtenerItem(codigoItem);
		Categoria categoria = obtenerCategoria(nombreCategoria);
	
		item.agregarCategoria(categoria);
		guardarDatos();
	}
	
	public void quitarCategoriaDeItem(String codigoItem, String nombreCategoria) throws Exception {
		Item item = obtenerItem(codigoItem);
		Categoria categoria = obtenerCategoria(nombreCategoria);
	
		item.quitarCategoria(categoria);
		guardarDatos();
	}

	//  PERSONAS 

	public Persona buscarPersona(String id) {
		if (id == null)
			return null;
		for (Persona p : personas) {
			if (p.getId().equals(id))
				return p;
		}
		return null;
	}
	public Persona buscarPersonaPorNombre(String nombre) {
		if (nombre == null)
			return null;
		for (Persona p : personas) {
			if (p.getNombre().equalsIgnoreCase(nombre))
				return p;
		}
		return null;
	}
	public Persona obtenerPersona(String id) throws Exception {
		verificarPersonaExistente(id);
		return buscarPersona(id);
	}
	public List<Persona> obtenerListadoPersonas() {
		return personas;
	}
	public String crearPersona(String nombre, String telefono, String correo) throws Exception {
		if (nombre == null || nombre.isBlank())
			throw new Exception("El nombre no puede ser vacio.");
		Persona persona = new Persona(nombre, telefono, correo);
		personas.add(persona);
		guardarDatos();
		return persona.getId();
	}
	public void modificarPersona(String id, String nombre, String telefono, String correo) throws Exception {
		Persona persona = obtenerPersona(id);
		persona.setNombre(nombre);
		persona.setTelefono(telefono);
		persona.setCorreo(correo);
		guardarDatos();
	}
	public void borrarPersona(String id) throws Exception {
		Persona persona = obtenerPersona(id);
		if (persona.tienePrestamos())
			throw new Exception("La persona tiene prestamos activos.");
		personas.remove(persona);
		guardarDatos();
	}

	//  CATEGORIAS 

	public Categoria buscarCategoria(String nombre) {
		if (nombre == null)
			return null;
		for (Categoria c : categorias) {
			if (c.getNombre().equalsIgnoreCase(nombre))
				return c;
		}
		return null;
	}
	public Categoria obtenerCategoria(String nombre) throws Exception {
		verificarCategoriaExistente(nombre);
		return buscarCategoria(nombre);
	}
	public List<Categoria> obtenerListadoCategorias() {
		return categorias;
	}
	public void crearCategoria(String nombre) throws Exception {
		if (nombre == null || nombre.isBlank())
			throw new Exception("El nombre no puede ser vacio.");
		verificarCategoriaNoExistente(nombre);
		categorias.add(new Categoria(nombre));
		guardarDatos();
	}
	public void modificarCategoria(String nombreActual, String nuevoNombre) throws Exception {
		Categoria categoria = obtenerCategoria(nombreActual);
		categoria.setNombre(nuevoNombre);
		guardarDatos();
	}
	public void borrarCategoria(String nombre) throws Exception {
		Categoria categoria = obtenerCategoria(nombre);
		for (Item item : new ArrayList<Item>(categoria.getItems())) {
			item.quitarCategoria(categoria);
		}
		categorias.remove(categoria);
		guardarDatos();
	}

	//  TIPOS 

	public Tipo buscarTipo(String nombre) {
		if (nombre == null)
			return null;
		for (Tipo t : tipos) {
			if (t.getNombre().equalsIgnoreCase(nombre))
				return t;
		}
		return null;
	}
	public Tipo obtenerTipo(String nombre) throws Exception {
		verificarTipoExistente(nombre);
		return buscarTipo(nombre);
	}
	public Tipo obtenerTipoGenerico() {
		for (Tipo t : tipos) {
			if (t.getNombre().equals(TIPO_GENERICO))
				return t;
		}
		return tipos.get(0);
	}
	public List<Tipo> obtenerListadoTipos() {
		return tipos;
	}
	public void crearTipo(String nombre) throws Exception {
		if (nombre == null || nombre.isBlank())
			throw new Exception("El nombre no puede ser vacio.");
		verificarTipoNoExistente(nombre);
		tipos.add(new Tipo(nombre));
		guardarDatos();
	}
	public void modificarTipo(String nombreActual, String nuevoNombre) throws Exception {
		Tipo tipo = obtenerTipo(nombreActual);
		tipo.setNombre(nuevoNombre);
		guardarDatos();
	}
	public void borrarTipo(String nombre) throws Exception {
		Tipo tipo = obtenerTipo(nombre);
		if (tipo.getNombre().equals(TIPO_GENERICO))
			throw new Exception("El tipo generico no se puede borrar.");
		Tipo generico = obtenerTipoGenerico();
		for (Item item : new ArrayList<Item>(tipo.getItems())) {
			item.setTipo(generico);
		}
		tipos.remove(tipo);
		guardarDatos();
	}

	//  PRESTAMOS 

	public Prestamo buscarPrestamo(String codigo) {
		if (codigo == null)
			return null;
		for (Prestamo p : prestamos) {
			if (p.getCodigo().equalsIgnoreCase(codigo))
				return p;
		}
		return null;
	}
	public Prestamo obtenerPrestamo(String codigo) throws Exception {
		verificarPrestamoExistente(codigo);
		return buscarPrestamo(codigo);
	}
	public List<Prestamo> obtenerListadoPrestamos() {
		return prestamos;
	}
	public String hacerPrestamo(String personaId, String codigoItem) throws Exception {
		Persona persona = obtenerPersona(personaId);
		Item item = obtenerItem(codigoItem);
		if (item.isPrestado())
			throw new Exception("El item ya se encuentra prestado.");
		String codigo = "P-" + UUID.randomUUID().toString().substring(0, 8);
		Prestamo prestamo = new Prestamo(codigo, persona);
		prestamo.agregarItem(item);
		prestamos.add(prestamo);
		persona.agregarPrestamo(prestamo);
		guardarDatos();
		return codigo;
	}
	public void agregarItemPrestamo(String prestamoCod, String codigoItem) throws Exception {
		Prestamo prestamo = obtenerPrestamo(prestamoCod);
		Item item = obtenerItem(codigoItem);
		if (item.isPrestado())
			throw new Exception("El item ya se encuentra prestado.");
		prestamo.agregarItem(item);
		guardarDatos();
	}
	public void eliminarItemPrestamo(String prestamoCod, String codigoItem) throws Exception {
		Prestamo prestamo = obtenerPrestamo(prestamoCod);
		Item item = obtenerItem(codigoItem);
		if (!prestamo.getItems().contains(item))
			throw new Exception("El item no pertenece a este préstamo.");
		prestamo.quitarItem(item);
		guardarDatos();
	}
	public void retornarItem(String prestamoCod, String codigoItem) throws Exception {
		Prestamo prestamo = obtenerPrestamo(prestamoCod);
		Item item = obtenerItem(codigoItem);
		if (!prestamo.getItems().contains(item))
			throw new Exception("El item no pertenece a este préstamo.");
		prestamo.quitarItem(item);
		if (prestamo.getItems().isEmpty()) {
			prestamo.getPersona().quitarPrestamo(prestamo);
			prestamos.remove(prestamo);
		}
		guardarDatos();
	}
	public void finalizarPrestamo(String prestamoCod) throws Exception {
		Prestamo prestamo = obtenerPrestamo(prestamoCod);
		prestamo.finalizar();
		prestamo.getPersona().quitarPrestamo(prestamo);
		prestamos.remove(prestamo);
		guardarDatos();
	}
	public void agregarAlertaPrestamo(String prestamoCod, String mensaje, boolean recurrente, int dias) throws Exception {
		Prestamo prestamo = obtenerPrestamo(prestamoCod);
		prestamo.agregarAlerta(new Alerta(mensaje, recurrente, dias, prestamo));
		guardarDatos();
	}

	//  REPORTES 

	public String reporteUsuario(String personaId) throws Exception {
		Persona persona = obtenerPersona(personaId);

		StringBuilder sb = new StringBuilder();

		sb.append("Nombre: ").append(persona.getNombre()).append("\n");
		sb.append("Teléfono: ").append(persona.getTelefono()).append("\n");
		sb.append("Correo: ").append(persona.getCorreo()).append("\n");
		if (persona.getPrestamos().isEmpty()) {
			sb.append("Préstamos: Sin préstamos activos.\n");
		} else {
			for (Prestamo pr : persona.getPrestamos()) {
				sb.append("Préstamo [").append(pr.getCodigo()).append("]: ");
				for (int i = 0; i < pr.getItems().size(); i++) {
					Item it = pr.getItems().get(i);
					if (i > 0) {
						sb.append(", ");
					}
					sb.append(it.getCodigo())
					  .append(" - ")
					  .append(it.getNombre());
				}
				sb.append("\n");
			}
		}
		return sb.toString();
	}
	public String reporteItem(String itemNombre) throws Exception {
		Item item = obtenerItem(itemNombre);
		StringBuilder sb = new StringBuilder("=== Reporte de item ===\n\n");
		sb.append("Nombre: ").append(item.getNombre()).append("\n");
		sb.append("Descripcion: ").append(item.getDescripcion()).append("\n");
		sb.append("Tipo: ").append(item.getTipo().getNombre()).append("\n");
		sb.append("Prestado: ").append(item.isPrestado() ? "Si" : "No").append("\n");
		return sb.toString();
	}
	public String reporteCategoria(String categoriaNombre) throws Exception {
		Categoria categoria = obtenerCategoria(categoriaNombre);
		StringBuilder sb = new StringBuilder();
		sb.append("Categoría: ").append(categoria.getNombre()).append("\n");
		if (categoria.getItems().isEmpty()) {
			sb.append("Sin ítems registrados en esta categoría.\n");
		} else {
			for (Item it : categoria.getItems()) {
				sb.append("- ")
				  .append(it.getCodigo())
				  .append(" - ")
				  .append(it.getNombre())
				  .append(" | Prestado: ")
				  .append(it.isPrestado() ? "Sí" : "No")
				  .append("\n");
			}
		}
		return sb.toString();
	}
	public String reporteTipo(String tipoNombre) throws Exception {
		Tipo tipo = obtenerTipo(tipoNombre);
		StringBuilder sb = new StringBuilder();
		sb.append("Tipo: ").append(tipo.getNombre()).append("\n");
		if (tipo.getItems().isEmpty()) {
			sb.append("Sin ítems registrados en este tipo.\n");
		} else {
			for (Item it : tipo.getItems()) {
				sb.append("- ")
				  .append(it.getCodigo())
				  .append(" - ")
				  .append(it.getNombre())
				  .append(" | Prestado: ")
				  .append(it.isPrestado() ? "Sí" : "No")
				  .append("\n");
			}
		}
		return sb.toString();
	}
	public String reporteTodosUsuarios() throws Exception {
		List<Persona> lista = new ArrayList<Persona>(personas);
		lista.sort(Comparator.comparing(Persona::getNombre));

		StringBuilder sb = new StringBuilder("=== Reporte por usuarios ===\n");

		for (Persona persona : lista) {
			sb.append("\nNombre: ").append(persona.getNombre()).append("\n");
			sb.append("Teléfono: ").append(persona.getTelefono()).append("\n");
			sb.append("Correo: ").append(persona.getCorreo()).append("\n");

			if (persona.getPrestamos().isEmpty()) {
				sb.append("Préstamos: Sin préstamos activos.\n");
			} else {
				for (Prestamo pr : persona.getPrestamos()) {
					sb.append("Préstamo [").append(pr.getCodigo()).append("]: ");

					for (Item it : pr.getItems()) {
						sb.append(it.getNombre()).append(", ");
					}

					sb.append("\n");
				}
			}

			sb.append("----------------------------------------\n");
		}

		return sb.toString();
	}

	public String reporteTodosItems() throws Exception {
		List<Item> lista = new ArrayList<Item>(items);
		lista.sort(Comparator.comparing(Item::getNombre));

		StringBuilder sb = new StringBuilder("=== Reporte por ítems ===\n");

		for (Item item : lista) {
			sb.append("\nCódigo: ").append(item.getCodigo()).append("\n");
			sb.append("Nombre: ").append(item.getNombre()).append("\n");
			sb.append("Descripción: ").append(item.getDescripcion()).append("\n");

			if (item.getTipo() != null) {
				sb.append("Tipo: ").append(item.getTipo().getNombre()).append("\n");
			} else {
				sb.append("Tipo: Sin tipo\n");
			}

			sb.append("Categorías: ");

			if (item.getCategorias().isEmpty()) {
				sb.append("Sin categorías");
			} else {
				for (Categoria c : item.getCategorias()) {
					sb.append(c.getNombre()).append(", ");
				}
			}

			sb.append("\n");
			sb.append("Prestado: ").append(item.isPrestado() ? "Sí" : "No").append("\n");

			if (item.isPrestado() && item.getPrestamo() != null) {
				sb.append("Usuario que lo tiene: ")
				  .append(item.getPrestamo().getPersona().getNombre())
				  .append("\n");
			} else {
				sb.append("Usuario que lo tiene: Ninguno\n");
			}

			sb.append("----------------------------------------\n");
		}

		return sb.toString();
	}

	public String reporteTodasCategorias() throws Exception {
		List<Categoria> lista = new ArrayList<Categoria>(categorias);
		lista.sort(Comparator.comparing(Categoria::getNombre));

		StringBuilder sb = new StringBuilder("=== Reporte por categorías ===\n");

		for (Categoria categoria : lista) {
			sb.append("\nCategoría: ").append(categoria.getNombre()).append("\n");

			if (categoria.getItems().isEmpty()) {
				sb.append("Sin ítems registrados en esta categoría.\n");
			} else {
				for (Item it : categoria.getItems()) {
					sb.append("- ")
					  .append(it.getNombre())
					  .append(" | Prestado: ")
					  .append(it.isPrestado() ? "Sí" : "No")
					  .append("\n");
				}
			}

			sb.append("----------------------------------------\n");
		}

		return sb.toString();
	}

	public String reporteTodosTipos() throws Exception {
		List<Tipo> lista = new ArrayList<Tipo>(tipos);
		lista.sort(Comparator.comparing(Tipo::getNombre));

		StringBuilder sb = new StringBuilder("=== Reporte por tipos ===\n");

		for (Tipo tipo : lista) {
			sb.append("\nTipo: ").append(tipo.getNombre()).append("\n");

			if (tipo.getItems().isEmpty()) {
				sb.append("Sin ítems registrados en este tipo.\n");
			} else {
				for (Item it : tipo.getItems()) {
					sb.append("- ")
					  .append(it.getNombre())
					  .append(" | Prestado: ")
					  .append(it.isPrestado() ? "Sí" : "No")
					  .append("\n");
				}
			}

			sb.append("----------------------------------------\n");
		}

		return sb.toString();
	}

	//  ALERTAS 

	public List<String> verificarAlertas() {
		List<String> mensajes = new ArrayList<String>();
		for (Prestamo p : prestamos) {
			if (p.tieneAlerta()) {
				Alerta a = p.getAlerta();
				if (a.debeActivarse()) {
					mensajes.add("Prestamo [" + p.getCodigo() + "] - " + p.getPersona().getNombre() + ": " + a.getMensaje());
					a.activar();
				}
			}
		}
		if (!mensajes.isEmpty())
			guardarDatos();
		return mensajes;
	} 

	//  PERSISTENCIA 

	public void guardarDatos() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_DATOS))) {
			oos.writeObject(this);
		} catch (IOException e) {
			System.err.println("Error al guardar: " + e.getMessage());
		}
	}
	public static void cargarDatos() {
		File archivo = new File(ARCHIVO_DATOS);
		if (archivo.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
				instance = (SistemaControl) ois.readObject();
				return;
			} catch (IOException | ClassNotFoundException e) {
				System.err.println("Iniciando sistema nuevo: " + e.getMessage());
			}
		}
		instance = new SistemaControl();
	}
}