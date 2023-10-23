package dpworld.com.ec.boton.pago.clientes;

import com.google.gson.Gson;
import dpworld.com.ec.boton.pago.models.RequestEmision;
import dpworld.com.ec.boton.pago.models.ResponseRealizarPagoPacifico;
import dpworld.com.ec.boton.pago.models.Reverso;
import dpworld.com.ec.boton.pago.models.ReversoResponse;
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
public class ReversoCobroFactura{

	Logger logger = LoggerFactory.getLogger(ReversoCobroFactura.class);

	@Autowired
	ActiveMQProducerLogger activeMQProducerLogger;

	@Value("${dpworld.url.reverso}")
	private String url;

	public void reversoCobro(RequestEmision requestEmision, String uuid, ResponseRealizarPagoPacifico responseRealizarPagoPacifico) throws Exception {

		StopWatch watch = new StopWatch();
		watch.restart();

		try {

			Reverso reverso = this.armarReverso(requestEmision, responseRealizarPagoPacifico);

			activeMQProducerLogger.sendLogger(uuid, new Gson().toJson(reverso), url, "REQUEST REVERSO GESTION PAGO", "200", "0");

			logger.info("REQUEST REVERSO GESTION PAGO: " + new Gson().toJson(reverso));

			var reversoResponse = WebClient.builder()
					.defaultHeaders(httpHeaders -> httpHeaders.setContentType(MediaType.APPLICATION_JSON))
					.build()
					.post()
					.uri(url)
					.body(Mono.just(reverso), Reverso.class)
					.retrieve()
					.bodyToMono(ReversoResponse.class)
					.block();

			activeMQProducerLogger.sendLogger(uuid, new Gson().toJson(reversoResponse), url, "RESPONSE REVERSO GESTION PAGO", "200", String.valueOf(watch.taken()));

			logger.info("RESPONSE REVERSO GESTION PAGO: " + new Gson().toJson(reversoResponse));

		} catch (Exception e) {

			e.printStackTrace();
			activeMQProducerLogger.sendLogger(uuid, e.getMessage(), url, "ERROR REVERSO GESTION PAGO", "400", String.valueOf(watch.taken()));

			logger.error("ERROR REVERSO GESTION PAGO: " + e.getMessage());

			throw new Exception(e.getMessage());

		}

	}

	private Reverso armarReverso(RequestEmision requestEmision, ResponseRealizarPagoPacifico responseRealizarPagoPacifico) {

		Reverso reverso = new Reverso();

		reverso.setFacturaNumero(requestEmision.getFacturaNumero());

		String fecha = requestEmision.getFechaPago();
		if(requestEmision.getFechaPago().contains("T")){
			String[] fechaTimeStamp = requestEmision.getFechaPago().split("T");
			fecha = fechaTimeStamp[0];
		}
		reverso.setFechaReverso(fecha);

		reverso.setNumeroTrx(requestEmision.getIdMensaje());
		reverso.setComentario("PAGO INTERBANCARIO");

		if(responseRealizarPagoPacifico != null){

			reverso.setNumeroTrx(responseRealizarPagoPacifico.getNutCore());
			reverso.setComentario(responseRealizarPagoPacifico.getDescripcion());

			if(responseRealizarPagoPacifico.getNutCore().isEmpty() || responseRealizarPagoPacifico.getNutCore().isBlank()){
				reverso.setNumeroTrx(requestEmision.getIdMensaje());
			}

		}

		reverso.setEmpresa(requestEmision.getEmpresa());
		reverso.setUuid(requestEmision.getUuid());

		return reverso;

	}

}
