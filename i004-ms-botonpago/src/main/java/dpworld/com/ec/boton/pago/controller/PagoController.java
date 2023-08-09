package dpworld.com.ec.boton.pago.controller;

import com.google.gson.Gson;
import dpworld.com.ec.boton.pago.clientes.ActiveMQProducerLogger;
import dpworld.com.ec.boton.pago.models.RequestEmision;
import dpworld.com.ec.boton.pago.models.ResponseEmision;
import dpworld.com.ec.boton.pago.service.IPagosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping("/api/botonpago")
public class PagoController {

	@Autowired
	private IPagosService iPagosService;

	@Autowired
	ActiveMQProducerLogger activeMQProducerLogger;
	
	@PostMapping("/emision")
	public ResponseEmision emitirCobro(@Valid @RequestBody RequestEmision requestEmision) {

		String uuid = UUID.randomUUID().toString();
		requestEmision.setUuid(uuid);

		activeMQProducerLogger.sendLogger(uuid, new Gson().toJson(requestEmision), "/api/emision", "REQUEST");

		ResponseEmision responseEmision = iPagosService.emitirCobro(requestEmision);

		activeMQProducerLogger.sendLogger(uuid, new Gson().toJson(responseEmision), "/api/pago/consulta", "RESPONSE");

		return responseEmision;
	}
	
}
