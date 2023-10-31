package dpworld.com.ec.gestion.pago.service;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import dpworld.com.ec.gestion.pago.clientes.ActiveMQProducerLogger;
import org.apache.activemq.util.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import dpworld.com.ec.gestion.pago.clientes.ClienteConsulta;
import dpworld.com.ec.gestion.pago.models.Consulta;
import dpworld.com.ec.gestion.pago.models.ConsultaResponse;
import dpworld.com.ec.gestion.pago.models.Pago;
import dpworld.com.ec.gestion.pago.models.PagoResponse;
import dpworld.com.ec.gestion.pago.models.Reverso;
import dpworld.com.ec.gestion.pago.models.ReversoResponse;

@Service
public class PagosServiceImpl implements IPagosService {

	Logger logger = LoggerFactory.getLogger(PagosServiceImpl.class);

	@Autowired
	ClienteConsulta clienteConsulta;

	@Autowired
	ActiveMQProducerLogger activeMQProducerLogger;

	/*
	private static String soapEndpointUrlConsulta = "https://fapiuat.dpworld.com/amrlatmec/fin/bdp/GetARInvoice";
	private static String soapEndpointUrlPago = "https://fapiuat.dpworld.com/amrlatmec/fin/bdp/CreateApplyReceipt";
	private static String soapEndpointUrlReverso = "https://fapiuat.dpworld.com/amrlatmec/fin/bdp/ReverseReceipt";
	 */
	//PRODUCCION
	private static String soapEndpointUrlConsulta = "https://fapi.dpworld.com/amrlatmec/fin/bdp/GetARInvoice";
	private static String soapEndpointUrlPago = "https://fapi.dpworld.com/amrlatmec/fin/bdp/CreateApplyReceipt";
	private static String soapEndpointUrlReverso = "https://fapi.dpworld.com/amrlatmec/fin/bdp/ReverseReceipt";
	
	
	
	@Override
	public ConsultaResponse facturaCobrar(Consulta consulta) {
		// TODO Auto-generated method stub

		StopWatch watch = new StopWatch();
		watch.restart();

		String soapRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" \n"
				+ "xmlns:con=\"http://www.dpworld.com/DPWorldOSB/TransformadorConsultarEC/ConsultarEC\">"
				+ "<soapenv:Header/><soapenv:Body><con:runReport><con:reportRequest>"
				+ "<con:parameterNameValues><con:item>" + "<con:name>empresa</con:name>" + "<con:values>" + "<con:item>"
				+ consulta.getEmpresa() + "</con:item></con:values>" + "</con:item>" + "<con:item>"
				+ "<con:name>factura</con:name>" + "<con:values>" + "<con:item>" + consulta.getNumFactura()
				+ "</con:item>" + "</con:values></con:item>" + "</con:parameterNameValues><con:reportAbsolutePath/>"
				+ "</con:reportRequest><con:userID/><" + "con:password/></con:runReport>" + "</soapenv:Body>"
				+ "</soapenv:Envelope>";

		System.out.println(soapRequest);

		activeMQProducerLogger.sendLogger(consulta.getUuid(), soapRequest, soapEndpointUrlConsulta, "REQUEST SOAP", "200", "0");

		logger.info("REQUEST SOAP: " + soapRequest);

		String responseF = ClienteConsulta.llamarSOAPString(soapRequest, soapEndpointUrlConsulta);

		activeMQProducerLogger.sendLogger(consulta.getUuid(), responseF, soapEndpointUrlConsulta, "RESPONSE SOAP", "200", String.valueOf(watch.taken()));

		logger.info("RESPONSE SOAP: " + responseF);

		responseF = responseF.substring(responseF.indexOf("<nstrgmpr:runReportReturn>"),
				responseF.indexOf("</runReportResponse>"));
		String codigo = null, mensaje = null, identificacion = null;
		String valor = null, fecha = null, referencia = null, nombre = null;

		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource src = new InputSource();
			src.setCharacterStream(new StringReader(responseF));

			Document doc = builder.parse(src);
			codigo = doc.getElementsByTagName("nstrgmpr:CodigoRespuesta").item(0).getTextContent();
			mensaje = doc.getElementsByTagName("nstrgmpr:MensajeRespuesta").item(0).getTextContent();
			identificacion = doc.getElementsByTagName("nstrgmpr:NumIdentifica").item(0).getTextContent();
			valor = doc.getElementsByTagName("nstrgmpr:ValorFactura").item(0).getTextContent();
			fecha = doc.getElementsByTagName("nstrgmpr:FechaEmision").item(0).getTextContent();
			referencia = doc.getElementsByTagName("nstrgmpr:Referencia").item(0).getTextContent();
			nombre = doc.getElementsByTagName("nstrgmpr:NombreCliente").item(0).getTextContent();

		} catch (Exception e) {

			activeMQProducerLogger.sendLogger(consulta.getUuid(), e.getMessage(), soapEndpointUrlConsulta, "ERROR SOAP", "400", String.valueOf(watch.taken()));

			logger.error("ERROR SOAP: " + e.getMessage());

		}

