package dpworld.com.ec.gestion.pago.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dpworld.com.ec.gestion.pago.models.Consulta;
import dpworld.com.ec.gestion.pago.models.ConsultaResponse;
import dpworld.com.ec.gestion.pago.models.Pago;
import dpworld.com.ec.gestion.pago.models.PagoResponse;
import dpworld.com.ec.gestion.pago.models.Reverso;
import dpworld.com.ec.gestion.pago.models.ReversoResponse;
import dpworld.com.ec.gestion.pago.service.IPagosService;



@RestController
@RequestMapping("/api/pago")
public class PagoController {

	@Autowired
	private IPagosService iPagosService;
	
	
	@PostMapping("/consulta")
	public ConsultaResponse facturaCobrar(@RequestBody Consulta consulta) {
	return 	iPagosService.facturaCobrar(consulta);
	}
	
	@PostMapping("/crea")
	public PagoResponse facturaPago(@RequestBody Pago pago) {
	return 	iPagosService.facturaPago(pago);
	}
	
	@PostMapping("/reverso")
	public ReversoResponse facturaReverso(@RequestBody Reverso reverso) {
	return 	iPagosService.facturaReverso(reverso);
	}
	
}
