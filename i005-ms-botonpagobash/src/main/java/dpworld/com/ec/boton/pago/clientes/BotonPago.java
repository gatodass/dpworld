package dpworld.com.ec.boton.pago.clientes;

import com.google.gson.Gson;
import dpworld.com.ec.boton.pago.models.RequestBotonPago;
import dpworld.com.ec.boton.pago.models.ResponseBotonPago;
import org.apache.activemq.util.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class BotonPago {

	Logger logger = LoggerFactory.getLogger(BotonPago.class);

	@Autowired
	ActiveMQProducerLogger activeMQProducerLogger;

	@Value("${boton.pago.url}")
	private String url;

	public BotonPago() {
		// TODO Auto-generated constructor stub
	}

	public ResponseBotonPago ejecutarBotonPago(RequestBotonPago requestBotonPago) throws Exception {

		StopWatch watch = new StopWatch();
		watch.restart();

		activeMQProducerLogger.sendLogger(requestBotonPago.getUuid(), new Gson().toJson(requestBotonPago), url, "REQUEST BOTON PAGO", "200", "0");

		logger.info("REQUEST GESTION PAGO: " + new Gson().toJson(requestBotonPago));

		URI uri;

		try {
			uri = new URI(url);
			HttpEntity<RequestBotonPago> httpEntity = new HttpEntity<>(requestBotonPago, IFacturaClientRest.httpHeaders());

			var respuesta = IFacturaClientRest.restTemplate().postForObject(uri, httpEntity, ResponseBotonPago.class);

			activeMQProducerLogger.sendLogger(requestBotonPago.getUuid(), new Gson().toJson(respuesta), url, "RESPONSE BOTON PAGO", "200", String.valueOf(watch.taken()));

			logger.info("RESPONSE BOTON PAGO: " + new Gson().toJson(respuesta));

			if(respuesta == null){
				throw new Exception("No hubo respuesta");
			}

			return respuesta;

		} catch (Exception e) {

			activeMQProducerLogger.sendLogger(requestBotonPago.getUuid(), e.getMessage(), url, "ERROR BOTON PAGO", "400", String.valueOf(watch.taken()));

			logger.error("ERROR BOTON PAGO: " + e.getMessage());

			throw new Exception(e.getMessage());

		}

	}

}
