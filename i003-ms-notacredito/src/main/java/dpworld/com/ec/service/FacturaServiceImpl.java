package dpworld.com.ec.service;

import com.google.gson.Gson;
import dpworld.com.ec.client.ActiveMQProducerLogger;
import dpworld.com.ec.models.Factura;
import dpworld.com.ec.models.Response;
import org.apache.activemq.util.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class FacturaServiceImpl implements IFacturaService{

	Logger logger = LoggerFactory.getLogger(FacturaServiceImpl.class);

	@Autowired
	ActiveMQProducerLogger activeMQProducerLogger;

	@Value("${dpworld.url.n4}")
	private String urlN4;

	@Value("${dpworld.url.geko}")
	private String urlGeko;

	public Factura facturaCobrar(Factura factura, String uuid) {

		StopWatch watch = new StopWatch();
		watch.restart();

		try {

			String url = urlN4;

			if(!factura.getReceivableinvoices().get(0).getSource().equals("N4")){
				url = urlGeko;
			}

			activeMQProducerLogger.sendLogger(uuid, this.convertirVariablesRequest(new Gson().toJson(factura)), url, "REQUEST N4CREDITNOTES", "200", "0");

			logger.info("REQUEST N4CREDITNOTES: " + this.convertirVariablesRequest(new Gson().toJson(factura)));

			var respuesta = WebClient.builder()
					.defaultHeaders(header -> header.setBasicAuth("amrlmsapi.consumer", "LLB@D5fpzs#b"))
					.defaultHeaders(httpHeaders -> httpHeaders.setContentType(MediaType.APPLICATION_JSON))
					.build()
					.post()
					.uri(url)
					.body(Mono.just(factura), Factura.class)
					.retrieve()
					.bodyToMono(Response[].class)
					.block();

			activeMQProducerLogger.sendLogger(uuid, new Gson().toJson(respuesta), url, "RESPONSE", "200", String.valueOf(watch.taken()));

			logger.info("RESPONSE: " + new Gson().toJson(respuesta));

		} catch (Exception e) {

			activeMQProducerLogger.sendLogger(uuid, e.getMessage(), "https://fapiuat.dpworld.com/amrlatmec/fin/n4/CreateARInvoice", "ERROR N4INVOICES", "400", String.valueOf(watch.taken()));

			logger.error("ERROR N4CREDITNOTES: " + e.getMessage());

		}

		return factura;
	}

	private String convertirVariablesRequest(String request) {

		return request.replace("AmountApplied","AmountApplied(for CM use only)")
				.replace("ApplyDate", "ApplyDate(for CM use only)")
				.replace("ParentInvoiceTrxNumber", "ParentInvoiceTrxNumber(for CM use only)")
				.replace("ParentInvoiceTrxType", "ParentInvoiceTrxType(for CM use only)");

	}

}
