package dpworld.com.ec.boton.pago.models;

import com.fasterxml.jackson.annotation.JsonSetter;

public class ResponseEmision {
	
	private String CodigoRespuesta;
	private String mensajeError;

	public String getCodigoRespuesta() {
		return CodigoRespuesta;
	}

	@JsonSetter("CodigoRespuesta")
	public void setCodigoRespuesta(String codigoRespuesta) {
		CodigoRespuesta = codigoRespuesta;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	@JsonSetter("mensajeError")
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

}
