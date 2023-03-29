package com.example.authserver.config.federated_identity;

import com.example.authserver.models.MyUserPrincipal;
import com.example.authserver.models.User;
import com.example.authserver.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * An {@link OAuth2TokenCustomizer} to map claims from a federated identity to
 * the {@code id_token} produced by this authorization server.
 */
@Component
@RequiredArgsConstructor
public final class CustomOAuth2TokeCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {
    private final UserRepository userRepository;

    @Override
    public void customize(JwtEncodingContext context) {
        if (context.getTokenType() == OAuth2TokenType.ACCESS_TOKEN) {
            Authentication principal = context.getPrincipal();
            User user;
            if (principal.getPrincipal() instanceof MyUserPrincipal myUserPrincipal) {
                user = myUserPrincipal.getUser();
            } else {
                Map<String, Object> claims = extractClaims(context.getPrincipal());
                user = userRepository.findByEmail((String) claims.get("email"))
                        .orElseThrow(() -> new UsernameNotFoundException("User with email " + claims.get("email") + " was not found."));
            }
            context.getClaims().claim("userId", user.getId());
            context.getClaims().claim("roles", user.getRoles());
        }
    }

    private Map<String, Object> extractClaims(Authentication principal) {
        Map<String, Object> claims;
        if (principal.getPrincipal() instanceof OidcUser oidcUser) {
            OidcIdToken idToken = oidcUser.getIdToken();
            claims = idToken.getClaims();
        } else if (principal.getPrincipal() instanceof OAuth2User oauth2User) {
            claims = oauth2User.getAttributes();
        } else {
            claims = Collections.emptyMap();
        }
        return new HashMap<>(claims);
    }

}
