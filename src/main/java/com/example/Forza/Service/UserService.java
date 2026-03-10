package com.example.Forza.Service;

import com.example.Forza.DTO.UserRequestDTO;
import com.example.Forza.Entity.User;
import com.example.Forza.Reposit.UserRepository;
import com.example.Forza.Roles.AuthProvider;
import com.example.Forza.Roles.UserRole;
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

        newUser.setUserRole(UserRole.USER);
        newUser.setProvider(AuthProvider.LOCAL);
        newUser.setEnabled(false);

        String code = generateSecureCode();
        newUser.setVerificationCode(code);
        newUser.setCodeExpiration(LocalDateTime.now().plusMinutes(20));

        User savedUser = repository.save(newUser);

        emailService.sendEmail(savedUser.getEmail(), code);

        return savedUser;
    }

    private String generateSecureCode() {
        int number = 100000 + secureRandom.nextInt(900000);
        return String.valueOf(number);
    }

    public void verificationCode(String email, String code) {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));


        if (user.getVerificationCode() == null) {
            throw new RuntimeException("Não há código de verificação pendente para este usuário.");
        }

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



    @Transactional
    public void requestPasswordReset(String email) {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));


        if (user.getProvider() != AuthProvider.LOCAL) {
            throw new IllegalStateException("Esta conta está vinculada ao " + user.getProvider() +
                    ". Por favor, gerencie sua senha diretamente na plataforma de origem ou utilize o login social.");
        }

        String resetCode = generateSecureCode();

        user.setVerificationCode(resetCode);
        user.setCodeExpiration(LocalDateTime.now().plusMinutes(15));

        repository.save(user);

        emailService.sendPasswordResetCode(user.getEmail(), resetCode);
    }

    @Transactional
    public void resetPassword(String email, String code, String newPassword) {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        if (user.getVerificationCode() == null || !user.getVerificationCode().equals(code)) {
            throw new IllegalArgumentException("Código de recuperação inválido.");
        }

        if (user.getCodeExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("O código de recuperação expirou.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setVerificationCode(null);
        user.setCodeExpiration(null);

        repository.save(user);
    }





}