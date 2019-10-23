package hr.sedamit.security.webp;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.web.filter.GenericFilterBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {

			if (!(request instanceof HttpServletRequest)) {
				throw new RuntimeException("Expecting a HTTP request");
			}

			RefreshableKeycloakSecurityContext context = (RefreshableKeycloakSecurityContext) request
					.getAttribute(KeycloakSecurityContext.class.getName());

			if (context == null) {
				handleNoSecurityContext(request, response, chain);
				chain.doFilter(request, response);
				return;
			}

			AccessToken accessToken = context.getToken();
			Integer userId = Integer.parseInt(accessToken.getOtherClaims().get("user_id").toString());
			log.debug("userID {}", userId);
			chain.doFilter(request, response);
		} catch (Exception e) {
			log.error("", e);
		}
	}

	private void handleNoSecurityContext(ServletRequest request, ServletResponse response, FilterChain chain) {
		log.debug("no security ??? ");
	}

}