package dpworld.com.ec.gestion.pago.models;

public class ConsultaResponse {
	
	private String codigoRespuesta;
	private String mensajeRespuesta;
	private String numIdentifica;
	private String valorFactura;
	private String fechaEmision;
	private String referencia;
	private String nombreCliente;

	

	public String getCodigoRespuesta() {
		return codigoRespuesta;
	}
	public void setCodigoRespuesta(String codigoRespuesta) {
		this.codigoRespuesta = codigoRespuesta;
	}
	public String getMensajeRespuesta() {
		return mensajeRespuesta;
	}
	public void setMensajeRespuesta(String mensajeRespuesta) {
		this.mensajeRespuesta = mensajeRespuesta;
	}
	public String getNumIdentifica() {
		return numIdentifica;
	}
	public void setNumIdentifica(String numIdentifica) {
		this.numIdentifica = numIdentifica;
	}
	public String getValorFactura() {
		return valorFactura;
	}
	public void setValorFactura(String valorFactura) {
		this.valorFactura = valorFactura;
	}
	public String getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
}
