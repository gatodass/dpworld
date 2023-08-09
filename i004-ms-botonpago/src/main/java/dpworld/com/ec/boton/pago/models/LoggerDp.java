package dpworld.com.ec.boton.pago.models;

public class LoggerDp {

	public LoggerDp(String uuid, String timeStand, String mensaje, String peticion, String url,
                    String nombrecomponente) {
		super();
		this.uuid = uuid;
		this.timeStand = timeStand;
		this.mensaje = mensaje;
		this.peticion = peticion;
		this.url = url;
		this.nombrecomponente = nombrecomponente;
	}
	private String uuid;
	private String timeStand;
	private String mensaje;
	private String peticion;
	private String url;
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

}
