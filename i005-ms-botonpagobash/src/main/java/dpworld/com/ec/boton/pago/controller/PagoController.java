package dpworld.com.ec.boton.pago.controller;

import com.google.gson.Gson;
import dpworld.com.ec.boton.pago.clientes.ActiveMQProducerLogger;
import dpworld.com.ec.boton.pago.models.RequestEmision;
import dpworld.com.ec.boton.pago.models.ResponseEmision;
import dpworld.com.ec.boton.pago.service.IPagosService;
import jakarta.validation.Valid;
import org.apache.activemq.util.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping("/api/botonpagobash")
public class PagoController {

	Logger logger = LoggerFactory.getLogger(PagoController.class);

	@Autowired
	private IPagosService iPagosService;

	@Autowired
	ActiveMQProducerLogger activeMQProducerLogger;
	
	@PostMapping("/emision")
	public ResponseEmision emitirCobro(@Valid @RequestBody RequestEmision requestEmision) {

		StopWatch watch = new StopWatch();
		watch.restart();

		String uuid = UUID.randomUUID().toString();
		requestEmision.setUuid(uuid);

		activeMQProducerLogger.sendLogger(uuid, new Gson().toJson(requestEmision), "/api/emision", "REQUEST", "200", "0");

		logger.info("REQUEST: " + new Gson().toJson(requestEmision));

		ResponseEmision responseEmision = iPagosService.emitirCobro(requestEmision);

		activeMQProducerLogger.sendLogger(uuid, new Gson().toJson(responseEmision), "/api/pago/consulta", "RESPONSE", "200", String.valueOf(watch.taken()));

		logger.info("RESPONSE: " + new Gson().toJson(responseEmision));

		return responseEmision;
	}
	
}
