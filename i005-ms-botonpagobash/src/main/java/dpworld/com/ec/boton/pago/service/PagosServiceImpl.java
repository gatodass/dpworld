package dpworld.com.ec.boton.pago.service;

import dpworld.com.ec.boton.pago.clientes.BotonPago;
import dpworld.com.ec.boton.pago.clientes.CobroFactura;
import dpworld.com.ec.boton.pago.models.RequestBotonPago;
import dpworld.com.ec.boton.pago.models.RequestEmision;
import dpworld.com.ec.boton.pago.models.RequestPago;
import dpworld.com.ec.boton.pago.models.ResponseBotonPago;
import dpworld.com.ec.boton.pago.models.ResponseEmision;
import dpworld.com.ec.boton.pago.models.ResponsePago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagosServiceImpl implements IPagosService {

	@Autowired
	CobroFactura cobroFactura;

	@Autowired
	BotonPago botonPago;

	@Override
	public ResponseEmision emitirCobro(RequestEmision requestEmision) {

		try {

			if(!requestEmision.getEstado().equalsIgnoreCase("OK")){
				ResponseEmision responseEmision = new ResponseEmision();
				responseEmision.setCodigoRespuesta("1");
				responseEmision.setMensajeError(requestEmision.getEstado());
				return responseEmision;
			}

			ResponseEmision responseEmision = new ResponseEmision();
			responseEmision.setCodigoRespuesta("0");
			responseEmision.setMensajeError("Transaccion Exitosa");


			if(requestEmision.getNumeroDocumento().length() == 19){
				ejecucionBotonPago(requestEmision, responseEmision);
				return responseEmision;
			}

			ejecucionCobroFactura(requestEmision, responseEmision);

			return responseEmision;

		} catch (Exception e) {

			System.err.println("i005-ms-botonpagobash " + e);
			ResponseEmision responseEmision = new ResponseEmision();
			responseEmision.setCodigoRespuesta("1");
			responseEmision.setMensajeError(e.getMessage());
			return responseEmision;

		}

	}

	private void ejecucionCobroFactura(RequestEmision requestEmision, ResponseEmision responseEmision) throws Exception {

		ResponsePago responsePago = cobroFactura.ejecutarCobroFactura(this.armarPago(requestEmision));

		if(!responsePago.getCodigoRespuesta().equalsIgnoreCase("0")){
			responseEmision.setCodigoRespuesta(responsePago.getCodigoRespuesta());
			responseEmision.setMensajeError(responsePago.getMensajeRespuesta());
		}
	}

	private RequestPago armarPago(RequestEmision requestEmision){

		RequestPago requestPago = new RequestPago();

	    requestPago.setTipotransaccion("N");
	    requestPago.setFacturaNumero(requestEmision.getNumeroDocumento());

		String fecha = requestEmision.getFechaHora();
		if(requestEmision.getFechaHora().contains("T")){
			String[] fechaTimeStamp = requestEmision.getFechaHora().split("T");
			fecha = fechaTimeStamp[0];
		}

	    requestPago.setFechaFactura(fecha);
	    requestPago.setFechaPago(fecha);
	    requestPago.setMonto(requestEmision.getMonto());
	    requestPago.setIdentificacionNumero(requestEmision.getNumeroIdentifica());
		requestPago.setNumeroTrx(requestEmision.getSecuencia());
		requestPago.setComentario(requestEmision.getComentario());
	    requestPago.setEmpresa(requestEmision.getEmpresa());
	    requestPago.setUuid(requestEmision.getUuid());

		return requestPago;

	}

	private void ejecucionBotonPago(RequestEmision requestEmision, ResponseEmision responseEmision) throws Exception {

		ResponseBotonPago responseBotonPago = botonPago.ejecutarBotonPago(this.armarBotonPago(requestEmision));

		if(!responseBotonPago.getCodigoRespuesta().equalsIgnoreCase("0")){
			responseEmision.setCodigoRespuesta(responseBotonPago.getCodigoRespuesta());
			responseEmision.setMensajeError(responseBotonPago.getMensajeError());
		}
	}

	private RequestBotonPago armarBotonPago(RequestEmision requestEmision){

		RequestBotonPago requestBotonPago = new RequestBotonPago();
		requestBotonPago.setTipoTransaccion("P");
		requestBotonPago.setFechaPago(requestEmision.getFechaHora());
		requestBotonPago.setFacturaNumero(requestEmision.getNumeroDocumento());
		requestBotonPago.setFechaFactura(requestEmision.getFechaHora());
		requestBotonPago.setHostname("LTDPWEC-PSV066");
		requestBotonPago.setUsuario("USRDPWORLD");
		requestBotonPago.setIp("10.24.1.31");
		requestBotonPago.setLocalidad("1");
		requestBotonPago.setMacAdress("00-50-56-8E-39-E7");

		String mensaje = requestEmision.getFechaHora() + requestEmision.getSecuencia();
		if(requestEmision.getFechaHora().contains("T")){
			String[] fechaTimeStamp = requestEmision.getFechaHora().split("T");
			mensaje = fechaTimeStamp[0].replace("-","") + requestEmision.getSecuencia();
		}

		requestBotonPago.setIdMensaje(mensaje);
		requestBotonPago.setMonto(requestEmision.getMonto());
		requestBotonPago.setIdentificacionTipo(requestEmision.getTipoIdentifica());
		requestBotonPago.setIdentificacionNumero(requestEmision.getNumeroIdentifica());
		requestBotonPago.setBancoCodigo("030");
		requestBotonPago.setCuentaTipo("Corriente");
		requestBotonPago.setCuentaNumero("99929716"); //TODO PONER ESTA CUENTA EN PRODUCCION 7923546
		requestBotonPago.setCodigoOtp("");
		requestBotonPago.setNombre("");
		requestBotonPago.setEmpresa(requestEmision.getEmpresa());
		requestBotonPago.setUuid(requestEmision.getUuid());

		return requestBotonPago;

	}

}
