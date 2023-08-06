package dpworld.com.ec.boton.pago.clientes;

import dpworld.com.ec.boton.pago.models.ResponseToken;
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

	public TokenPacifico() {
		// TODO Auto-generated constructor stub
	}

	public ResponseToken ejecutarToken(String soapEndpointUrl) throws Exception {

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

		String responseF = llamarSOAPString(soapRequest, soapEndpointUrl);

		responseF = responseF.substring(responseF.indexOf("<wes:autenticacionExternaResponse>"),
				responseF.indexOf("</soapenv:Body>"));

		responseF = responseF.substring(responseF.indexOf("<return>"),
				responseF.indexOf("</wes:autenticacionExternaResponse>"));

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
			throw new Exception(e.getMessage());
		}

		ResponseToken consultaReponse = new ResponseToken();

		consultaReponse.setCodigoError(codigo);
		consultaReponse.setMensajeError(mensaje);
		consultaReponse.setToken(token);

		return consultaReponse;
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

}
