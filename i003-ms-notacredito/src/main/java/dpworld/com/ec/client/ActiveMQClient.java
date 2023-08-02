package dpworld.com.ec.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQClient {
	private static final Logger logger = LoggerFactory.getLogger(ActiveMQClient.class);

	@JmsListener(destination = "N4CREDITNOTES")
	public void processMessage(String content) {
        logger.info("");
        logger.info( this.getClass().getSimpleName());
        logger.info("Received message of type: " + content.getClass().getSimpleName());
        logger.info("Received message :" + content);
	}
}
