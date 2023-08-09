package dpworld.com.ec.boton.pago.clientes;

import com.google.gson.Gson;
import dpworld.com.ec.boton.pago.models.RequestPago;
import dpworld.com.ec.boton.pago.models.ResponsePago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class CobroFactura {

	@Autowired
	ActiveMQProducerLogger activeMQProducerLogger;

	@Value("${gestion.pago.url}")
	private String url;

	public CobroFactura() {
		// TODO Auto-generated constructor stub
	}

	public ResponsePago ejecutarCobroFactura(RequestPago requestPago) throws Exception {

		activeMQProducerLogger.sendLogger(requestPago.getUuid(), new Gson().toJson(requestPago), url, "REQUEST GESTION PAGO");

		URI uri;

		try {
			uri = new URI(url);
			HttpEntity<RequestPago> httpEntity = new HttpEntity<>(requestPago, IFacturaClientRest.httpHeaders());

			var respuesta = IFacturaClientRest.restTemplate().postForObject(uri, httpEntity, ResponsePago.class);

			activeMQProducerLogger.sendLogger(requestPago.getUuid(), new Gson().toJson(respuesta), url, "RESPONSE GESTION PAGO");

			System.out.println("respuesta");
			System.out.println(respuesta);

			if(respuesta == null){
				throw new Exception("No hubo respuesta");
			}

			return respuesta;

		} catch (Exception e) {
			activeMQProducerLogger.sendLogger(requestPago.getUuid(), e.getMessage(), url, "ERROR GESTION PAGO");
			throw new Exception(e.getMessage());
		}

	}

}
