package dpworld.com.ec.gestion.pago.controller;

import java.util.UUID;

import org.apache.activemq.util.StopWatch;
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

		StopWatch watch = new StopWatch();
		watch.restart();

		String uuid = UUID.randomUUID().toString();
		consulta.setUuid(uuid);

		activeMQProducerLogger.sendLogger(uuid, new Gson().toJson(consulta), "/api/pago/consulta", "REQUEST", "200", "0");
		
		ConsultaResponse consultaResponse = iPagosService.facturaCobrar(consulta);

		activeMQProducerLogger.sendLogger(uuid, new Gson().toJson(consultaResponse), "/api/pago/consulta", "RESPONSE", "200", String.valueOf(watch.taken()));
				
		return consultaResponse;
	}

	@PostMapping("/crea")
	public PagoResponse facturaPago(@RequestBody Pago pago) {

		StopWatch watch = new StopWatch();
		watch.restart();

		String uuid = UUID.randomUUID().toString();
		pago.setUuid(uuid);

		activeMQProducerLogger.sendLogger(uuid, new Gson().toJson(pago), "/api/pago/crea", "REQUEST", "200", "0");

		PagoResponse pagoResponse = iPagosService.facturaPago(pago);

		activeMQProducerLogger.sendLogger(uuid, new Gson().toJson(pagoResponse), "/api/pago/crea", "RESPONSE", "200", String.valueOf(watch.taken()));

		return pagoResponse;
	}

	@PostMapping("/reverso")
	public ReversoResponse facturaReverso(@RequestBody Reverso reverso) {

		StopWatch watch = new StopWatch();
		watch.restart();

		String uuid = UUID.randomUUID().toString();
		reverso.setUuid(uuid);

		activeMQProducerLogger.sendLogger(uuid, new Gson().toJson(reverso), "/api/pago/reverso", "REQUEST", "200", "0");

		ReversoResponse reversoResponse = iPagosService.facturaReverso(reverso);

		activeMQProducerLogger.sendLogger(uuid, new Gson().toJson(reversoResponse), "/api/pago/reverso", "RESPONSE", "200", String.valueOf(watch.taken()));

		return reversoResponse;
	}

}
