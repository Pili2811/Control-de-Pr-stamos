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
	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
		}
	}
	public void quitarItem(Item item) {
		items.remove(item);
		item.setPrestado(false);
	}
	public void agregarAlerta(Alerta alerta) {
		this.alerta = alerta;
	}
	public List<Alerta> alerta() {
		List<Alerta> lista = new ArrayList<Alerta>();
		if (alerta != null) {
			lista.add(alerta);
		}
		return lista;
	}
	public boolean tieneAlerta() {
		return alerta != null && alerta.isActiva();
	}
	public void finalizar() {
		for (Item item : new ArrayList<Item>(items)) {
			item.setPrestado(false);
		}
		items.clear();
		if (alerta != null) {
			alerta.eliminar();
		}
		alerta = null;
	}
	public String toString() {
		return codigo + " - " + persona.getNombre();
	}
}