package dpworld.com.ec.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQClient {

	@Autowired
	private ActiveMQProducerLogger activeMQProducer;

	@JmsListener(destination = "loggersend")
	public void processMessage(String content) {

		activeMQProducer.sendLogger("", "ERROR", "", "", "");

	}

}
