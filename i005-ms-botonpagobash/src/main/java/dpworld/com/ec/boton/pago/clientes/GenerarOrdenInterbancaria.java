package dpworld.com.ec.boton.pago.clientes;

import dpworld.com.ec.boton.pago.models.RequestEmision;
import dpworld.com.ec.boton.pago.models.ResponseGenerarOrden;
import org.apache.activemq.util.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class GenerarOrdenInterbancaria {

	Logger logger = LoggerFactory.getLogger(GenerarOrdenInterbancaria.class);

	@Autowired
	ActiveMQProducerLogger activeMQProducerLogger;

	public GenerarOrdenInterbancaria() {
		// TODO Auto-generated constructor stub
	}

	public ResponseGenerarOrden ejecutarOrdenInterbancaria(RequestEmision requestEmision, String soapEndpointUrl) throws Exception {

		StopWatch watch = new StopWatch();
		watch.restart();

		//TODO REVISAR identificacionCodigoServicio	DOCUMENTONUMERO
		String crearOrden = "<soapenv:Envelope\n" +
				"    xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
				"    xmlns:wes=\"http://western.ifc.servicios.western.empresas.externos.bpe/\"\n" +
				"    xmlns:pet=\"http://peticiones.servicios.common.bpe/\"\n" +
				"    xmlns:ent=\"http://entidades.servicios.common.ocpgtw.bpe/\">\n" +
				"    <soapenv:Header/>\n" +
				"    <soapenv:Body>\n" +
				"        <wes:generarOrden>\n" +
				"            <peticion>\n" +
				"                <pet:agencia>0</pet:agencia>\n" +
				"                <pet:canal>INT</pet:canal>\n" +
				"                <pet:fechaHora>" + requestEmision.getFechaPago() + "</pet:fechaHora>\n" +
				"                <pet:hostName>" + requestEmision.getHostname() + "</pet:hostName>\n" +
				"                <pet:idMensaje>" + this.obtenerIdMensaje(requestEmision.getFechaPago()) + "</pet:idMensaje>\n" +
				"                <pet:idUsuario>" + (requestEmision.getUsuario().isEmpty() ? "USRDPWORLD" : requestEmision.getUsuario()) + "</pet:idUsuario>\n" +
				"                <pet:ip>" + "10.24.1.189" + "</pet:ip>\n" +
				"                <pet:localidad>" + "1" + "</pet:localidad>\n" +
				"                <pet:macAddress>" + "00-50-56-8E-39-E7" + "</pet:macAddress>\n" +
				"                <pet:token>" + requestEmision.getTokenTransaccional() + "</pet:token>\n" +
				"                <codigoBanco>" + requestEmision.getBancoCodigo() + "</codigoBanco>\n" +
				"                <formasPago>\n" +
				"                    <ent:codigo>CU</ent:codigo>\n" +
				"                    <ent:codigoBancoCheque>NULL</ent:codigoBancoCheque>\n" +
				"                    <ent:numeroCheque>NULL</ent:numeroCheque>\n" +
				"                    <ent:numeroCuenta>" + requestEmision.getCuentaNumero() + "</ent:numeroCuenta>\n" +
				"                    <ent:referencia>Pago Interbancario</ent:referencia>\n" +
				"                    <ent:tipoCuenta>" + requestEmision.getCuentaTipo() + "</ent:tipoCuenta>\n" +
				"                </formasPago>\n" +
				"                <identificacionCodigoServicio>" + "" + "</identificacionCodigoServicio>\n" +
				"                <identificacionServicio>" + requestEmision.getIdentificacionNumero() + "</identificacionServicio>\n" +
				"                <moneda>USD</moneda>\n" +
				"                <nombreEmpresa>" + this.obtenerNombreEmpresa(requestEmision.getEmpresa()) + "</nombreEmpresa>\n" +
				"                <nombreTercero>" + requestEmision.getNombre() + "</nombreTercero>\n" +
				"                <numeroCuenta>" + requestEmision.getCuentaNumero() + "</numeroCuenta>\n" +
				"                <numeroIdentificacion>" + requestEmision.getIdentificacionNumero() + "</numeroIdentificacion>\n" +
				"                <servicio>" + "CI" + "</servicio>\n" +
				"                <tipoActividad>" + this.obtenerTipoActividad(requestEmision.getCuentaTipo()) + "</tipoActividad>\n" +
				"                <tipoCuenta>" + requestEmision.getCuentaTipo() + "</tipoCuenta>\n" +
				"                <tipoIdentificacion>" + requestEmision.getIdentificacionTipo() + "</tipoIdentificacion>\n" +
				"                <valorBaseImponible>" + requestEmision.getMonto() + "</valorBaseImponible>\n" +
				"                <valorIva>" + "0" + "</valorIva>\n" +
				"                <valorTotal>" + requestEmision.getMonto() + "</valorTotal>\n" +
				"            </peticion>\n" +
				"        </wes:generarOrden>\n" +
				"    </soapenv:Body>\n" +
				"</soapenv:Envelope>";

		activeMQProducerLogger.sendLogger(requestEmision.getUuid(), crearOrden, soapEndpointUrl, "REQUEST GENERAR ORDEN PAGO", "200", "0");

		logger.info("REQUEST GENERAR ORDEN PAGO: " + crearOrden);

		String responseF = llamarSOAPString(crearOrden, soapEndpointUrl);

		activeMQProducerLogger.sendLogger(requestEmision.getUuid(), responseF, soapEndpointUrl, "RESPONSE GENERAR ORDEN PAGO", "200", String.valueOf(watch.taken()));

		logger.info("RESPONSE GENERAR ORDEN PAGO: " + responseF);

		responseF = responseF.substring(responseF.indexOf("<wes:generarOrdenResponse>"),
				responseF.indexOf("</soapenv:Body>"));

		responseF = responseF.substring(responseF.indexOf("<return>"),
				responseF.indexOf("</wes:generarOrdenResponse>"));

		try {

			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource src = new InputSource();
			src.setCharacterStream(new StringReader(responseF));

			Document doc = builder.parse(src);

			ResponseGenerarOrden responseGenerarOrden = new ResponseGenerarOrden();

			responseGenerarOrden.setCodigo(doc.getElementsByTagName("res:codigo").item(0).getTextContent());
		    responseGenerarOrden.setDescripcion(doc.getElementsByTagName("res:descripcion").item(0).getTextContent());
		    responseGenerarOrden.setDuracionTarea(doc.getElementsByTagName("res:duracionTarea").item(0).getTextContent());
		    responseGenerarOrden.setIdMensaje(doc.getElementsByTagName("res:idMensaje").item(0).getTextContent());
		    responseGenerarOrden.setTipo(doc.getElementsByTagName("res:tipo").item(0).getTextContent());
		    responseGenerarOrden.setCodigoRetornoCore(doc.getElementsByTagName("res:codigoRetornoCore").item(0).getTextContent());
		    responseGenerarOrden.setMensajeRetornoCore(doc.getElementsByTagName("res:codigoMensajeCore").item(0).getTextContent());
		    responseGenerarOrden.setNutCore(doc.getElementsByTagName("res:nutCore").item(0).getTextContent());
		    responseGenerarOrden.setFechaHoraCore(doc.getElementsByTagName("res:fechaHoraCore").item(0).getTextContent());

			return responseGenerarOrden;

		} catch (Exception e) {

			activeMQProducerLogger.sendLogger(requestEmision.getUuid(), e.getMessage(), soapEndpointUrl, "ERROR GENERAR ORDEN PAGO", "400", String.valueOf(watch.taken()));

			logger.error("ERROR GENERAR ORDEN PAGO: " + e.getMessage());

			throw new Exception(e.getMessage());

		}

	}

	private static String getBasicAuthHeader() {
		String credentials = "zmsapi.consumer:Dubai@2o21$";

		byte[] message = credentials.getBytes(StandardCharsets.UTF_8);
		return Base64.getEncoder().encodeToString(message);
	}
	
	public static String llamarSOAPString(String soapRequest, String url) {

		try {
			URL obj = new URL(url);
			 HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			 con.setRequestMethod("POST");
			 con.setRequestProperty("Content-Type","text/xml; charset=utf-8");
			 con.setRequestProperty("Authorization", "Basic " + getBasicAuthHeader());
			 con.setDoOutput(true);
			 DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			 System.out.println("Cliente REQUEST ------  "+soapRequest);
			 wr.writeBytes(soapRequest);
			 wr.flush();
			 wr.close();
			 String responseStatus = con.getResponseMessage();
			 System.out.println(responseStatus);
			 BufferedReader in = new BufferedReader(new InputStreamReader(
			 con.getInputStream()));
			 String inputLine;
			 StringBuffer response = new StringBuffer();
			 while ((inputLine = in.readLine()) != null) {
				 response.append(inputLine);
			 }
			 in.close();

			 String finalvalue= response.toString();
			 System.out.println("RESPUESTA ++++        "+finalvalue);

			 return finalvalue;

		} catch (Exception e) {
	        return e.getMessage();
	    }   
	}

	private String obtenerTipoActividad(String tipoTransaccion){
		if(tipoTransaccion.equalsIgnoreCase("P")){
			return "Portuaria";
		}
		return "Comercial";
	}

	private String obtenerNombreEmpresa(String empresa){
		switch (empresa){
			case "6204":
				return "DPWORDLD";
			case "6210":
				return "CENTROLOGD";
			case "6224":
				return "DP WORLD LOG";
			case "6209":
				return "DURANPORT";
			default:
				return "DPWORDLD";
		}
	}

	private String obtenerIdMensaje(String fechaPago){

		String idMensaje = fechaPago.replace("-","").replace("T","").replace(":","");

		try{
			return idMensaje.substring(0,14);
		}catch (Exception e){
			return idMensaje;
		}

	}

}
