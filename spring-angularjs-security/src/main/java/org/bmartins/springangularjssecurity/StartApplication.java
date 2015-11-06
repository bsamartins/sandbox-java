package org.bmartins.springangularjssecurity;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import org.bmartins.springangularjssecurity.repository.UserRepository;
import org.bmartins.springangularjssecurity.repository.impl.UserRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.social.config.annotation.EnableSocial;

@SpringBootApplication
@EnableSocial
@Import({SecurityConfiguration.class, SocialConfiguration.class})
public class StartApplication {	
	
	public static void main(String args[]) throws Exception {
		System.setProperty("http.proxyHost", "localhost"); 
		System.setProperty("http.proxyPort", "3128");
		System.setProperty("https.proxyHost", "localhost"); 
		System.setProperty("https.proxyPort", "3128");

	    SSLContext sc  = SSLContext.getInstance("SSL");
	    sc.init(null, new X509TrustManager[] { new TrustAllCerts() }, new java.security.SecureRandom());
	    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		
		SpringApplication.run(StartApplication.class, args);			
	}
	
	public static class TrustAllCerts implements X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	}
	
	@Bean
	public UserRepository userRepository() {
		return new UserRepositoryImpl();
	}
}
