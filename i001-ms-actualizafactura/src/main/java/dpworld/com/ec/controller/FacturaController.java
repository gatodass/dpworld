package dpworld.com.ec.controller;

import dpworld.com.ec.models.Factura;
import dpworld.com.ec.models.Receivableinvoices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import dpworld.com.ec.service.IFacturaService;

@RestController
public class FacturaController {
	@Autowired
	private IFacturaService facturaService;
	
	
	@PostMapping("/api/factura/creacion")
	public Factura facturaCobrar(@RequestBody Factura factura) {
	return 	facturaService.facturaCobrar(factura);
	}
	
}
