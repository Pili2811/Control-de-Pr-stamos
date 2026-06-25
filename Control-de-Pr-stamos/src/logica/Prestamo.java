package logica;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Prestamo implements Serializable {

	private String codigo;
	private LocalDate fecha;
	private Persona persona;
	private List<Item> items;
	private Alerta alerta;

	public Prestamo(String codigo, Persona persona) {
		this.codigo = codigo;
		this.fecha = LocalDate.now();
		this.persona = persona;
		this.items = new ArrayList<Item>();
		this.alerta = null;
	}

	public String getCodigo() {
		return codigo;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public Persona getPersona() {
		return persona;
	}

	public List<Item> getItems() {
		return items;
	}

	public Alerta getAlerta() {
		return alerta;
	}

	public void agregarItem(Item item) {
		if (!items.contains(item)) {
			items.add(item);
			item.setPrestado(true);
			item.setPrestamo(this);
		}
	}

	public void quitarItem(Item item) {
		if (items.contains(item)) {
			items.remove(item);
			item.setPrestado(false);
			item.setPrestamo(null);
		}
	}

	public void agregarAlerta(Alerta alerta) {
		this.alerta = alerta;
	}

	public boolean tieneAlerta() {
		return alerta != null && alerta.isActiva();
	}

	public void finalizar() {
		for (Item item : new ArrayList<Item>(items)) {
			item.setPrestado(false);
			item.setPrestamo(null);
		}

		items.clear();

		if (alerta != null) {
			alerta.eliminar();
		}

		alerta = null;
	}

	public int cantidadItems() {
		return items.size();
	}

	public String nombresItems() {
		StringBuilder sb = new StringBuilder();

		for (Item item : items) {
			if (sb.length() > 0) {
				sb.append(", ");
			}

			sb.append(item.getCodigo())
			  .append(" - ")
			  .append(item.getNombre());
		}

		return sb.toString();
	}

	public String toString() {
		return codigo + " - " + persona.getNombre();
	}
}