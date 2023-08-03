package dpworld.com.ec.controller;

import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.json.JSONObject;
import org.json.XML;
import org.json.JSONException;



@Component
public class ActiveMQClient {
	private static final Logger logger = LoggerFactory.getLogger(ActiveMQClient.class);
	   public static int PRETTY_PRINT_INDENT_FACTOR = 4;
	   
	   
	   
	@JmsListener(destination = "N4CREDITNOTES")
	public void processMessage(String content) {
     /*   logger.info("");
        logger.info( this.getClass().getSimpleName());
        logger.info("Received message of type: " + content.getClass().getSimpleName());
        logger.info("Received message :" + content);*/

		try {
            JSONObject xmlJSONObj = XML.toJSONObject(content);
            String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
            System.out.println(jsonPrettyPrintString);
        } catch (JSONException je) {
            System.out.println(je.toString());
        }
	}
	
	
}
