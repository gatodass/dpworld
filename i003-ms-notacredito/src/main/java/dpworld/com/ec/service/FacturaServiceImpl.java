package dpworld.com.ec.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Base64;

import javax.net.ssl.SSLContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.URIScheme;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.ssl.SSLContextBuilder;

import dpworld.com.ec.client.IFacturaClientRest;
import dpworld.com.ec.models.Factura;

@Service
public class FacturaServiceImpl implements IFacturaService{


	
	@Autowired
	private IFacturaClientRest iFacturaClientRest; 

	
	
	public Factura facturaCobrar(Factura factura) {
		// TODO Auto-generated method stub
	
	
		URI uri;
		try {
			uri = new URI("https://fapidev.dpworld.com/amrlatmec/n4/fin/CreateARInvoice");
			HttpEntity<Factura> httpEntity = new HttpEntity<>(factura, iFacturaClientRest.httpHeaders());
	/*
	 * 		Factura objEmp = new Factura();
			objEmp.setName("cedss");
			objEmp.setCity("eeeee");
	 */

			factura = iFacturaClientRest.restTemplate().postForObject(uri, httpEntity, Factura.class);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return factura;
	}
}
