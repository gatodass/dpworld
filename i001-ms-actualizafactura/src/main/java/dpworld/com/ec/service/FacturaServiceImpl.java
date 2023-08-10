package dpworld.com.ec.service;

import java.net.URI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import dpworld.com.ec.client.ActiveMQProducer;
import dpworld.com.ec.client.ActiveMQProducerLogger;
import dpworld.com.ec.models.Factura;
import dpworld.com.ec.models.Response;
import org.apache.activemq.util.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import dpworld.com.ec.client.IFacturaClientRest;

@Service
public class FacturaServiceImpl implements IFacturaService{

	@Autowired
	private IFacturaClientRest iFacturaClientRest;

	@Autowired
	private ActiveMQProducer activeMQProducer;

	@Autowired
	ActiveMQProducerLogger activeMQProducerLogger;
	
	public Factura facturaCobrar(Factura factura, String uuid) {

		StopWatch watch = new StopWatch();
		watch.restart();

		String request = this.convertirObjetToString(factura);
		System.out.println(request);
		activeMQProducer.send("N4INVOICESREQUEST",request);

		URI uri;
		try {

			activeMQProducerLogger.sendLogger(uuid, new Gson().toJson(factura), "https://fapidev.dpworld.com/amrlatmec/n4/fin/CreateARInvoice", "REQUEST N4INVOICES", "200", "0");

			uri = new URI("https://fapidev.dpworld.com/amrlatmec/n4/fin/CreateARInvoice");
			HttpEntity<Factura> httpEntity = new HttpEntity<>(factura, iFacturaClientRest.httpHeaders());
	/*
	 * 		Factura objEmp = new Factura();
			objEmp.setName("cedss");
			objEmp.setCity("eeeee");
	 */

			var respuesta = iFacturaClientRest.restTemplate().postForObject(uri, httpEntity, Response[].class);

			activeMQProducerLogger.sendLogger(uuid, new Gson().toJson(respuesta), "https://fapidev.dpworld.com/amrlatmec/n4/fin/CreateARInvoice", "RESPONSE", "200", String.valueOf(watch.taken()));

			System.out.println("respuesta");
			System.out.println(respuesta);
			String response = this.convertirObjetToString(respuesta);
			activeMQProducer.send("N4INVOICESRESPONSE",response);

		} catch (Exception e) {
			// TODO Auto-generated catch block
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
