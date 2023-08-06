package dpworld.com.ec.boton.pago.service;

import dpworld.com.ec.boton.pago.models.RequestEmision;
import dpworld.com.ec.boton.pago.models.ResponseEmision;

public interface IPagosService {

	public ResponseEmision emitirCobro(RequestEmision requestEmision);

}
