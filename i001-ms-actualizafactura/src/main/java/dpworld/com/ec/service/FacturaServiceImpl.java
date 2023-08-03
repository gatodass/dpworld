package dpworld.com.ec.service;

import java.net.URI;
import java.net.URISyntaxException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dpworld.com.ec.models.Factura;
import dpworld.com.ec.models.Receivableinvoices;
import dpworld.com.ec.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import dpworld.com.ec.client.IFacturaClientRest;

@Service
public class FacturaServiceImpl implements IFacturaService{

	@Autowired
	private IFacturaClientRest iFacturaClientRest;
	
	public Factura facturaCobrar(Factura factura) {
		// TODO Auto-generated method stub
	
	
		URI uri;
		try {
			uri = new URI("https://fapidev.dpworld.com/amrlatmec/n4/fin/CreateARInvoice");
			HttpEntity<Factura> httpEntity = new HttpEntity<>(factura, iFacturaClientRest.httpHeaders());
	/*
	 * 		Factura objEmp = new Factura();
			objEmp.setName("cedss");
			objEmp.setCity("eeeee");
	 */

			var respuesta = iFacturaClientRest.restTemplate().postForObject(uri, httpEntity, Response[].class);

			System.out.println("respuesta");
			System.out.println(respuesta);

			String response = this.convertirObjetToString(respuesta);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
