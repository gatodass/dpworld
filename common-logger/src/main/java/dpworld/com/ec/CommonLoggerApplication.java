package dpworld.com.ec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import dpworld.com.ec.client.ActiveMQProducerLogger;

@SpringBootApplication
public class CommonLoggerApplication {
	@Autowired
	private ActiveMQProducerLogger activeMQProducer;
	
//	public static void main(String[] args) {
//		SpringApplication.run(CommonLoggerApplication.class, args);
//
//		try {
//
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	}

}
