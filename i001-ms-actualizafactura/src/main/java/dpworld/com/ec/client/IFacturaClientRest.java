package dpworld.com.ec.client;

import java.security.KeyStoreException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Base64;

import javax.net.ssl.SSLContext;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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

@Configuration
public class IFacturaClientRest {

	@Bean("RestTemplate-IgnoreSSL")
	public static RestTemplate restTemplate() {
		SSLContext sslContext = null;
		try {
			sslContext = SSLContextBuilder.create()
					.loadTrustMaterial((X509Certificate[] certificateChain, String authType) -> true).build();
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			e.printStackTrace();
		}

		Registry<ConnectionSocketFactory> socketRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register(URIScheme.HTTPS.getId(), new SSLConnectionSocketFactory(sslContext))
				.register(URIScheme.HTTP.getId(), new PlainConnectionSocketFactory()).build();

		HttpClient httpClient = HttpClientBuilder.create()
				.setConnectionManager(new PoolingHttpClientConnectionManager(socketRegistry))
				.setConnectionManagerShared(true).build();

		ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		return restTemplate;

	}

	@Bean("HeaderBasicAut")
	public static HttpHeaders httpHeaders() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Basic " + getBasicAuthHeader());
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		return httpHeaders;
	}

	private static String getBasicAuthHeader() {
		String credentials = "zmsapi.consumer:Dubai@2o21$";

		byte[] message = credentials.getBytes(StandardCharsets.UTF_8);
		return Base64.getEncoder().encodeToString(message);
	}

}
