package dpworld.com.ec.gestion.pago.controller;

import java.util.UUID;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${spring.application.name}")
	private String componente;
	

	@PostMapping("/consulta")
	public ConsultaResponse facturaCobrar(@RequestBody Consulta consulta) {
		String uuid = UUID.randomUUID().toString();
		consulta.setUuid("");
		
		try {
			activeMQProducerLogger.sendLogger(uuid, new Gson().toJson(consulta), "/api/pago/consulta", componente, "REQUEST");

		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
		
		ConsultaResponse consultaResponse = iPagosService.facturaCobrar(consulta);
		
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
			activeMQProducerLogger.sendLogger(uuid, new Gson().toJson(consultaResponse), "/api/pago/consulta", componente, "RESPONSE");

		}
				
		return consultaResponse;
	}

	@PostMapping("/crea")
	public PagoResponse facturaPago(@RequestBody Pago pago) {
		return iPagosService.facturaPago(pago);
	}

	@PostMapping("/reverso")
	public ReversoResponse facturaReverso(@RequestBody Reverso reverso) {
		return iPagosService.facturaReverso(reverso);
	}

}
