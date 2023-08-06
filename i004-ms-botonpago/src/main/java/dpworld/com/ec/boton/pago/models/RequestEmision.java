package dpworld.com.ec.boton.pago.models;

import com.fasterxml.jackson.annotation.JsonGetter;

public class RequestEmision {

	private String TipoTransaccion;
	private String FechaPago;
	private String FacturaNumero;
	private String FechaFactura;
	private String Hostname;
	private String Usuario;
	private String ip;
	private String Localidad;
	private String MacAdress;
	private String IdMensaje;
	private String Monto;
	private String IdentificacionTipo;
	private String IdentificacionNumero;
	private String BancoCodigo;
	private String CuentaTipo;
	private String CuentaNumero;
	private String TokenTransaccional;
	private String codigoOtp;
	private String Nombre;
	private String empresa;

	@JsonGetter("TipoTransaccion")
	public String getTipoTransaccion() {
		return TipoTransaccion;
	}

	public void setTipoTransaccion(String tipoTransaccion) {
		TipoTransaccion = tipoTransaccion;
	}

	@JsonGetter("FechaPago")
	public String getFechaPago() {
		return FechaPago;
	}

	public void setFechaPago(String fechaPago) {
		FechaPago = fechaPago;
	}

	@JsonGetter("FacturaNumero")
	public String getFacturaNumero() {
		return FacturaNumero;
	}

	public void setFacturaNumero(String facturaNumero) {
		FacturaNumero = facturaNumero;
	}

	@JsonGetter("FechaFactura")
	public String getFechaFactura() {
		return FechaFactura;
	}

	public void setFechaFactura(String fechaFactura) {
		FechaFactura = fechaFactura;
	}

	@JsonGetter("Hostname")
	public String getHostname() {
		return Hostname;
	}

	public void setHostname(String hostname) {
		Hostname = hostname;
	}

	@JsonGetter("Usuario")
	public String getUsuario() {
		return Usuario;
	}

	public void setUsuario(String usuario) {
		Usuario = usuario;
	}

	@JsonGetter("ip")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@JsonGetter("Localidad")
	public String getLocalidad() {
		return Localidad;
	}

	public void setLocalidad(String localidad) {
		Localidad = localidad;
	}

	@JsonGetter("MacAdress")
	public String getMacAdress() {
		return MacAdress;
	}

	public void setMacAdress(String macAdress) {
		MacAdress = macAdress;
	}

	@JsonGetter("IdMensaje")
	public String getIdMensaje() {
		return IdMensaje;
	}

	public void setIdMensaje(String idMensaje) {
		IdMensaje = idMensaje;
	}

	@JsonGetter("Monto")
	public String getMonto() {
		return Monto;
	}

	public void setMonto(String monto) {
		Monto = monto;
	}

	@JsonGetter("IdentificacionTipo")
	public String getIdentificacionTipo() {
		return IdentificacionTipo;
	}

	public void setIdentificacionTipo(String identificacionTipo) {
		IdentificacionTipo = identificacionTipo;
	}

	@JsonGetter("IdentificacionNumero")
	public String getIdentificacionNumero() {
		return IdentificacionNumero;
	}

	public void setIdentificacionNumero(String identificacionNumero) {
		IdentificacionNumero = identificacionNumero;
	}

	@JsonGetter("BancoCodigo")
	public String getBancoCodigo() {
		return BancoCodigo;
	}

	public void setBancoCodigo(String bancoCodigo) {
		BancoCodigo = bancoCodigo;
	}

	@JsonGetter("CuentaTipo")
	public String getCuentaTipo() {
		return CuentaTipo;
	}

	public void setCuentaTipo(String cuentaTipo) {
		CuentaTipo = cuentaTipo;
	}

	@JsonGetter("CuentaNumero")
	public String getCuentaNumero() {
		return CuentaNumero;
	}

	public void setCuentaNumero(String cuentaNumero) {
		CuentaNumero = cuentaNumero;
	}

	@JsonGetter("TokenTransaccional")
	public String getTokenTransaccional() {
		return TokenTransaccional;
	}

	public void setTokenTransaccional(String tokenTransaccional) {
		TokenTransaccional = tokenTransaccional;
	}

	@JsonGetter("codigoOtp")
	public String getCodigoOtp() {
		return codigoOtp;
	}

	public void setCodigoOtp(String codigoOtp) {
		this.codigoOtp = codigoOtp;
	}

	@JsonGetter("Nombre")
	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	@JsonGetter("empresa")
	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
}
