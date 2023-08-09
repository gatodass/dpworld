package dpworld.com.ec.gestion.pago.models;

public class Consulta {

	private String empresa;
	private String numFactura;
	private String uuid;

	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getEmpresa() {
		return empresa;
	}
	
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	
	public String getNumFactura() {
		return numFactura;
	}
	
	public void setNumFactura(String numFactura) {
		this.numFactura = numFactura;
	}


}
