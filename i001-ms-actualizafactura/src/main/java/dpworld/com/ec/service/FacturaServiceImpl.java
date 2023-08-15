package dpworld.com.ec.service;

import java.util.Arrays;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import dpworld.com.ec.client.ActiveMQProducerLogger;
import dpworld.com.ec.models.Factura;
import dpworld.com.ec.models.Response;
import org.apache.activemq.util.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class FacturaServiceImpl implements IFacturaService{

	@Autowired
	ActiveMQProducerLogger activeMQProducerLogger;
	
	public Factura facturaCobrar(Factura factura, String uuid) {

		StopWatch watch = new StopWatch();
		watch.restart();

		String request = this.convertirObjetToString(factura);
		System.out.println(request);

		try {

			activeMQProducerLogger.sendLogger(uuid, new Gson().toJson(factura), "https://fapidev.dpworld.com/amrlatmec/n4/fin/CreateARInvoice", "REQUEST N4INVOICES", "200", "0");

			var respuesta = WebClient.builder()
					.defaultHeaders(header -> header.setBasicAuth("zmsapi.consumer", "Dubai@2o21$"))
					.defaultHeaders(httpHeaders -> httpHeaders.setContentType(MediaType.APPLICATION_JSON))
					.build()
					.post()
					.uri("https://fapidev.dpworld.com/amrlatmec/n4/fin/CreateARInvoice")
					.body(Mono.just(factura), Factura.class)
					.retrieve()
					.bodyToMono(Response[].class)
					.block();

			activeMQProducerLogger.sendLogger(uuid, new Gson().toJson(respuesta), "https://fapidev.dpworld.com/amrlatmec/n4/fin/CreateARInvoice", "RESPONSE", "200", String.valueOf(watch.taken()));

			System.out.println("respuesta");
			System.out.println(Arrays.toString(respuesta));

		} catch (Exception e) {

			e.printStackTrace();
			activeMQProducerLogger.sendLogger(uuid, e.getMessage(), "https://fapidev.dpworld.com/amrlatmec/n4/fin/CreateARInvoice", "ERROR N4INVOICES", "400", String.valueOf(watch.taken()));

		}

		return factura;
	}

	private String convertirObjetToString(Object conectorAS400Entrada) {
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonIn = "";
		try {
			return objectMapper.writeValueAsString(conectorAS400Entrada);
		} catch (JsonProcessingException e) {
			return jsonIn;
		}
	}

}
