package logica;

import java.io.Serializable;
import java.time.LocalDate;

public class Alerta implements Serializable {
	private String mensaje;
	private boolean recurrente;
	private int tiempo;
	private boolean activa;
	private LocalDate fechaActivacion;
	private Prestamo prestamo;
	public Alerta(String mensaje, boolean recurrente, int tiempo, Prestamo prestamo) {
		this.mensaje = mensaje;
		this.recurrente = recurrente;
		this.tiempo = tiempo;
		this.activa = true;
		this.fechaActivacion = LocalDate.now().plusDays(tiempo);
		this.prestamo = prestamo;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public boolean isRecurrente() {
		return recurrente;
	}
	public void setRecurrente(boolean recurrente) {
		this.recurrente = recurrente;
	}
	public int getTiempo() {
		return tiempo;
	}
	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}
	public boolean isActiva() {
		return activa;
	}
	public LocalDate getFechaActivacion() {
		return fechaActivacion;
	}
	public Prestamo getPrestamo() {
		return prestamo;
	}
	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}
	public boolean debeActivarse() {
		return activa && !LocalDate.now().isBefore(fechaActivacion);
	}
	public void activar() {
		if (recurrente)
			fechaActivacion = LocalDate.now().plusDays(tiempo);
		else
			activa = false;
	}
	public void eliminar() {
		activa = false;
	}
	public String toString() {
		return mensaje;
	}
}