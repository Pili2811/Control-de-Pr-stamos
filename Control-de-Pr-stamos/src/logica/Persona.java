package logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Persona implements Serializable {
	private final String id;
	private String nombre;
	private String telefono;
	private String correo;
	private List<Prestamo> prestamos;
	public Persona(String nombre, String telefono, String correo) {
		this.id = UUID.randomUUID().toString();
		this.nombre = nombre;
		this.telefono = telefono;
		this.correo = correo;
		this.prestamos = new ArrayList<Prestamo>();
	}
	public String getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public List<Prestamo> getPrestamos() {
		return prestamos;
	}
	public boolean tienePrestamos() {
		return !prestamos.isEmpty();
	}
	public void agregarPrestamo(Prestamo prestamo) {
		prestamos.add(prestamo);
	}
	public void quitarPrestamo(Prestamo prestamo) {
		prestamos.remove(prestamo);
	}
	public String toString() {
		return nombre;
	}
}