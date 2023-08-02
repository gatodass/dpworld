package dpworld.com.ec.gestion.pago.models;

public class PagoResponse {

	private String codigoRespuesta;
	private String mensajeRespuesta;
	private String baseImponible;
	private String ivaBienes;
	private String ivaServicios;
	
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
	public String getBaseImponible() {
		return baseImponible;
	}
	public void setBaseImponible(String baseImponible) {
		this.baseImponible = baseImponible;
	}
	public String getIvaBienes() {
		return ivaBienes;
	}
	public void setIvaBienes(String ivaBienes) {
		this.ivaBienes = ivaBienes;
	}
	public String getIvaServicios() {
		return ivaServicios;
	}
	public void setIvaServicios(String ivaServicios) {
		this.ivaServicios = ivaServicios;
	}

	
	
}
