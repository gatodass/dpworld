package dpworld.com.ec.boton.pago.clientes;

import dpworld.com.ec.boton.pago.models.RequestEmision;
import dpworld.com.ec.boton.pago.models.ResponseGenerarOrden;
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

	public GenerarOrdenInterbancaria() {
		// TODO Auto-generated constructor stub
	}

	public ResponseGenerarOrden ejecutarOrdenInterbancaria(RequestEmision requestEmision, String soapEndpointUrl) throws Exception {

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
				"                <pet:canal>WEB</pet:canal>\n" +
				"                <pet:fechaHora>" + requestEmision.getFechaPago() + "</pet:fechaHora>\n" +
				"                <pet:hostName>" + requestEmision.getHostname() + "</pet:hostName>\n" +
				"                <pet:idMensaje>" + requestEmision.getIdMensaje() + "</pet:idMensaje>\n" +
				"                <pet:idUsuario>" + requestEmision.getUsuario() + "</pet:idUsuario>\n" +
				"                <pet:ip>" + requestEmision.getIp() + "</pet:ip>\n" +
				"                <pet:localidad>" + requestEmision.getLocalidad() + "</pet:localidad>\n" +
				"                <pet:macAddress>" + requestEmision.getMacAdress() + "</pet:macAddress>\n" +
				"                <pet:token>" + requestEmision.getTokenTransaccional() + "</pet:token>\n" +
				"                <codigoBanco>" + requestEmision.getBancoCodigo() + "</codigoBanco>\n" +
				"                <formasPago>\n" +
				"                    <ent:codigo/>\n" +
				"                    <ent:codigoBancoCheque/>\n" +
				"                    <ent:numeroCheque/>\n" +
				"                    <ent:numeroCuenta/>\n" +
				"                    <ent:numeroCuentaCheque/>\n" +
				"                    <ent:numeroTarjeta/>\n" +
				"                    <ent:referencia/>\n" +
				"                    <ent:tipoCuenta/>\n" +
				"                    <ent:valor>" + requestEmision.getMonto() + "</ent:valor>\n" +
				"                </formasPago>\n" +
				"                <moneda/>\n" +
				"                <nombreEmpresa>" + this.obtenerNombreEmpresa(requestEmision.getTipoTransaccion()) + "</nombreEmpresa>\n" +
				"                <nombreTercero/>\n" +
				"                <numeroCuenta>" + requestEmision.getCuentaNumero() + "</numeroCuenta>\n" +
				"                <numeroIdentificacion>" + requestEmision.getIdentificacionNumero() + "</numeroIdentificacion>\n" +
				"                <servicio>" + this.obtenerServicio(requestEmision.getTipoTransaccion()) + "</servicio>\n" +
				"                <tipoCuenta>" + requestEmision.getCuentaTipo() + "</tipoCuenta>\n" +
				"                <tipoIdentificacion>" + requestEmision.getIdentificacionTipo() + "</tipoIdentificacion>\n" +
				"            </peticion>\n" +
				"        </wes:generarOrden>\n" +
				"    </soapenv:Body>\n" +
				"</soapenv:Envelope>";

		String responseF = llamarSOAPString(crearOrden, soapEndpointUrl);

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
			return "ZF";
		}
		return "DX";
	}

}
