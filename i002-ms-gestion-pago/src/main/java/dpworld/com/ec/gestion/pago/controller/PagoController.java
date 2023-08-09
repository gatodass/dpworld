package dpworld.com.ec.gestion.pago.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import dpworld.com.ec.gestion.pago.clientes.ActiveMQProducerLogger;
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

	@Autowired
	ActiveMQProducerLogger activeMQProducerLogger;
	

	@PostMapping("/consulta")
	public ConsultaResponse facturaCobrar(@RequestBody Consulta consulta) {

		String uuid = UUID.randomUUID().toString();
		consulta.setUuid(uuid);

		activeMQProducerLogger.sendLogger(uuid, new Gson().toJson(consulta), "/api/pago/consulta", "REQUEST");
		
		ConsultaResponse consultaResponse = iPagosService.facturaCobrar(consulta);

		activeMQProducerLogger.sendLogger(uuid, new Gson().toJson(consultaResponse), "/api/pago/consulta", "RESPONSE");
				
		return consultaResponse;
	}

	@PostMapping("/crea")
	public PagoResponse facturaPago(@RequestBody Pago pago) {

		String uuid = UUID.randomUUID().toString();
		pago.setUuid(uuid);

		activeMQProducerLogger.sendLogger(uuid, new Gson().toJson(pago), "/api/pago/crea", "REQUEST");

		PagoResponse pagoResponse = iPagosService.facturaPago(pago);

		activeMQProducerLogger.sendLogger(uuid, new Gson().toJson(pagoResponse), "/api/pago/crea", "RESPONSE");

		return pagoResponse;
	}

	@PostMapping("/reverso")
	public ReversoResponse facturaReverso(@RequestBody Reverso reverso) {

		String uuid = UUID.randomUUID().toString();
		reverso.setUuid(uuid);

		activeMQProducerLogger.sendLogger(uuid, new Gson().toJson(reverso), "/api/pago/reverso", "REQUEST");

		ReversoResponse reversoResponse = iPagosService.facturaReverso(reverso);

		activeMQProducerLogger.sendLogger(uuid, new Gson().toJson(reversoResponse), "/api/pago/reverso", "RESPONSE");

		return reversoResponse;
	}

}
