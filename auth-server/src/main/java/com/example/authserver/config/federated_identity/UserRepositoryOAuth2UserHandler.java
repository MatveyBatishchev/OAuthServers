package com.example.authserver.config.federated_identity;

import com.example.authserver.models.User;
import com.example.authserver.models.UserProvider;
import com.example.authserver.models.UserRole;
import com.example.authserver.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Example {@link OAuthConsumer} to perform JIT provisioning of an {@link OAuth2User}.
 */
@Component
@RequiredArgsConstructor
public final class UserRepositoryOAuth2UserHandler implements OAuthConsumer<OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public void accept(OAuth2User user, String authProvider) {

        // Capture user in a data store on first authentication
        if (authProvider.equals("google-idp")) {
            if (userRepository.findByEmail(user.getName()).isEmpty()) {
                userRepository.save(User.builder()
                        .email(user.getName())
                        .name(user.getAttribute("name"))
                        .image(user.getAttribute("picture"))
                        .provider(UserProvider.GOOGLE)
                        .roles(Collections.singletonList(UserRole.USER))
                        .build());
            }
        }
    }

}
