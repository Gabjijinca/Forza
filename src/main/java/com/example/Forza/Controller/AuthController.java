package com.example.Forza.Controller;

import com.example.Forza.Service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/success")
    public String success() {
        return "Login com Google realizado com sucesso";
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        try {
            userService.requestPasswordReset(request.get("email"));
            return ResponseEntity.ok("Código de recuperação enviado.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> payload) {
        userService.resetPassword(
                payload.get("email"),
                payload.get("code"),
                payload.get("newPassword")
        );
        return ResponseEntity.ok("Senha alterada com sucesso!");
    }




    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("accessToken", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }






}
