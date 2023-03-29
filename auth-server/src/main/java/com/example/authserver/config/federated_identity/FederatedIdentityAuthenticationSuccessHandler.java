package com.example.authserver.config.federated_identity;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;

/**
 * An {@link AuthenticationSuccessHandler} for capturing the {@link OidcUser} or
 * {@link OAuth2User} for Federated Account Linking or JIT Account Provisioning.
 */
public final class FederatedIdentityAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final AuthenticationSuccessHandler delegate = new SavedRequestAwareAuthenticationSuccessHandler();

    private OAuthConsumer<OAuth2User> oauth2UserHandler = (user, authProvider) -> {
    };

    private OAuthConsumer<OidcUser> oidcUserHandler = (user, authProvider) -> this.oauth2UserHandler.accept(user, authProvider);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication instanceof OAuth2AuthenticationToken) {
            String authProvider = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
            if (authentication.getPrincipal() instanceof OidcUser) {
                this.oidcUserHandler.accept((OidcUser) authentication.getPrincipal(), authProvider);
            } else if (authentication.getPrincipal() != null) {
                this.oauth2UserHandler.accept((OAuth2User) authentication.getPrincipal(), authProvider);
            }
        }

        this.delegate.onAuthenticationSuccess(request, response, authentication);
    }

    public void setOAuth2UserHandler(OAuthConsumer<OAuth2User> oauth2UserHandler) {
        this.oauth2UserHandler = oauth2UserHandler;
    }

    public void setOidcUserHandler(OAuthConsumer<OidcUser> oidcUserHandler) {
        this.oidcUserHandler = oidcUserHandler;
    }

}
