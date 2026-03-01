package com.example.Forza.Security;

import com.example.Forza.Entity.User;
import com.example.Forza.Reposit.UserRepository;
import com.example.Forza.Service.TokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public OAuth2SuccessHandler(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email") != null ?
                oAuth2User.getAttribute("email") : oAuth2User.getAttribute("login") + "@github.com";

        User user = userRepository.findByEmail(email).get();
        String token = tokenService.generateToken(user);

        CookieUtil.setJwtCookie(response, token);

        response.sendRedirect("http://localhost:3000/dashboard");
    }
}