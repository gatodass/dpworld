package dpworld.com.ec.gestion.pago.clientes;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


import org.springframework.stereotype.Component;


import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.MimeHeaders;
import jakarta.xml.soap.SOAPConnection;
import jakarta.xml.soap.SOAPConnectionFactory;
import jakarta.xml.soap.SOAPEnvelope;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.soap.SOAPPart;

@Component
public class ClienteConsulta {

	public ClienteConsulta() {
		// TODO Auto-generated constructor stub
	}

	public static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String myNamespace = "con";
		String myNamespaceURI = "http://www.dpworld.com/DPWorldOSB/TransformadorConsultarEC/ConsultarEC";

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

		
		try {
			soapMessage.writeTo(System.out);
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void callSoapWebService(String soapEndpointUrl, String soapAction) {
		try {
			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();
			System.out.println("--------------------------ANTES");
			// Send SOAP Message to SOAP Server
			//SOAPMessage soapResponse = 
					soapConnection.call(createSOAPRequest(soapAction), soapEndpointUrl);
			System.out.println("--------------------------DES");
			// Print the SOAP Response
			System.out.println("--------------------------Response SOAP Message:");
			//soapResponse.writeTo(System.out);
			System.out.println();

			soapConnection.close();
		} catch (Exception e) {
			System.err.println(
					"\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
			e.printStackTrace();
		}
	}

	public static SOAPMessage createSOAPRequest(String soapAction) throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();

		createSoapEnvelope(soapMessage);

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", soapAction);
		headers.addHeader("Authorization", "Basic " + getBasicAuthHeader());
		headers.addHeader("Content-Type", "text/xml");
		
		 
		soapMessage.saveChanges();

		/* Print the request message, just for debugging purposes */
		System.out.println("Request SOAP Message:");
		//soapMessage.writeTo(System.out);
		System.out.println("\n");

		return soapMessage;
	}

	private static String getBasicAuthHeader() {
		//DESARROLLO
		//String credentials = "amrlmsapi.consumer:LLB@D5fpzs#b";
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
	     
	     // You can play with response which is available as string now:
	     String finalvalue= response.toString();
	     System.out.println("RESPUESTA ++++        "+finalvalue);
	     // or you can parse/substring the required tag from response as below based your response code
	     
	  
	     
	     
	    // finalvalue= finalvalue.substring(finalvalue.indexOf("<response>")+10,finalvalue.indexOf("</response>")); 
	     
	     return finalvalue;
	     } 
	    catch (Exception e) {
	        return e.getMessage();
	    }   
	}
	
	

}
