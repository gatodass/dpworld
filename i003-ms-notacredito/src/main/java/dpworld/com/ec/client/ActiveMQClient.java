package dpworld.com.ec.client;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQClient {

	@JmsListener(destination = "N4CREDITNOTES")
	public void processMessage(String content) {
		System.out.println(content);
	}
}
