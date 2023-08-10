package dpworld.com.ec.service;

import dpworld.com.ec.models.Factura;
import dpworld.com.ec.models.Receivableinvoices;

public interface IFacturaService {
	
public Factura facturaCobrar(Factura factura, String uuid);

}
