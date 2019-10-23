package hr.sedamit.security.webp;

import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.admin.client.Keycloak;
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
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CreatePremisionController {

	private static final String SCOPE_TARGET_VIEW = "target_view";

	public void demo1() {

	
		

		// REST API

		Keycloak k = Keycloak.getInstance(null, null, null, null);
		ClientResource client = k.realm("").clients().get("sadsa");
		ManagementPermissionReference permissions = client.getPermissions();

		PolicyRepresentation policy = new PolicyRepresentation();

		PoliciesResource policies = client.authorization().policies();
		TimePolicyRepresentation representation = new TimePolicyRepresentation();
		policies.time().create(representation);
		policies.create(policy);
	}

	@Context
	private HttpServletRequest request;



	
}
