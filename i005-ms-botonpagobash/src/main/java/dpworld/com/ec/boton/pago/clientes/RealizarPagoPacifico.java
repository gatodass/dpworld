package dpworld.com.ec.boton.pago.clientes;

import dpworld.com.ec.boton.pago.models.RequestEmision;
import dpworld.com.ec.boton.pago.models.ResponseRealizarPagoPacifico;
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
public class RealizarPagoPacifico {

	Logger logger = LoggerFactory.getLogger(RealizarPagoPacifico.class);

	@Autowired
	ActiveMQProducerLogger activeMQProducerLogger;

	public RealizarPagoPacifico() {
		// TODO Auto-generated constructor stub
	}

	public ResponseRealizarPagoPacifico ejecutarPago(RequestEmision requestEmision, String soapEndpointUrl) throws Exception {

		StopWatch watch = new StopWatch();
		watch.restart();

		String soapRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wes=\"http://western.ifc.servicios.western.empresas.externos.bpe/\" xmlns:pet=\"http://peticiones.servicios.common.bpe/\">\n" +
				"   <soapenv:Header/>\n" +
				"   <soapenv:Body>\n" +
				"      <wes:realizarPago>\n" +
				"         <peticion>\n" +
				"            <pet:agencia>0</pet:agencia>\n" +
				"            <pet:canal>WEB</pet:canal>\n" +
				"            <pet:fechaHora>" + requestEmision.getFechaPago() + "</pet:fechaHora>\n" +
				"            <pet:hostName>" + requestEmision.getHostname() + "</pet:hostName>\n" +
				"            <pet:idMensaje>" + requestEmision.getIdMensaje() + "</pet:idMensaje>         \n" +
				"            <pet:idUsuario>" + requestEmision.getUsuario() + "</pet:idUsuario>\n" +
				"            <pet:ip>" + requestEmision.getIp() + "</pet:ip>\n" +
				"            <pet:localidad>" + requestEmision.getLocalidad() + "</pet:localidad>\n" +
				"            <pet:macAddress>" + requestEmision.getMacAdress() + "</pet:macAddress>\n" +
				"            <pet:token>" + requestEmision.getTokenTransaccional() + "</pet:token>\n" +
				"            <codigoBanco>" + requestEmision.getMacAdress() + "</codigoBanco>\n" +
				"            <codigoOTP>" + requestEmision.getCodigoOtp() + "</codigoOTP>\n" +
				"            <identificacion>" + requestEmision.getIdentificacionNumero() + "</identificacion>\n" +
				"            <monto>" + requestEmision.getMonto() + "</monto>\n" +
				"            <nombreEmpresa>" + this.obtenerNombreEmpresa(requestEmision.getTipoTransaccion()) + "</nombreEmpresa>\n" +
				"            <numeroCuentaDebito>" + requestEmision.getCuentaNumero() + "</numeroCuentaDebito>\n" +
				"            <numeroDocumento>" + requestEmision.getFacturaNumero() + "</numeroDocumento>\n" +
				"            <producto>" + "4" + "</producto>\n" +
				"            <servicio>" + this.obtenerServicio(requestEmision.getTipoTransaccion()) + "</servicio>\n" +
				"            <tipoCuenta>" + requestEmision.getCuentaTipo() + "</tipoCuenta>\n" +
				"            <tipoIdentificacion>" + requestEmision.getIdentificacionTipo() + "</tipoIdentificacion>\n" +
				"            <tipoServicio>" +  this.obtenerServicioTipo(requestEmision.getTipoTransaccion()) + "</tipoServicio>\n" +
				"         </peticion>\n" +
				"      </wes:realizarPago>\n" +
				"   </soapenv:Body>\n" +
				"</soapenv:Envelope>";

		activeMQProducerLogger.sendLogger(requestEmision.getUuid(), soapRequest, soapEndpointUrl, "REQUEST REALIZAR PAGO PACIFICO", "200", "0");

		logger.info("REQUEST REALIZAR PAGO PACIFICO: " + soapRequest);

		String responseF = llamarSOAPString(soapRequest, soapEndpointUrl);

		activeMQProducerLogger.sendLogger(requestEmision.getUuid(), responseF, soapEndpointUrl, "RESPONSE REALIZAR PAGO PACIFICO", "200", String.valueOf(watch.taken()));

		logger.info("RESPONSE REALIZAR PAGO PACIFICO " + responseF);

		responseF = responseF.substring(responseF.indexOf("<wes:realizarPagoResponse>"),
				responseF.indexOf("</soapenv:Body>"));

		responseF = responseF.substring(responseF.indexOf("<return>"),
				responseF.indexOf("</wes:realizarPagoResponse>"));

		try {

			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource src = new InputSource();
			src.setCharacterStream(new StringReader(responseF));

			Document doc = builder.parse(src);

			ResponseRealizarPagoPacifico responseRealizarPagoPacifico = new ResponseRealizarPagoPacifico();

			responseRealizarPagoPacifico.setCodigo(doc.getElementsByTagName("res:codigo").item(0).getTextContent());
		    responseRealizarPagoPacifico.setDescripcion(doc.getElementsByTagName("res:descripcion").item(0).getTextContent());
		    responseRealizarPagoPacifico.setDuracionTarea(doc.getElementsByTagName("res:duracionTarea").item(0).getTextContent());
		    responseRealizarPagoPacifico.setIdMensaje(doc.getElementsByTagName("res:idMensaje").item(0).getTextContent());
		    responseRealizarPagoPacifico.setTipo(doc.getElementsByTagName("res:tipo").item(0).getTextContent());
		    responseRealizarPagoPacifico.setCodigoRetornoCore(doc.getElementsByTagName("res:codigoRetornoCore").item(0).getTextContent());
		    responseRealizarPagoPacifico.setMensajeRetornoCore(doc.getElementsByTagName("res:codigoMensajeCore").item(0).getTextContent());
		    responseRealizarPagoPacifico.setNutCore(doc.getElementsByTagName("res:nutCore").item(0).getTextContent());
		    responseRealizarPagoPacifico.setFechaHoraCore(doc.getElementsByTagName("res:fechaHoraCore").item(0).getTextContent());

			return responseRealizarPagoPacifico;

		} catch (Exception e) {

			activeMQProducerLogger.sendLogger(requestEmision.getUuid(), e.getMessage(), soapEndpointUrl, "ERROR REALIZAR PAGO PACIFICO", "400", String.valueOf(watch.taken()));

			logger.error("ERROR REALIZAR PAGO PACIFICO " + e.getMessage());

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

	private String obtenerServicioTipo(String tipoTransaccion){
		if(tipoTransaccion.equalsIgnoreCase("P")){
			return "01";
		}
		return "02";
	}

	private String obtenerNombreEmpresa(String tipoTransaccion){
		if(tipoTransaccion.equalsIgnoreCase("P")){
			return "CAE";
		}
		return "DPWORLD";
	}

	private String obtenerServicio(String tipoTransaccion){
		if(tipoTransaccion.equalsIgnoreCase("P")){
			return "DX";
		}
		return "ZF";
	}

}
