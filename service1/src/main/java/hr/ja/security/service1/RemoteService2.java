package hr.ja.security.service1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

@Service
public class RemoteService2 {

	@Autowired
	private OAuth2RestTemplate template;

	//@PreAuthorize("hasAnyAuthority('READ_SERVICE2_DATA1')")
	public String getData() {
		String response = template.getForObject("http://localhost:8086/data1", String.class);
		return response;
	}
}