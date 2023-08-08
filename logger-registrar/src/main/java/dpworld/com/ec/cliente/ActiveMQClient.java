package dpworld.com.ec.cliente;

import dpworld.com.ec.modelo.entity.LoggerDp;
import dpworld.com.ec.modelo.servicio.ILoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQClient {

	@Autowired
	private ILoggerService iLoggerService;

	@Value("${dpworld.logger.mq:loggerMicroservice}")
	private String colaLogger;

	@JmsListener(destination = "loggerMicroservice")
	public void processMessage(String content) throws JSONException {

		System.out.println(content);

		JSONObject jsonObject= new JSONObject(content);

		LoggerDp loggerDp = new LoggerDp();
		loggerDp.setNombrecomponente(jsonObject.getString("nombrecomponente"));
		loggerDp.setUuid(jsonObject.getString("uuid"));
		loggerDp.setTimeStand(jsonObject.getString("timeStand"));
		loggerDp.setPeticion(jsonObject.getString("peticion"));
		loggerDp.setUrl(jsonObject.getString("url"));

		LoggerDp loggerDpfind = iLoggerService.findById(jsonObject.getString("uuid"));

		if(loggerDpfind == null){

			JSONObject log = new JSONObject();

			JSONObject detalles = new JSONObject();

			detalles.put("uuid", jsonObject.getString("uuid"));
			detalles.put("nombrecomponente", jsonObject.getString("nombrecomponente"));

			log.put("detalles", detalles);

			JSONObject mensaje = new JSONObject();
			mensaje.put("timestamp", jsonObject.getString("timeStand"));
			mensaje.put("url", jsonObject.getString("url"));
			mensaje.put("mensaje", jsonObject.getString("mensaje"));

			log.put(jsonObject.getString("peticion"), mensaje);

			loggerDp.setMensaje(log.toString());

		}

		if(loggerDpfind != null){

			JSONObject log = new JSONObject(loggerDpfind.getMensaje());

			JSONObject mensaje = new JSONObject();

			mensaje.put("timestamp", jsonObject.getString("timeStand"));
			mensaje.put("url", jsonObject.getString("url"));
			mensaje.put("mensaje", jsonObject.getString("mensaje"));

			log.put(jsonObject.getString("peticion"), mensaje);

			loggerDp.setMensaje(log.toString());

		}

		try{
			iLoggerService.save(loggerDp);
		}catch (Exception e){
			System.out.println(e.getMessage());
		}

	}

}
