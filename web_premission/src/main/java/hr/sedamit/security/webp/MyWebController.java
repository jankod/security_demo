package hr.sedamit.security.webp;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.keycloak.AuthorizationContext;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.PoliciesResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.ClientAuthorizationContext;
import org.keycloak.representations.idm.ManagementPermissionReference;
import org.keycloak.representations.idm.authorization.Permission;
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

	private static final String SCOPE_TARGET_VIEW = "scope_target_view";

	@Context
	private HttpServletRequest request;

	@RequestMapping("/")
	public String home(Principal principal) {
		log.debug("user {}", principal);

//		getID(principal);

		listPremision();

		if (principal != null) {
			KeycloakAuthenticationToken p = (KeycloakAuthenticationToken) principal;
			KeycloakSecurityContext keycloakSecurityContext = p.getAccount().getKeycloakSecurityContext();
			AuthorizationContext authorizationContext = keycloakSecurityContext.getAuthorizationContext();
			if (authorizationContext == null) {
				log.debug("AuthorizationContext je null");
			} else {
				List<Permission> permissions = authorizationContext.getPermissions();
				log.debug("premisions {}", permissions);
			}
		}

		// http://localhost:8080/auth/realms/web_premission_realm/account/

		return "home";
	}

	private void listPremision() {

		try {
			List<PermissionTicketRepresentation> premissions = getAuthzClient().protection().permission().find(null,
					null, null, getKeycloakSecurityContext().getToken().getSubject(), true, true, null, null);
			log.debug("premissions {}", premissions);
		} catch (Exception e) {
			log.error("valjda nije logiran  {}", e.getMessage());
		}
	}

	@RequestMapping("/page1")
	public String page1() {
		return "page1";
	}

	@Secured("ROLE_view")
	@RequestMapping("/target")
	public String target() {
		return "target";
	}

	// @Secured("ROLE_view")
	@RequestMapping("/add_time_premission")
	public String addTimePremission() {
		return "add_time_premission";
	}

	private void getID(Principal principalReal) {
		try {
			KeycloakAuthenticationToken principal = (KeycloakAuthenticationToken) principalReal; // (KeycloakAuthenticationToken)
																									// request.getUserPrincipal();
			// String userId =
			// principal.getAccount().getKeycloakSecurityContext().getIdToken().getSubject();
			if (principal != null)
				log.debug("Principal " + principal.getAuthorities());
		} catch (Exception e) {
			log.error("Not find id: " + e.getMessage());

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

	private AuthzClient getAuthzClient() {
		return getAuthorizationContext().getClient();
	}

	private ClientAuthorizationContext getAuthorizationContext() {
		KeycloakSecurityContext keycloakSecurityContext = getKeycloakSecurityContext();
		if (keycloakSecurityContext == null) {
			return null;
		}

		return ClientAuthorizationContext.class.cast(keycloakSecurityContext.getAuthorizationContext());
	}

	private KeycloakSecurityContext getKeycloakSecurityContext() {
		Object attribute = request.getAttribute(KeycloakSecurityContext.class.getName());
		if (attribute == null) {
			log.debug("nije logiran vjeroajtno");
			return null;

		}
		return KeycloakSecurityContext.class.cast(attribute);
	}

	private void createProtectedResource(Target target) {
		try {
			HashSet<ScopeRepresentation> scopes = new HashSet<ScopeRepresentation>();

			scopes.add(new ScopeRepresentation(SCOPE_TARGET_VIEW));

//	            scopes.add(new ScopeRepresentation(SCOPE_ALBUM_DELETE));

			ResourceRepresentation resourceResource = new ResourceRepresentation(target.getName(), scopes,
					"/target/" + target.getId(), "http://localhost:8099/target");

			resourceResource.setOwner(target.getOwnerId());

			resourceResource.setOwnerManagedAccess(true);

			ResourceRepresentation response = getAuthzClient().protection().resource().create(resourceResource);

			target.setExternalId(response.getId());
		} catch (Exception e) {
			throw new RuntimeException("Could not register protected resource.", e);
		}
	}
}
