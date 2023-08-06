package dpworld.com.ec.boton.pago.service;

import dpworld.com.ec.boton.pago.clientes.CobroFactura;
import dpworld.com.ec.boton.pago.clientes.GenerarOrdenInterbancaria;
import dpworld.com.ec.boton.pago.clientes.RealizarPagoPacifico;
import dpworld.com.ec.boton.pago.clientes.TokenPacifico;
import dpworld.com.ec.boton.pago.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagosServiceImpl implements IPagosService {

	@Autowired
	TokenPacifico tokenPacifico;

	@Autowired
	RealizarPagoPacifico realizarPagoPacifico;

	@Autowired
	CobroFactura cobroFactura;

	@Autowired
	GenerarOrdenInterbancaria generarOrdenInterbancaria;

	private static String soapEndpointUrl = "http://localhost:8080/bpdig/empresas/dpworld/proxy/DpWorldService";

	@Override
	public ResponseEmision emitirCobro(RequestEmision requestEmision) {

		try {

			ResponseEmision responseEmision = new ResponseEmision();
			responseEmision.setCodigoRespuesta("0");
			responseEmision.setMensajeError("SE APLICO EL PAGO EN DPWORLD");

			ResponseToken responseToken = tokenPacifico.ejecutarToken(soapEndpointUrl);

			if(!responseToken.getCodigoError().equals("0")){
				System.err.println("i004-ms-botonpago  " + "Error en obtener Token - " + responseToken.getCodigoError() + " - " + responseToken.getMensajeError());
				throw new Exception(responseToken.getMensajeError());
			}

			requestEmision.setTokenTransaccional(responseToken.getToken());

			if(requestEmision.getBancoCodigo().equals("30")){

				ResponseRealizarPagoPacifico responseRealizarPagoPacifico = realizarPagoPacifico.ejecutarPago(requestEmision, soapEndpointUrl);

				if(responseRealizarPagoPacifico.getCodigo().equals("0") && requestEmision.getIdentificacionTipo().equalsIgnoreCase("N")){

					ejecucionCobroFactura(requestEmision, responseEmision, responseRealizarPagoPacifico);
				}

				return responseEmision;

			}

			if(requestEmision.getLocalidad().equalsIgnoreCase("Interbank")){

				ejecucionCobroFactura(requestEmision, responseEmision, null);

				return responseEmision;
			}

			ResponseGenerarOrden responseGenerarOrden = generarOrdenInterbancaria.ejecutarOrdenInterbancaria(requestEmision, soapEndpointUrl);

			if(!responseGenerarOrden.getCodigo().equalsIgnoreCase("1")){
				responseEmision.setCodigoRespuesta("1");
				responseEmision.setMensajeError("NO SE APLICO EL PAGO EN DPWORLD");
			}

			return responseEmision;



		} catch (Exception e) {

			System.err.println("i004-ms-botonpago  " + e);
			ResponseEmision responseEmision = new ResponseEmision();
			responseEmision.setCodigoRespuesta("1");
			responseEmision.setMensajeError("NO SE APLICO EL PAGO EN DPWORLD");
			return responseEmision;

		}

	}

	private void ejecucionCobroFactura(RequestEmision requestEmision, ResponseEmision responseEmision, ResponseRealizarPagoPacifico responseRealizarPagoPacifico) throws Exception {

		ResponsePago responsePago = cobroFactura.ejecutarCobroFactura(this.armarPago(requestEmision, responseRealizarPagoPacifico));

		if(!responsePago.getCodigoRespuesta().equalsIgnoreCase("0")){
			responseEmision.setCodigoRespuesta("1");
			responseEmision.setMensajeError("NO SE APLICO EL PAGO EN DPWORLD");
		}
	}

	private RequestPago armarPago(RequestEmision requestEmision, ResponseRealizarPagoPacifico responseRealizarPagoPacifico){

		RequestPago requestPago = new RequestPago();
	    requestPago.setTipotransaccion(requestEmision.getTipoTransaccion());
	    requestPago.setFacturaNumero(requestEmision.getFacturaNumero());
	    requestPago.setFechaFactura(requestEmision.getFechaFactura());
	    requestPago.setFechaPago(requestEmision.getFechaPago());
	    requestPago.setMonto(requestEmision.getMonto());
	    requestPago.setIdentificacionNumero(requestEmision.getIdentificacionNumero());
		requestPago.setNumeroTrx("");
		requestPago.setComentario("");
		if(responseRealizarPagoPacifico != null){
			requestPago.setNumeroTrx(responseRealizarPagoPacifico.getIdMensaje());
			requestPago.setComentario(responseRealizarPagoPacifico.getDescripcion());
		}
	    requestPago.setEmpresa(requestEmision.getEmpresa());

		return requestPago;

	}

}
