package dpworld.com.ec.gestion.pago.models;

public class Reverso {


	private String numeroTrx;
	private String fechaReverso;
	private String comentario;
	private String facturaNumero;
	private String empresa;
	private String uuid;

	
		
	public String getNumeroTrx() {
		return numeroTrx;
	}
	public void setNumeroTrx(String numeroTrx) {
		this.numeroTrx = numeroTrx;
	}
	public String getFechaReverso() {
		return fechaReverso;
	}
	public void setFechaReverso(String fechaReverso) {
		this.fechaReverso = fechaReverso;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getFacturaNumero() {
		return facturaNumero;
	}
	public void setFacturaNumero(String facturaNumero) {
		this.facturaNumero = facturaNumero;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
