package dpworld.com.ec.models;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.List;

public class Factura {

	private List<Receivableinvoices> Receivableinvoices;

	@JsonGetter("Receivableinvoices")
	public List<dpworld.com.ec.models.Receivableinvoices> getReceivableinvoices() {
		return Receivableinvoices;
	}

	public void setReceivableinvoices(List<dpworld.com.ec.models.Receivableinvoices> receivableinvoices) {
		Receivableinvoices = receivableinvoices;
	}

}
