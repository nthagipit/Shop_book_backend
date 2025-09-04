package com.gipit.bookshop_backend.components.Oauth2Client;

import com.gipit.bookshop_backend.models.Role;
import com.gipit.bookshop_backend.models.User;
import com.gipit.bookshop_backend.services.impl.RoleService;
import com.gipit.bookshop_backend.services.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserService userService;

    private final RoleService roleService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User= new DefaultOAuth2UserService().loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId().toUpperCase();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        Role role=roleService.findRoleById(3);
        User user = userService.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setUsername(generateUsernameFromEmail(email));
            newUser.setFirstName(name);
            newUser.setActive(true);
            newUser.setProvider(provider);
            newUser.setListRoles(List.of(role));
            return userService.saveUser(newUser);
        });
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),oAuth2User.getAttributes(), "email");
    }
    private String generateUsernameFromEmail(String email) {
        return email.split("@")[0];
    }
}