		ConsultaResponse consultaReponse = new ConsultaResponse();

		consultaReponse.setCodigoRespuesta(codigo);
		consultaReponse.setMensajeRespuesta(mensaje);
		consultaReponse.setNumIdentifica(identificacion);
		consultaReponse.setValorFactura(valor);
		consultaReponse.setFechaEmision(fecha);
		consultaReponse.setReferencia(referencia);
		consultaReponse.setNombreCliente(nombre);

		return consultaReponse;
	}

	@Override
	public PagoResponse facturaPago(Pago pago) {
		// TODO Auto-generated method stub

		StopWatch watch = new StopWatch();
		watch.restart();

		pago.setComentario("PAGO REALIZADO - " + pago.getFacturaNumero());

		String soapRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
				+ "xmlns:i007=\"http://www.dpworld.com/I007_GestionFactura\">"
				+ "<soapenv:Header/><soapenv:Body><i007:cobrarFactura>" + "<i007:tipoTransaccion>"
				+ pago.getTipotransaccion() + "</i007:tipoTransaccion>"
				+ "<i007:facturaNumero>"
				+ pago.getFacturaNumero() + "</i007:facturaNumero>" + "<i007:fechaFactura>" + pago.getFechaFactura()
				+ "</i007:fechaFactura>" + "<i007:fechaPago>" + pago.getFechaPago() + "</i007:fechaPago>"
				+ "<i007:monto>" + pago.getMonto() + "</i007:monto>" + "<i007:identificacionNumero>"
				+ pago.getIdentificacionNumero() + "</i007:identificacionNumero>" + "<i007:numeroTrx>"
				+ pago.getNumeroTrx() + "</i007:numeroTrx><" + "i007:comentario>" + pago.getComentario()
				+ "</i007:comentario>" + "<i007:empresa>" + pago.getEmpresa() + "</i007:empresa>"
				+ "</i007:cobrarFactura>" + "</soapenv:Body></soapenv:Envelope>";

		System.out.println(soapRequest);

		activeMQProducerLogger.sendLogger(pago.getUuid(), soapRequest, soapEndpointUrlPago, "REQUEST SOAP", "200", "0");

		logger.info("REQUEST SOAP: " + soapRequest);

		String responseF = ClienteConsulta.llamarSOAPString(soapRequest, soapEndpointUrlPago);

		activeMQProducerLogger.sendLogger(pago.getUuid(), responseF, soapEndpointUrlPago, "RESPONSE SOAP", "200", String.valueOf(watch.taken()));

		logger.info("RESPONSE SOAP: " + responseF);

		PagoResponse pagoResponse = new PagoResponse();

		if (responseF.length() > 0) {
			responseF = responseF.substring(responseF.indexOf("<nstrgmpr:codigoRespuesta>"),
					responseF.indexOf("</cobrarFacturaResponse>"));

		}

		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource src = new InputSource();
			src.setCharacterStream(
					new StringReader("<cobrarFacturaResponse>" + responseF + "</cobrarFacturaResponse>"));

			Document doc = builder.parse(src);

			pagoResponse.setCodigoRespuesta(
					doc.getElementsByTagName("nstrgmpr:codigoRespuesta").item(0).getTextContent() == null ? ""
							: doc.getElementsByTagName("nstrgmpr:codigoRespuesta").item(0).getTextContent());
			pagoResponse.setMensajeRespuesta(
					doc.getElementsByTagName("nstrgmpr:mensajeRespuesta").item(0).getTextContent() == null ? ""
							: doc.getElementsByTagName("nstrgmpr:mensajeRespuesta").item(0).getTextContent());
			pagoResponse.setBaseImponible(
					doc.getElementsByTagName("nstrgmpr:baseImponible").item(0).getTextContent() == null ? ""
							: doc.getElementsByTagName("nstrgmpr:baseImponible").item(0).getTextContent());

			pagoResponse.setIvaBienes(doc.getElementsByTagName("nstrgmpr:ivaBienes").item(0) == null ? ""
					: doc.getElementsByTagName("nstrgmpr:ivaBienes").item(0).getTextContent());
			pagoResponse.setIvaServicios(doc.getElementsByTagName("nstrgmpr:ivaServicios").item(0) == null ? ""
					: doc.getElementsByTagName("nstrgmpr:ivaServicios").item(0).getTextContent());

		} catch (Exception e) {

			activeMQProducerLogger.sendLogger(pago.getUuid(), e.getMessage(), soapEndpointUrlPago, "ERROR SOAP", "400", String.valueOf(watch.taken()));

			logger.error("ERROR SOAP: " + e.getMessage());

		}

		return pagoResponse;
	}

	@Override
	public ReversoResponse facturaReverso(Reverso reverso) {
		// TODO Auto-generated method stub

		StopWatch watch = new StopWatch();
		watch.restart();

		String soapRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
				+ "xmlns:rev=\"http://www.dpworld.com/ReversarFactura\">" + "<soapenv:Header/><soapenv:Body>"
				+ "<rev:reversarFacturas>" + "<rev:numeroTrx>" + reverso.getNumeroTrx() + "</rev:numeroTrx>"
				+ "<rev:fechaReverso>" + reverso.getFechaReverso() + "</rev:fechaReverso>" + "<rev:comentario>"
				+ reverso.getComentario() + "</rev:comentario>" + "<rev:facturaNumero>" + reverso.getFacturaNumero()
				+ "</rev:facturaNumero>" + "<rev:empresa>" + reverso.getEmpresa() + "</rev:empresa>"
				+ "</rev:reversarFacturas></soapenv:Body></soapenv:Envelope>";

		System.out.println(soapRequest);

		activeMQProducerLogger.sendLogger(reverso.getUuid(), soapRequest, soapEndpointUrlReverso, "REQUEST SOAP", "200", "0");

		logger.info("REQUEST SOAP: " + soapRequest);

		String responseF = ClienteConsulta.llamarSOAPString(soapRequest, soapEndpointUrlReverso);

		activeMQProducerLogger.sendLogger(reverso.getUuid(), responseF, soapEndpointUrlReverso, "RESPONSE SOAP", "200", String.valueOf(watch.taken()));

		logger.info("RESPONSE SOAP: " + responseF);

		ReversoResponse reversoResponse = new ReversoResponse();

		if (responseF.length() > 0) {
			responseF = responseF.substring(responseF.indexOf("<nstrgmpr:CodRespuesta>"),
					responseF.indexOf("</reversarFacturasResponse>"));

		}

		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource src = new InputSource();
			src.setCharacterStream(
					new StringReader("<reversarFacturasResponse>" + responseF + "</reversarFacturasResponse>"));

			Document doc = builder.parse(src);

			reversoResponse.setCodRespuesta(
					doc.getElementsByTagName("nstrgmpr:CodRespuesta").item(0).getTextContent() == null ? ""
							: doc.getElementsByTagName("nstrgmpr:CodRespuesta").item(0).getTextContent());
			reversoResponse.setMensajeRespuesta(
					doc.getElementsByTagName("nstrgmpr:MensajeRespuesta").item(0).getTextContent() == null ? ""
							: doc.getElementsByTagName("nstrgmpr:MensajeRespuesta").item(0).getTextContent());

		} catch (Exception e) {

			activeMQProducerLogger.sendLogger(reverso.getUuid(), e.getMessage(), soapEndpointUrlReverso, "ERROR SOAP", "400", String.valueOf(watch.taken()));

			logger.error("ERROR SOAP: " + e.getMessage());

		}

		return reversoResponse;
	}

}
