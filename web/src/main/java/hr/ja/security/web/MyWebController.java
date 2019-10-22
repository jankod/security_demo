package hr.ja.security.web;

import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.ClientRegistrationPolicyResource;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.PoliciesResource;
import org.keycloak.representations.idm.ManagementPermissionReference;
import org.keycloak.representations.idm.authorization.PolicyRepresentation;
import org.keycloak.representations.idm.authorization.TimePolicyRepresentation;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MyWebController {

	@RequestMapping("/")
	public String home(Principal principal) {
		log.debug("user {}", principal);

		getID(principal);

		return "index";
	}

	private void getID(Principal principalReal) {
		try {
			KeycloakAuthenticationToken principal = (KeycloakAuthenticationToken) principalReal; // (KeycloakAuthenticationToken)
																									// request.getUserPrincipal();
			
			String userId = principal.getAccount().getKeycloakSecurityContext().getIdToken().getSubject();

			log.debug("User ID " + userId);
		} catch (Exception e) {
			log.error("", e);
		}
	}

	@GetMapping(path = "/logout")
	public String logout(HttpServletRequest request) throws ServletException {
		request.logout();
		return "redirect:/";
	}

	@Secured("ROLE_WEB")
	@RequestMapping("/protected_web")
	public String protectedWeb(Principal principal) {
		log.debug("user {}", principal);
		
		// REST API
		
		Keycloak k = Keycloak.getInstance(null, null, null, null);
		ClientResource client = k.realm("").clients().get("sadsa");
		ManagementPermissionReference permissions = client.getPermissions();
		
		PolicyRepresentation policy = new PolicyRepresentation();
		
		PoliciesResource policies = client.authorization().policies();
		TimePolicyRepresentation representation = new TimePolicyRepresentation();
		policies.time().create(representation);
		policies.create(policy);
		
		return "protected_web";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("/admin_page")
	public String adminPage(Principal principal) {
		log.debug("Admin user {}", principal);
		return "admin_page1";
	}

	// protected by annotation....in SecurityConfig
	@RequestMapping("/web1")
	public String web1(Principal principal) {
		log.debug("user {}", principal);
		return "web1";
	}
}
