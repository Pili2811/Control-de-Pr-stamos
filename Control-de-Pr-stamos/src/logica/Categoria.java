package logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Categoria implements Serializable {
	private String nombre;
	private List<Item> items;
	public Categoria(String nombre) {
		this.nombre = nombre;
		this.items = new ArrayList<Item>();
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<Item> getItems() {
		return items;
	}
	public void agregarItem(Item item) {
		if (!items.contains(item)) {
			items.add(item);
		}
	}
	public void quitarItem(Item item) {
		items.remove(item);
	}
	public String toString() {
		return nombre;
	}
}