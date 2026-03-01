package com.example.Forza.Service;

import com.example.Forza.Entity.User;
import com.example.Forza.Reposit.UserRepository;
import com.example.Forza.Roles.UserRole;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");

        if (email == null) {
            email = oAuth2User.getAttribute("login") + "@github.com";
        }


        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword("");
            newUser.setUserRole(UserRole.USER);
            newUser.setEnabled(true);

            userRepository.save(newUser);
            System.out.println("Novo usuário do Google cadastrado: " + email);
        } else {
            System.out.println("Usuário do Google já existe, apenas logando: " + email);
        }

        return oAuth2User;
    }



}
