package dpworld.com.ec.models;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "invoice")
@XmlAccessorType(XmlAccessType.FIELD)
public class invoice {

	private String draftId;
	private String InvoiceType;
	private String create;
	private String facility;
	private String customerId;
	

}
