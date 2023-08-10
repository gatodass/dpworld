package dpworld.com.ec.modelo.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="log")
public class LoggerDp implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	private String uuid;
	private String timeStand;
	@Column(length = 10000)
	private String mensaje;
	private String peticion;
	private String url;
	private String status;
	private String tiempoEjecucion;
	private String nombrecomponente;
	
	public String getNombrecomponente() {
		return nombrecomponente;
	}
	public void setNombrecomponente(String nombrecomponente) {
		this.nombrecomponente = nombrecomponente;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getTimeStand() {
		return timeStand;
	}
	public void setTimeStand(String timeStand) {
		this.timeStand = timeStand;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getPeticion() {
		return peticion;
	}
	public void setPeticion(String peticion) {
		this.peticion = peticion;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTiempoEjecucion() {
		return tiempoEjecucion;
	}

	public void setTiempoEjecucion(String tiempoEjecucion) {
		this.tiempoEjecucion = tiempoEjecucion;
	}
}
