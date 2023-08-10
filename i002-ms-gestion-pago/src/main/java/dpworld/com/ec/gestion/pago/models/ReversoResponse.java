package dpworld.com.ec.gestion.pago.models;

import com.fasterxml.jackson.annotation.JsonGetter;

public class ReversoResponse {
	
	private String codRespuesta;
	private String mensajeRespuesta;
	
	@JsonGetter("CodigoRespuesta")
	public String getCodRespuesta() {
		return codRespuesta;
	}
	public void setCodRespuesta(String codRespuesta) {
		this.codRespuesta = codRespuesta;
	}
	public String getMensajeRespuesta() {
		return mensajeRespuesta;
	}
	public void setMensajeRespuesta(String mensajeRespuesta) {
		this.mensajeRespuesta = mensajeRespuesta;
	}

}
