package logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item implements Serializable {
	private String nombre;
	private String descripcion;
	private Tipo tipo;
	private List<Categoria> categorias;
	private boolean prestado;
	public Item(String nombre, String descripcion, Tipo tipo) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.tipo = tipo;
		this.categorias = new ArrayList<Categoria>();
		this.prestado = false;
		if (tipo != null) {
			tipo.agregarItem(this);
		}
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo nuevoTipo) {
		if (this.tipo != null) {
			this.tipo.quitarItem(this);
		}
		this.tipo = nuevoTipo;
		if (nuevoTipo != null) {
			nuevoTipo.agregarItem(this);
		}
	}
	public List<Categoria> getCategorias() {
		return categorias;
	}
	public boolean isPrestado() {
		return prestado;
	}
	public void setPrestado(boolean prestado) {
		this.prestado = prestado;
	}
	public void agregarCategoria(Categoria categoria) {
		if (!categorias.contains(categoria)) {
			categorias.add(categoria);
			categoria.agregarItem(this);
		}
	}
	public void quitarCategoria(Categoria categoria) {
		categorias.remove(categoria);
		categoria.quitarItem(this);
	}
	public void desvincular() {
		for (Categoria c : new ArrayList<Categoria>(categorias)) {
			c.quitarItem(this);
		}
		categorias.clear();
		if (tipo != null) {
			tipo.quitarItem(this);
		}
	}
	public String toString() {
		return nombre;
	}
}