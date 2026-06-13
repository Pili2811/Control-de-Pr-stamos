package control;

import logica.Alerta;
import logica.Categoria;
import logica.Item;
import logica.Persona;
import logica.Prestamo;
import logica.Tipo;

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

public class SistemaControl implements Serializable {
	private static final String ARCHIVO_DATOS = "datos.dat";
	private static final String TIPO_GENERICO = "Generico";
	private static SistemaControl instancia;
	private List<Item> items;
	private List<Persona> personas;
	private List<Prestamo> prestamos;
	private List<Tipo> tipos;
	private List<Categoria> categorias;
	private SistemaControl() {
		items = new ArrayList<Item>();
		personas = new ArrayList<Persona>();
		prestamos = new ArrayList<Prestamo>();
		tipos = new ArrayList<Tipo>();
		categorias = new ArrayList<Categoria>();
		tipos.add(new Tipo(TIPO_GENERICO));
	}
	public static SistemaControl getInstancia() {
		if (instancia == null) {
			instancia = cargarDatos();
		}
		return instancia;
	}
	public boolean crearItem(String nombre, String descripcion) {
		if (nombre == null || nombre.isBlank()) {
			return false;
		}
		Item item = new Item(nombre, descripcion, obtenerTipoGenerico());
		items.add(item);
		guardarDatos();
		return true;
	}
	public boolean modificarItem(String nombreActual, String nuevoNombre, String descripcion) {
		Item item = buscarItem(nombreActual);
		if (item == null) {
			return false;
		}
		item.setNombre(nuevoNombre);
		item.setDescripcion(descripcion);
		guardarDatos();
		return true;
	}
	public boolean borrarItem(String nombre) {
		Item item = buscarItem(nombre);
		if (item == null || item.isPrestado()) {
			return false;
		}
		item.desvincular();
		items.remove(item);
		guardarDatos();
		return true;
	}
	public Item buscarItem(String nombre) {
		if (nombre == null) {
			return null;
		}
		for (Item it : items) {
			if (it.getNombre().equalsIgnoreCase(nombre)) {
				return it;
			}
		}
		return null;
	}
	public List<Item> obtenerListadoItems() {
		return items;
	}
	public boolean asignarTipoItem(String nombreItem, String nombreTipo) {
		Item item = buscarItem(nombreItem);
		Tipo tipo = buscarTipo(nombreTipo);
		if (item == null || tipo == null) {
			return false;
		}
		item.setTipo(tipo);
		guardarDatos();
		return true;
	}
	public boolean agregarCategoriaAItem(String nombreItem, String nombreCategoria) {
		Item item = buscarItem(nombreItem);
		Categoria categoria = buscarCategoria(nombreCategoria);
		if (item == null || categoria == null) {
			return false;
		}
		item.agregarCategoria(categoria);
		guardarDatos();
		return true;
	}
	public boolean quitarCategoriaDeItem(String nombreItem, String nombreCategoria) {
		Item item = buscarItem(nombreItem);
		Categoria categoria = buscarCategoria(nombreCategoria);
		if (item == null || categoria == null) {
			return false;
		}
		item.quitarCategoria(categoria);
		guardarDatos();
		return true;
	}
	public String crearPersona(String nombre, String telefono, String correo) {
		if (nombre == null || nombre.isBlank()) {
			return null;
		}
		Persona persona = new Persona(nombre, telefono, correo);
		personas.add(persona);
		guardarDatos();
		return persona.getId();
	}
	public boolean modificarPersona(String id, String nombre, String telefono, String correo) {
		Persona persona = buscarPersona(id);
		if (persona == null) {
			return false;
		}
		persona.setNombre(nombre);
		persona.setTelefono(telefono);
		persona.setCorreo(correo);
		guardarDatos();
		return true;
	}
	public boolean borrarPersona(String id) {
		Persona persona = buscarPersona(id);
		if (persona == null || persona.tienePrestamos()) {
			return false;
		}
		personas.remove(persona);
		guardarDatos();
		return true;
	}
	public Persona buscarPersona(String id) {
		if (id == null) {
			return null;
		}
		for (Persona p : personas) {
			if (p.getId().equals(id)) {
				return p;
			}
		}
		return null;
	}
	public Persona buscarPersonaPorNombre(String nombre) {
		if (nombre == null) {
			return null;
		}
		for (Persona p : personas) {
			if (p.getNombre().equalsIgnoreCase(nombre)) {
				return p;
			}
		}
		return null;
	}
	public List<Persona> obtenerListadoPersonas() {
		return personas;
	}
	public boolean crearCategoria(String nombre) {
		if (nombre == null || nombre.isBlank() || buscarCategoria(nombre) != null) {
			return false;
		}
		categorias.add(new Categoria(nombre));
		guardarDatos();
		return true;
	}
	public boolean modificarCategoria(String nombreActual, String nuevoNombre) {
		Categoria categoria = buscarCategoria(nombreActual);
		if (categoria == null) {
			return false;
		}
		categoria.setNombre(nuevoNombre);
		guardarDatos();
		return true;
	}
	public boolean borrarCategoria(String nombre) {
		Categoria categoria = buscarCategoria(nombre);
		if (categoria == null) {
			return false;
		}
		for (Item item : new ArrayList<Item>(categoria.getItems())) {
			item.quitarCategoria(categoria);
		}
		categorias.remove(categoria);
		guardarDatos();
		return true;
	}
	public Categoria buscarCategoria(String nombre) {
		if (nombre == null) {
			return null;
		}
		for (Categoria c : categorias) {
			if (c.getNombre().equalsIgnoreCase(nombre)) {
				return c;
			}
		}
		return null;
	}
	public List<Categoria> obtenerListadoCategorias() {
		return categorias;
	}
	public boolean crearTipo(String nombre) {
		if (nombre == null || nombre.isBlank() || buscarTipo(nombre) != null) {
			return false;
		}
		tipos.add(new Tipo(nombre));
		guardarDatos();
		return true;
	}
	public boolean modificarTipo(String nombreActual, String nuevoNombre) {
		Tipo tipo = buscarTipo(nombreActual);
		if (tipo == null) {
			return false;
		}
		tipo.setNombre(nuevoNombre);
		guardarDatos();
		return true;
	}
	public boolean borrarTipo(String nombre) {
		Tipo tipo = buscarTipo(nombre);
		if (tipo == null || tipo.getNombre().equals(TIPO_GENERICO)) {
			return false;
		}
		Tipo generico = obtenerTipoGenerico();
		for (Item item : new ArrayList<Item>(tipo.getItems())) {
			item.setTipo(generico);
		}
		tipos.remove(tipo);
		guardarDatos();
		return true;
	}
	public Tipo buscarTipo(String nombre) {
		if (nombre == null) {
			return null;
		}
		for (Tipo t : tipos) {
			if (t.getNombre().equalsIgnoreCase(nombre)) {
				return t;
			}
		}
		return null;
	}
	public Tipo obtenerTipoGenerico() {
		for (Tipo t : tipos) {
			if (t.getNombre().equals(TIPO_GENERICO)) {
				return t;
			}
		}
		return tipos.get(0);
	}
	public List<Tipo> obtenerListadoTipos() {
		return tipos;
	}
	public String hacerPrestamo(String personaId, String itemNombre) {
		Persona persona = buscarPersona(personaId);
		Item item = buscarItem(itemNombre);
		if (persona == null || item == null || item.isPrestado()) {
			return null;
		}
		String codigo = "P-" + UUID.randomUUID().toString().substring(0, 8);
		Prestamo prestamo = new Prestamo(codigo, persona);
		prestamo.agregarItem(item);
		prestamos.add(prestamo);
		persona.agregarPrestamo(prestamo);
		guardarDatos();
		return codigo;
	}
	public boolean agregarItemPrestamo(String prestamoCod, String itemNombre) {
		Prestamo prestamo = buscarPrestamo(prestamoCod);
		Item item = buscarItem(itemNombre);
		if (prestamo == null || item == null || item.isPrestado()) {
			return false;
		}
		prestamo.agregarItem(item);
		guardarDatos();
		return true;
	}
	public boolean eliminarItemPrestamo(String prestamoCod, String itemNombre) {
		Prestamo prestamo = buscarPrestamo(prestamoCod);
		Item item = buscarItem(itemNombre);
		if (prestamo == null || item == null || !prestamo.getItems().contains(item)) {
			return false;
		}
		prestamo.quitarItem(item);
		guardarDatos();
		return true;
	}
	public boolean retornarItem(String prestamoCod, String itemNombre) {
		Prestamo prestamo = buscarPrestamo(prestamoCod);
		Item item = buscarItem(itemNombre);
		if (prestamo == null || item == null || !prestamo.getItems().contains(item)) {
			return false;
		}
		prestamo.quitarItem(item);
		if (prestamo.getItems().isEmpty()) {
			prestamo.getPersona().quitarPrestamo(prestamo);
			prestamos.remove(prestamo);
		}
		guardarDatos();
		return true;
	}
	public boolean finalizarPrestamo(String prestamoCod) {
		Prestamo prestamo = buscarPrestamo(prestamoCod);
		if (prestamo == null) {
			return false;
		}
		prestamo.finalizar();
		prestamo.getPersona().quitarPrestamo(prestamo);
		prestamos.remove(prestamo);
		guardarDatos();
		return true;
	}
	public boolean agregarAlertaPrestamo(String prestamoCod, String mensaje, boolean recurrente, int dias) {
		Prestamo prestamo = buscarPrestamo(prestamoCod);
		if (prestamo == null) {
			return false;
		}
		prestamo.agregarAlerta(new Alerta(mensaje, recurrente, dias));
		guardarDatos();
		return true;
	}
	public Prestamo buscarPrestamo(String codigo) {
		if (codigo == null) {
			return null;
		}
		for (Prestamo p : prestamos) {
			if (p.getCodigo().equalsIgnoreCase(codigo)) {
				return p;
			}
		}
		return null;
	}
	public List<Prestamo> obtenerListadoPrestamos() {
		return prestamos;
	}
	public String reporteUsuario(String personaId) {
		Persona persona = buscarPersona(personaId);
		if (persona == null) {
			return "Usuario no encontrado.";
		}
		StringBuilder sb = new StringBuilder("=== Reporte de usuario ===\n\n");
		sb.append("Nombre: ").append(persona.getNombre()).append("\n");
		sb.append("Telefono: ").append(persona.getTelefono()).append("\n");
		sb.append("Correo: ").append(persona.getCorreo()).append("\n\n");
		if (persona.getPrestamos().isEmpty()) {
			sb.append("Sin prestamos activos.\n");
		} else {
			for (Prestamo pr : persona.getPrestamos()) {
				sb.append("Prestamo [").append(pr.getCodigo()).append("]: ");
				for (Item it : pr.getItems()) {
					sb.append(it.getNombre()).append(", ");
				}
				sb.append("\n");
			}
		}
		return sb.toString();
	}
	public String reporteItem(String itemNombre) {
		Item item = buscarItem(itemNombre);
		if (item == null) {
			return "Item no encontrado.";
		}
		StringBuilder sb = new StringBuilder("=== Reporte de item ===\n\n");
		sb.append("Nombre: ").append(item.getNombre()).append("\n");
		sb.append("Descripcion: ").append(item.getDescripcion()).append("\n");
		sb.append("Tipo: ").append(item.getTipo().getNombre()).append("\n");
		sb.append("Prestado: ").append(item.isPrestado() ? "Si" : "No").append("\n");
		return sb.toString();
	}
	public String reporteCategoria(String categoriaNombre) {
		Categoria categoria = buscarCategoria(categoriaNombre);
		if (categoria == null) {
			return "Categoria no encontrada.";
		}
		StringBuilder sb = new StringBuilder("=== Reporte por categoria ===\n\n");
		sb.append("Categoria: ").append(categoria.getNombre()).append("\n");
		for (Item it : categoria.getItems()) {
			sb.append("  - ").append(it.getNombre()).append(" (Prestado: ").append(it.isPrestado() ? "Si" : "No").append(")\n");
		}
		return sb.toString();
	}
	public String reporteTipo(String tipoNombre) {
		Tipo tipo = buscarTipo(tipoNombre);
		if (tipo == null) {
			return "Tipo no encontrado.";
		}
		StringBuilder sb = new StringBuilder("=== Reporte por tipo ===\n\n");
		sb.append("Tipo: ").append(tipo.getNombre()).append("\n");
		for (Item it : tipo.getItems()) {
			sb.append("  - ").append(it.getNombre()).append(" (Prestado: ").append(it.isPrestado() ? "Si" : "No").append(")\n");
		}
		return sb.toString();
	}
	public String reporteTodosUsuarios() {
		List<Persona> lista = new ArrayList<Persona>(personas);
		lista.sort(Comparator.comparing(Persona::getNombre));
		StringBuilder sb = new StringBuilder("=== Reporte por usuarios ===\n\n");
		for (Persona p : lista) {
			sb.append(reporteUsuario(p.getId())).append("\n");
		}
		return sb.toString();
	}
	public String reporteTodosItems() {
		List<Item> lista = new ArrayList<Item>(items);
		lista.sort(Comparator.comparing(Item::getNombre));
		StringBuilder sb = new StringBuilder("=== Reporte por items ===\n\n");
		for (Item it : lista) {
			sb.append(reporteItem(it.getNombre())).append("\n");
		}
		return sb.toString();
	}
	public String reporteTodasCategorias() {
		List<Categoria> lista = new ArrayList<Categoria>(categorias);
		lista.sort(Comparator.comparing(Categoria::getNombre));
		StringBuilder sb = new StringBuilder("=== Reporte por categorias ===\n\n");
		for (Categoria c : lista) {
			sb.append(reporteCategoria(c.getNombre())).append("\n");
		}
		return sb.toString();
	}
	public String reporteTodosTipos() {
		List<Tipo> lista = new ArrayList<Tipo>(tipos);
		lista.sort(Comparator.comparing(Tipo::getNombre));
		StringBuilder sb = new StringBuilder("=== Reporte por tipos ===\n\n");
		for (Tipo t : lista) {
			sb.append(reporteTipo(t.getNombre())).append("\n");
		}
		return sb.toString();
	}
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
		if (!mensajes.isEmpty()) {
			guardarDatos();
		}
		return mensajes;
	}
	public void guardarDatos() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_DATOS))) {
			oos.writeObject(this);
		} catch (IOException e) {
			System.err.println("Error al guardar: " + e.getMessage());
		}
	}
	private static SistemaControl cargarDatos() {
		File archivo = new File(ARCHIVO_DATOS);
		if (archivo.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
				return (SistemaControl) ois.readObject();
			} catch (IOException | ClassNotFoundException e) {
				System.err.println("Iniciando sistema nuevo: " + e.getMessage());
			}
		}
		return new SistemaControl();
	}
}