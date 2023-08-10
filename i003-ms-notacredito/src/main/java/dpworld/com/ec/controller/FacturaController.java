package dpworld.com.ec.controller;

import dpworld.com.ec.models.Factura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import dpworld.com.ec.service.IFacturaService;

import java.util.UUID;

@RestController
public class FacturaController {
	@Autowired
	private IFacturaService facturaService;
	
	
	@PostMapping("/api/nota/creacion")
	public Factura facturaCobrar(@RequestBody Factura factura) {
		String uuid = UUID.randomUUID().toString();
		return 	facturaService.facturaCobrar(factura, uuid);
	}
	
}
