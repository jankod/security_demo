package hr.ja.security.web;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.ClientRegistrationPolicyResource;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.PoliciesResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.ClientAuthorizationContext;
import org.keycloak.representations.idm.ManagementPermissionReference;
import org.keycloak.representations.idm.authorization.PermissionTicketRepresentation;
import org.keycloak.representations.idm.authorization.PolicyRepresentation;
import org.keycloak.representations.idm.authorization.ResourceRepresentation;
import org.keycloak.representations.idm.authorization.ScopeRepresentation;
import org.keycloak.representations.idm.authorization.TimePolicyRepresentation;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MyWebController {

	private static final String SCOPE_TARGET_VIEW = "target_view";

	@RequestMapping("/")
	public String home(Principal principal) {
		log.debug("user {}", principal);

		getID(principal);

		try {
			List<PermissionTicketRepresentation> premissions = getAuthzClient().protection().permission().find(null,
					null, null, getKeycloakSecurityContext().getToken().getSubject(), true, true, null, null);
			log.debug("premissions {}", premissions);
		} catch (Exception e) {
			log.error("Not find premission {}", e.getMessage());
		}

		return "index";
	}

	private void getID(Principal principalReal) {
		try {
			KeycloakAuthenticationToken principal = (KeycloakAuthenticationToken) principalReal; // (KeycloakAuthenticationToken)
																									// request.getUserPrincipal();

			String userId = principal.getAccount().getKeycloakSecurityContext().getIdToken().getSubject();

			log.debug("User ID " + userId);
		} catch (Exception e) {
			log.error("Exception ", e.getMessage());
		}
	}

	@GetMapping(path = "/logout")
	public String logout(HttpServletRequest request) throws ServletException {
		request.logout();
		// NE RADI
		return "redirect:/";
	}

	@Context
	private HttpServletRequest request;

	private AuthzClient getAuthzClient() {
		return getAuthorizationContext().getClient();
	}

	private ClientAuthorizationContext getAuthorizationContext() {
		return ClientAuthorizationContext.class.cast(getKeycloakSecurityContext().getAuthorizationContext());
	}

	private KeycloakSecurityContext getKeycloakSecurityContext() {
		return KeycloakSecurityContext.class.cast(request.getAttribute(KeycloakSecurityContext.class.getName()));
	}

	@Secured("ROLE_WEB")
	@RequestMapping("/protected_web")
	public String protectedWeb(Principal principal) {
		log.debug("user {}", principal);

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
