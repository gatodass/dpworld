package dpworld.com.ec.cliente;

import dpworld.com.ec.modelo.servicio.ILoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ActiveMQClient {

	@Autowired
	private ILoggerService iLoggerService;

	@Value("${dpworld.logger.mq:loggerMicroservice}")
	private String colaLogger;

	@JmsListener(destination = "loggerMicroservice")
	public void processMessage(String content) {
		System.out.println(content);
		//iLoggerService.save(null);

	}

}
