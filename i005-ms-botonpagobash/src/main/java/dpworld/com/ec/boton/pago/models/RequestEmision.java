package dpworld.com.ec.boton.pago.models;

import jakarta.validation.constraints.Size;

public class RequestEmision {

	@Size(max=20,message="El campo numeroDocumento es mayor a 20 caracteres")
	private String numeroDocumento;
	private String fechaHora;
	private String secuencia;
	private String monto;
	@Size(max=1,message="El campo tipoIdentifica es mayor a 1 caracter")
	private String tipoIdentifica;
	@Size(max=20,message="El campo numeroIdentifica es mayor a 20 caracteres")
	private String numeroIdentifica;
	@Size(max=3,message="El campo codigoBanco es mayor a 3 caracteres")
	private String codigoBanco;
	private String tipoCuenta;
	@Size(max=20,message="El campo numeroCuenta es mayor a 20 caracteres")
	private String numeroCuenta;
	@Size(max=5,message="El campo estado es mayor a 5 caracteres")
	private String estado;
	private String comentario;
	private String empresa;
	private String uuid;


	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(String fechaHora) {
		this.fechaHora = fechaHora;
	}

	public String getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(String secuencia) {
		this.secuencia = secuencia;
	}

	public String getMonto() {
		return monto;
	}

	public void setMonto(String monto) {
		this.monto = monto;
	}

	public String getTipoIdentifica() {
		return tipoIdentifica;
	}

	public void setTipoIdentifica(String tipoIdentifica) {
		this.tipoIdentifica = tipoIdentifica;
	}

	public String getNumeroIdentifica() {
		return numeroIdentifica;
	}

	public void setNumeroIdentifica(String numeroIdentifica) {
		this.numeroIdentifica = numeroIdentifica;
	}

	public String getCodigoBanco() {
		return codigoBanco;
	}

	public void setCodigoBanco(String codigoBanco) {
		this.codigoBanco = codigoBanco;
	}

	public String getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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
