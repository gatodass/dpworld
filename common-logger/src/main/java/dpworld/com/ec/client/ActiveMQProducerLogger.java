package dpworld.com.ec.client;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import dpworld.com.ec.modelo.LoggerDp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class ActiveMQProducerLogger {

	private final JmsTemplate jmsTemplate;

	@Value("${dpworld.logger.mq:loggerMicroservice}")
	private String colaLogger;

	@Autowired
	public ActiveMQProducerLogger(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void sendLogger(String uuid, String origen, String mensaje) {


		
		 Date date = new Date();
		 Timestamp timestamp2 = new Timestamp(date.getTime());

		
		LoggerDp loggerDp = new LoggerDp("38400000-8cf0-11bd-b23e-10b96e4ef00d",timestamp2.toString(),
				"{ \"tipotransaccion\": \"P\", \"facturaNumero\": \"120129\", \"fechaFactura\": \"2023-04-20\", \"fechaPago\": \"2023-04-20\", \"monto\": \"123\", \"identificacionNumero\": \"1790870073001\", \"numeroTrx\": \"5051752\", \"comentario\": \"PAGO\", \"empresa\": \"6167\" }",
				"REQUEST","http://localhos","i001_est");

	       System.out.println(new Gson().toJson(loggerDp));    

		 


		jmsTemplate.convertAndSend(colaLogger, mensaje);
	}

}