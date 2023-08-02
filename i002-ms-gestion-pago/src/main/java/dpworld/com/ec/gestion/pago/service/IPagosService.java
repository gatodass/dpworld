package dpworld.com.ec.gestion.pago.service;

import dpworld.com.ec.gestion.pago.models.Consulta;
import dpworld.com.ec.gestion.pago.models.ConsultaResponse;
import dpworld.com.ec.gestion.pago.models.Pago;
import dpworld.com.ec.gestion.pago.models.PagoResponse;
import dpworld.com.ec.gestion.pago.models.Reverso;
import dpworld.com.ec.gestion.pago.models.ReversoResponse;

public interface IPagosService {

	public ConsultaResponse facturaCobrar(Consulta consulta);

	public PagoResponse facturaPago(Pago pago);

	public ReversoResponse facturaReverso(Reverso reverso);

}
