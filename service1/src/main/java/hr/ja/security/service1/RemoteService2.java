package hr.ja.security.service1;

import java.util.List;

import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Service;

@Service
public class RemoteService2 {

	@Autowired
	private KeycloakRestTemplate template;

	public String getData() {
		ResponseEntity<String> response = template.getForEntity("http://localhost:8086/data1", String.class);
		return response.getBody();
	}
}