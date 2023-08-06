package dpworld.com.ec.boton.pago.controller;

import dpworld.com.ec.boton.pago.models.RequestEmision;
import dpworld.com.ec.boton.pago.models.ResponseEmision;
import dpworld.com.ec.boton.pago.service.IPagosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/botonpago")
public class PagoController {

	@Autowired
	private IPagosService iPagosService;
	
	
	@PostMapping("/emision")
	public ResponseEmision emitirCobro(@RequestBody RequestEmision requestEmision) {
	return 	iPagosService.emitirCobro(requestEmision);
	}
	
}
