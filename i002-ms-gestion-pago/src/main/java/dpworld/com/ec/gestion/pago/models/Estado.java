package dpworld.com.ec.gestion.pago.models;

import java.util.Date;

public class Estado {
	private String codigo;
	private String mensaje;
	private Date hora;

	public Estado() {
		// TODO Auto-generated constructor stub
		setHora(new java.util.Date());
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Date getHora() {

		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}
}
