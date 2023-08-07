package dpworld.com.ec.boton.pago.clientes;

import dpworld.com.ec.boton.pago.models.RequestPago;
import dpworld.com.ec.boton.pago.models.ResponsePago;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class CobroFactura {

	public CobroFactura() {
		// TODO Auto-generated constructor stub
	}

	public ResponsePago ejecutarCobroFactura(RequestPago requestPago) throws Exception {

		URI uri;

		try {
			uri = new URI("http://localhost:6001/api/pago/crea");
			HttpEntity<RequestPago> httpEntity = new HttpEntity<>(requestPago, IFacturaClientRest.httpHeaders());

			var respuesta = IFacturaClientRest.restTemplate().postForObject(uri, httpEntity, ResponsePago.class);

			System.out.println("respuesta");
			System.out.println(respuesta);

			if(respuesta == null){
				throw new Exception("No hubo respuesta");
			}

			return respuesta;

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

}
