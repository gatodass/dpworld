package dpworld.com.ec.client;

import com.google.gson.Gson;
import dpworld.com.ec.models.LoggerDp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
public class ActiveMQProducerLogger {

	private final JmsTemplate jmsTemplate;

	@Value("${dpworld.logger.mq}")
	private String colaLogger;

	@Value("${spring.application.name}")
	private String componente;

	@Autowired
	public ActiveMQProducerLogger(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void sendLogger(String uuid, String mensaje, String url, String peticion) {

		try {

			Date date = new Date();

			Timestamp timestamp = new Timestamp(date.getTime());

			LoggerDp loggerDp = new LoggerDp(uuid, timestamp.toString(), mensaje, peticion, url, componente);

			String jsonLog = new Gson().toJson(loggerDp);

			System.out.println(jsonLog);

			jmsTemplate.convertAndSend(colaLogger, jsonLog);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("Error");
		}

	}

}
