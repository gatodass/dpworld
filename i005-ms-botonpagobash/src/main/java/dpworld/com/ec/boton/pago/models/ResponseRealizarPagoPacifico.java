package dpworld.com.ec.boton.pago.models;

public class ResponseRealizarPagoPacifico {
	
	private String codigo;
	private String descripcion;
	private String duracionTarea;
	private String idMensaje;
	private String tipo;
	private String codigoRetornoCore;
	private String mensajeRetornoCore;
	private String nutCore;
	private String fechaHoraCore;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDuracionTarea() {
		return duracionTarea;
	}

	public void setDuracionTarea(String duracionTarea) {
		this.duracionTarea = duracionTarea;
	}

	public String getIdMensaje() {
		return idMensaje;
	}

	public void setIdMensaje(String idMensaje) {
		this.idMensaje = idMensaje;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCodigoRetornoCore() {
		return codigoRetornoCore;
	}

	public void setCodigoRetornoCore(String codigoRetornoCore) {
		this.codigoRetornoCore = codigoRetornoCore;
	}

	public String getMensajeRetornoCore() {
		return mensajeRetornoCore;
	}

	public void setMensajeRetornoCore(String mensajeRetornoCore) {
		this.mensajeRetornoCore = mensajeRetornoCore;
	}

	public String getNutCore() {
		return nutCore;
	}

	public void setNutCore(String nutCore) {
		this.nutCore = nutCore;
	}

	public String getFechaHoraCore() {
		return fechaHoraCore;
	}

	public void setFechaHoraCore(String fechaHoraCore) {
		this.fechaHoraCore = fechaHoraCore;
	}
}
