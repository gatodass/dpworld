package dpworld.com.ec.boton.pago.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RequestEmision {

	@NotNull(message = "El campo TipoTransaccion es requerido")
	@Size(max=1,message="El campo TipoTransaccion es mayor a 1 caracter")
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
	@Size(max=10,message="El campo IdentificacionTipo es mayor a 10 caracteres")
	private String IdentificacionTipo;
	@Size(max=20,message="El campo IdentificacionTipo es mayor a 20 caracteres")
	private String IdentificacionNumero;
	@NotNull(message = "El campo BancoCodigo es requerido")
	@Size(max=3,message="El campo BancoCodigo es mayor a 3 caracteres")
	private String BancoCodigo;
	private String CuentaTipo;
	private String CuentaNumero;
	private String TokenTransaccional;
	private String codigoOtp;
	private String Nombre;
	private String empresa;
	private String uuid;

	@JsonGetter("tipoTransaccion")
	public String getTipoTransaccion() {
		return TipoTransaccion;
	}

	public void setTipoTransaccion(String tipoTransaccion) {
		TipoTransaccion = tipoTransaccion;
	}

	@JsonGetter("fechaPago")
	public String getFechaPago() {
		return FechaPago;
	}

	public void setFechaPago(String fechaPago) {
		FechaPago = fechaPago;
	}

	@JsonGetter("facturaNumero")
	public String getFacturaNumero() {
		return FacturaNumero;
	}

	public void setFacturaNumero(String facturaNumero) {
		FacturaNumero = facturaNumero;
	}

	@JsonGetter("fechaFactura")
	public String getFechaFactura() {
		return FechaFactura;
	}

	public void setFechaFactura(String fechaFactura) {
		FechaFactura = fechaFactura;
	}

	@JsonGetter("hostname")
	public String getHostname() {
		return Hostname;
	}

	public void setHostname(String hostname) {
		Hostname = hostname;
	}

	@JsonGetter("usuario")
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

	@JsonGetter("localidad")
	public String getLocalidad() {
		return Localidad;
	}

	public void setLocalidad(String localidad) {
		Localidad = localidad;
	}

	@JsonGetter("macAdress")
	public String getMacAdress() {
		return MacAdress;
	}

	public void setMacAdress(String macAdress) {
		MacAdress = macAdress;
	}

	@JsonGetter("idMensaje")
	public String getIdMensaje() {
		return IdMensaje;
	}

	public void setIdMensaje(String idMensaje) {
		IdMensaje = idMensaje;
	}

	@JsonGetter("monto")
	public String getMonto() {
		return Monto;
	}

	public void setMonto(String monto) {
		Monto = monto;
	}

	@JsonGetter("identificacionTipo")
	public String getIdentificacionTipo() {
		return IdentificacionTipo;
	}

	public void setIdentificacionTipo(String identificacionTipo) {
		IdentificacionTipo = identificacionTipo;
	}

	@JsonGetter("identificacionNumero")
	public String getIdentificacionNumero() {
		return IdentificacionNumero;
	}

	public void setIdentificacionNumero(String identificacionNumero) {
		IdentificacionNumero = identificacionNumero;
	}

	@JsonGetter("bancoCodigo")
	public String getBancoCodigo() {
		return BancoCodigo;
	}

	public void setBancoCodigo(String bancoCodigo) {
		BancoCodigo = bancoCodigo;
	}

	@JsonGetter("cuentaTipo")
	public String getCuentaTipo() {
		return CuentaTipo;
	}

	public void setCuentaTipo(String cuentaTipo) {
		CuentaTipo = cuentaTipo;
	}

	@JsonGetter("cuentaNumero")
	public String getCuentaNumero() {
		return CuentaNumero;
	}

	public void setCuentaNumero(String cuentaNumero) {
		CuentaNumero = cuentaNumero;
	}

	@JsonGetter("tokenTransaccional")
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

	@JsonGetter("nombre")
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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
