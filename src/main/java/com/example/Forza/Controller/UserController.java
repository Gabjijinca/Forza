package com.example.Forza.Controller;

import com.example.Forza.DTO.*;
import com.example.Forza.Entity.User;
import com.example.Forza.Security.CookieUtil;
import com.example.Forza.Service.TokenService;
import com.example.Forza.Service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;


    public UserController(UserService userService, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;


    }


@PostMapping("/register")
public ResponseEntity<?> register(@RequestBody @Valid UserRequestDTO userRequestDTO){
        var user = userService.register(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponseDTO.toResponse(user));
}



    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody @Valid VerificationRequest data) {
        userService.verificationCode(data.email(), data.code());
        return ResponseEntity.ok("Conta ativada com sucesso! Agora você pode fazer o login.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO data, HttpServletResponse response) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        CookieUtil.setJwtCookie(response, token);

        return ResponseEntity.ok().build();
    }



}
