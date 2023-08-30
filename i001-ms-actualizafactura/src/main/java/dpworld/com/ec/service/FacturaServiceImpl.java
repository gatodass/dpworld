package dpworld.com.ec.service;

import com.google.gson.Gson;
import dpworld.com.ec.client.ActiveMQProducerLogger;
import dpworld.com.ec.models.Factura;
import dpworld.com.ec.models.Response;
import org.apache.activemq.util.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class FacturaServiceImpl implements IFacturaService{

	Logger logger = LoggerFactory.getLogger(FacturaServiceImpl.class);

	@Autowired
	ActiveMQProducerLogger activeMQProducerLogger;

	public Factura facturaCobrar(Factura factura, String uuid) {

		StopWatch watch = new StopWatch();
		watch.restart();

		try {

			activeMQProducerLogger.sendLogger(uuid, new Gson().toJson(factura), "https://fapiuat.dpworld.com/amrlatmec/fin/n4/CreateARInvoice", "REQUEST N4INVOICES", "200", "0");

			logger.info("REQUEST N4INVOICES: " + new Gson().toJson(factura));

			var respuesta = WebClient.builder()
					.defaultHeaders(header -> header.setBasicAuth("amrlmsapi.consumer", "LLB@D5fpzs#b"))
					.defaultHeaders(httpHeaders -> httpHeaders.setContentType(MediaType.APPLICATION_JSON))
					.build()
					.post()
					.uri("https://fapiuat.dpworld.com/amrlatmec/fin/n4/CreateARInvoice")
					.body(Mono.just(factura), Factura.class)
					.retrieve()
					.bodyToMono(Response[].class)
					.block();

			activeMQProducerLogger.sendLogger(uuid, new Gson().toJson(respuesta), "https://fapiuat.dpworld.com/amrlatmec/fin/n4/CreateARInvoice", "RESPONSE", "200", String.valueOf(watch.taken()));

			logger.info("RESPONSE: " + new Gson().toJson(respuesta));

		} catch (Exception e) {

			e.printStackTrace();
			activeMQProducerLogger.sendLogger(uuid, e.getMessage(), "https://fapiuat.dpworld.com/amrlatmec/fin/n4/CreateARInvoice", "ERROR N4INVOICES", "400", String.valueOf(watch.taken()));

			logger.error("ERROR N4INVOICES: " + e.getMessage());

		}

		return factura;
	}

}
