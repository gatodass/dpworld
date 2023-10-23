package dpworld.com.ec.boton.pago.service;

import dpworld.com.ec.boton.pago.clientes.CobroFactura;
import dpworld.com.ec.boton.pago.clientes.GenerarOrdenInterbancaria;
import dpworld.com.ec.boton.pago.clientes.RealizarPagoPacifico;
import dpworld.com.ec.boton.pago.clientes.ReversoCobroFactura;
import dpworld.com.ec.boton.pago.clientes.TokenPacifico;
import dpworld.com.ec.boton.pago.models.RequestEmision;
import dpworld.com.ec.boton.pago.models.RequestPago;
import dpworld.com.ec.boton.pago.models.ResponseEmision;
import dpworld.com.ec.boton.pago.models.ResponseGenerarOrden;
import dpworld.com.ec.boton.pago.models.ResponsePago;
import dpworld.com.ec.boton.pago.models.ResponseRealizarPagoPacifico;
import dpworld.com.ec.boton.pago.models.ResponseToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PagosServiceImpl implements IPagosService {

	Logger logger = LoggerFactory.getLogger(PagosServiceImpl.class);

	@Autowired
	TokenPacifico tokenPacifico;

	@Autowired
	RealizarPagoPacifico realizarPagoPacifico;

	@Autowired
	CobroFactura cobroFactura;

	@Autowired
	ReversoCobroFactura reversoCobroFactura;

	@Autowired
	GenerarOrdenInterbancaria generarOrdenInterbancaria;

	@Value("${realizar.pago.url}")
	private String soapEndpointUrl;

	@Override
	public ResponseEmision emitirCobro(RequestEmision requestEmision) {

		try {

			ResponseEmision responseEmision = new ResponseEmision();
			responseEmision.setCodigoRespuesta("0");
			responseEmision.setMensajeError("SE APLICO EL PAGO EN DPWORLD");

			ResponseToken responseToken = tokenPacifico.ejecutarToken(requestEmision, soapEndpointUrl);

			if(!responseToken.getCodigoError().equals("0")){
				System.err.println("i004-ms-botonpago  " + "Error en obtener Token - " + responseToken.getCodigoError() + " - " + responseToken.getMensajeError());
				throw new Exception(responseToken.getMensajeError());
			}

			requestEmision.setTokenTransaccional(responseToken.getToken());

			if(requestEmision.getBancoCodigo().equals("30") || requestEmision.getBancoCodigo().equals("030")){

				ResponseRealizarPagoPacifico responseRealizarPagoPacifico = realizarPagoPacifico.ejecutarPago(requestEmision, soapEndpointUrl);

				if(!responseRealizarPagoPacifico.getCodigo().equalsIgnoreCase("0001")){
					responseEmision.setCodigoRespuesta("1");
					responseEmision.setMensajeError(responseRealizarPagoPacifico.getDescripcion());
				}

				if(responseRealizarPagoPacifico.getCodigo().equals("0001") && requestEmision.getTipoTransaccion().equalsIgnoreCase("N")){
					ejecucionCobroFactura(requestEmision, responseEmision, responseRealizarPagoPacifico);
				}

				return responseEmision;

			}

			if(requestEmision.getLocalidad().equalsIgnoreCase("Interbank")){
//			if(requestEmision.getLocalidad().equalsIgnoreCase("1")){

				ejecucionCobroFactura(requestEmision, responseEmision, null);

				return responseEmision;
			}

			ResponseGenerarOrden responseGenerarOrden = generarOrdenInterbancaria.ejecutarOrdenInterbancaria(requestEmision, soapEndpointUrl);

			if(!responseGenerarOrden.getCodigo().equalsIgnoreCase("0000")){
				responseEmision.setCodigoRespuesta("1");
				responseEmision.setMensajeError(responseGenerarOrden.getDescripcion());
			}

			return responseEmision;



		} catch (Exception e) {

			System.err.println("i004-ms-botonpago  " + e);
			ResponseEmision responseEmision = new ResponseEmision();
			responseEmision.setCodigoRespuesta("1");
			responseEmision.setMensajeError(e.getMessage());
			return responseEmision;

		}

	}

	private void ejecucionCobroFactura(RequestEmision requestEmision, ResponseEmision responseEmision, ResponseRealizarPagoPacifico responseRealizarPagoPacifico) throws Exception {

		try{

			ResponsePago responsePago = cobroFactura.ejecutarCobroFactura(this.armarPago(requestEmision, responseRealizarPagoPacifico));

			if(!responsePago.getCodigoRespuesta().equalsIgnoreCase("0")){

				responseEmision.setCodigoRespuesta("1");
				responseEmision.setMensajeError(responsePago.getMensajeRespuesta());

				try {
					reversoCobroFactura.reversoCobro(requestEmision, requestEmision.getUuid(), responseRealizarPagoPacifico);
				} catch (Exception e){
					logger.error(e.getMessage());
				}
			}

		} catch (Exception e){

			responseEmision.setCodigoRespuesta("1");
			responseEmision.setMensajeError("OCURRIO UN ERROR: " + e.getMessage() + ". SE REALIZÓ EL REVERSO");

			try {
				reversoCobroFactura.reversoCobro(requestEmision, requestEmision.getUuid(), responseRealizarPagoPacifico);
			} catch (Exception ex){
				logger.error(ex.getMessage());
			}
		}
	}

	private RequestPago armarPago(RequestEmision requestEmision, ResponseRealizarPagoPacifico responseRealizarPagoPacifico){

		RequestPago requestPago = new RequestPago();
	    requestPago.setTipotransaccion(requestEmision.getTipoTransaccion());
	    requestPago.setFacturaNumero(requestEmision.getFacturaNumero());

		String fecha = requestEmision.getFechaPago();
		if(requestEmision.getFechaPago().contains("T")){
			String[] fechaTimeStamp = requestEmision.getFechaPago().split("T");
			fecha = fechaTimeStamp[0];
		}
	    requestPago.setFechaFactura(fecha);

	    requestPago.setFechaPago(fecha);
	    requestPago.setMonto(requestEmision.getMonto());
	    requestPago.setIdentificacionNumero(requestEmision.getIdentificacionNumero());
		requestPago.setNumeroTrx(requestEmision.getIdMensaje());
		requestPago.setComentario("PAGO INTERBANCARIO");
		if(responseRealizarPagoPacifico != null){
			requestPago.setNumeroTrx(responseRealizarPagoPacifico.getNutCore());
			requestPago.setComentario(responseRealizarPagoPacifico.getDescripcion());

			//TODO MOMENTANEAMENTE HASTA SABER QUE ENVIAR POR ERIC
			if(responseRealizarPagoPacifico.getNutCore().isEmpty() || responseRealizarPagoPacifico.getNutCore().isBlank()){
				requestPago.setNumeroTrx(requestEmision.getIdMensaje());
			}

		}
	    requestPago.setEmpresa(requestEmision.getEmpresa());
	    requestPago.setUuid(requestEmision.getUuid());

		return requestPago;

	}

}
