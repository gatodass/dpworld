package dpworld.com.ec.boton.pago.clientes;

import dpworld.com.ec.boton.pago.models.RequestEmision;
import dpworld.com.ec.boton.pago.models.ResponseToken;
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
public class TokenPacifico {

	Logger logger = LoggerFactory.getLogger(TokenPacifico.class);

	@Autowired
	ActiveMQProducerLogger activeMQProducerLogger;

	public TokenPacifico() {
		// TODO Auto-generated constructor stub
	}

	public ResponseToken ejecutarToken(RequestEmision requestEmision, String soapEndpointUrl) throws Exception {

		StopWatch watch = new StopWatch();
		watch.restart();
//DESARRROLLO CLAVE TOKEN CLAVE TOKEN USER USRDPWORLD PASS BPdpworld19
		
		//PRODUCCION CLAVE TOKEN USER USRDPWORLD PASS Pacifico2019
		String soapRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wes=\"http://western.ifc.servicios.western.empresas.externos.bpe/\">\n" +
				"   <soapenv:Header/>\n" +
				"   <soapenv:Body>\n" +
				"      <wes:autenticacionExterna>\n" +
				"         <peticion>\n" +
				"            <clave>Pacifico2019</clave>\n" +
				"            <idUsuario>USRDPWORLD</idUsuario>\n" +
				"            <maquina>00-50-56-8E-39-E7</maquina>\n" +
				"         </peticion>\n" +
				"      </wes:autenticacionExterna>\n" +
				"   </soapenv:Body>\n" +
				"</soapenv:Envelope>";

		activeMQProducerLogger.sendLogger(requestEmision.getUuid(), soapRequest, soapEndpointUrl, "REQUEST TOKEN", "200", "0");

		logger.info("REQUEST TOKEN: " + soapRequest);

		String responseF = llamarSOAPString(soapRequest, soapEndpointUrl);

		activeMQProducerLogger.sendLogger(requestEmision.getUuid(), responseF, soapEndpointUrl, "RESPONSE TOKEN", "200", String.valueOf(watch.taken()));

		logger.info("RESPONSE TOKEN: " + responseF);

		responseF = responseF.substring(responseF.indexOf("<return>"),
				responseF.indexOf("</tns:autenticacionExternaResponse>"));

		String codigo = null;
		String mensaje = null;
		String token = null;

		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource src = new InputSource();
			src.setCharacterStream(new StringReader(responseF));

			Document doc = builder.parse(src);
			codigo = doc.getElementsByTagName("codigoError").item(0).getTextContent();
			mensaje = doc.getElementsByTagName("mensajeError").item(0).getTextContent();
			token = doc.getElementsByTagName("token").item(0).getTextContent();

		} catch (Exception e) {

			activeMQProducerLogger.sendLogger(requestEmision.getUuid(), e.getMessage(), soapEndpointUrl, "ERROR TOKEN", "400", String.valueOf(watch.taken()));

			logger.error("ERROR TOKEN: " + e.getMessage());

			throw new Exception(e.getMessage());

		}

		ResponseToken consultaReponse = new ResponseToken();

		consultaReponse.setCodigoError(codigo);
		consultaReponse.setMensajeError(mensaje);
		consultaReponse.setToken(token);

		return consultaReponse;
	}

	private static String getBasicAuthHeader() {
		//
		//DESARROLLO
		//String credentials = "zmsapi.consumer:Dubai@2o21$";

		//PRODUCCION
		String credentials = "amrltm.ecapiconsumer:TPRDg2f7b#VW";
		
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

}
