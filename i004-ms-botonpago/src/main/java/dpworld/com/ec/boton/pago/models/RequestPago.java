package dpworld.com.ec.boton.pago.models;

public class RequestPago {
	
	private String tipotransaccion;
	private String facturaNumero;
	private String fechaFactura;
	private String fechaPago;
	private String monto;
	private String identificacionNumero;
	private String numeroTrx;
	private String comentario;
	private String empresa;
	private String uuid;
	
	public String getTipotransaccion() {
		return tipotransaccion;
	}
	public void setTipotransaccion(String tipotransaccion) {
		this.tipotransaccion = tipotransaccion;
	}
	public String getFacturaNumero() {
		return facturaNumero;
	}
	public void setFacturaNumero(String facturaNumero) {
		this.facturaNumero = facturaNumero;
	}
	public String getFechaFactura() {
		return fechaFactura;
	}
	public void setFechaFactura(String fechaFactura) {
		this.fechaFactura = fechaFactura;
	}
	public String getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(String fechaPago) {
		this.fechaPago = fechaPago;
	}
	public String getMonto() {
		return monto;
	}
	public void setMonto(String monto) {
		this.monto = monto;
	}
	public String getIdentificacionNumero() {
		return identificacionNumero;
	}
	public void setIdentificacionNumero(String identificacionNumero) {
		this.identificacionNumero = identificacionNumero;
	}
	public String getNumeroTrx() {
		return numeroTrx;
	}
	public void setNumeroTrx(String numeroTrx) {
		this.numeroTrx = numeroTrx;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
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
