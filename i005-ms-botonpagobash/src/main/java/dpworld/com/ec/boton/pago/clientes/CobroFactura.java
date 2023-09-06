package dpworld.com.ec.boton.pago.clientes;

import com.google.gson.Gson;
import dpworld.com.ec.boton.pago.models.RequestPago;
import dpworld.com.ec.boton.pago.models.ResponsePago;
import org.apache.activemq.util.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class CobroFactura {

	Logger logger = LoggerFactory.getLogger(CobroFactura.class);

	@Autowired
	ActiveMQProducerLogger activeMQProducerLogger;

	@Value("${gestion.pago.url}")
	private String url;

	public CobroFactura() {
		// TODO Auto-generated constructor stub
	}

	public ResponsePago ejecutarCobroFactura(RequestPago requestPago) throws Exception {

		StopWatch watch = new StopWatch();
		watch.restart();

		activeMQProducerLogger.sendLogger(requestPago.getUuid(), new Gson().toJson(requestPago), url, "REQUEST GESTION PAGO", "200", "0");

		logger.info("REQUEST GESTION PAGO: " + new Gson().toJson(requestPago));

		URI uri;

		try {
			uri = new URI(url);
			HttpEntity<RequestPago> httpEntity = new HttpEntity<>(requestPago, IFacturaClientRest.httpHeaders());

			var respuesta = IFacturaClientRest.restTemplate().postForObject(uri, httpEntity, ResponsePago.class);

			activeMQProducerLogger.sendLogger(requestPago.getUuid(), new Gson().toJson(respuesta), url, "RESPONSE GESTION PAGO", "200", String.valueOf(watch.taken()));

			logger.info("RESPONSE GESTION PAGO: " + new Gson().toJson(respuesta));

			if(respuesta == null){
				throw new Exception("No hubo respuesta");
			}

			return respuesta;

		} catch (Exception e) {

			activeMQProducerLogger.sendLogger(requestPago.getUuid(), e.getMessage(), url, "ERROR GESTION PAGO", "400", String.valueOf(watch.taken()));

			logger.error("ERROR GESTION PAGO: " + e.getMessage());

			throw new Exception(e.getMessage());

		}

	}

}
