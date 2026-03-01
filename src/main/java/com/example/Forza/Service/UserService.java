package com.example.Forza.Service;

import com.example.Forza.DTO.UserRequestDTO;
import com.example.Forza.Entity.User;
import com.example.Forza.Reposit.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    private static final SecureRandom secureRandom = new SecureRandom();

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }



     @Transactional
    public User register(UserRequestDTO userRequestDTO) {
        if (repository.findByEmail(userRequestDTO.email()).isPresent()) {
            throw new IllegalArgumentException("E-mail já está sendo utilizado");
        }

        User newUser = new User();
        newUser.setEmail(userRequestDTO.email());

        String encryptedPassword = passwordEncoder.encode(userRequestDTO.password());
        newUser.setPassword(encryptedPassword);

        newUser.setUserRole(userRequestDTO.userRole());
        newUser.setEnabled(false);

        String code = generateSecureCode();
        newUser.setVerificationCode(code);
        newUser.setCodeExpiration(LocalDateTime.now().plusMinutes(20));

        User savedUser = repository.save(newUser);

        emailService.SentVerificationCode(savedUser.getEmail(), code);

        return savedUser;
    }

    private String generateSecureCode() {
        int number = 100000 + secureRandom.nextInt(900000);
        return String.valueOf(number);
    }

    public void verificationCode(String email, String code) {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        if (user.isEnabled()) {
            throw new RuntimeException("Este usuário já está ativo.");
        }

        if (!user.getVerificationCode().equals(code)) {
            throw new IllegalArgumentException("O código informado é inválido");
        }

        if (user.getCodeExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("O código expirou.");
        }

        user.setEnabled(true);
        user.setVerificationCode(null);
        user.setCodeExpiration(null);

        repository.save(user);
    }
}