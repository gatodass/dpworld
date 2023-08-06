package dpworld.com.ec.boton.pago.clientes;

import dpworld.com.ec.boton.pago.models.RequestPago;
import dpworld.com.ec.boton.pago.models.ResponsePago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class CobroFactura {

	@Autowired
	private IFacturaClientRest iFacturaClientRest;

	public CobroFactura() {
		// TODO Auto-generated constructor stub
	}

	public ResponsePago ejecutarCobroFactura(RequestPago requestPago) throws Exception {

		URI uri;

		try {
			uri = new URI("http://localhost:6001/api/pago/crea");
			HttpEntity<RequestPago> httpEntity = new HttpEntity<>(requestPago, iFacturaClientRest.httpHeaders());

			var respuesta = iFacturaClientRest.restTemplate().postForObject(uri, httpEntity, ResponsePago.class);

			System.out.println("respuesta");
			System.out.println(respuesta);

			return respuesta;

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

}
