package com.example.Forza.Service;

import com.example.Forza.Entity.User;
import com.example.Forza.Reposit.UserRepository;
import com.example.Forza.Roles.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class AdminInitializar implements CommandLineRunner {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${my.setup.email}")
    private String adminEmail;

    @Value("${my.setup.password}")
    private String adminPassword;

    public AdminInitializar(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail(adminEmail).isEmpty()) {

            User admin = new User();
            admin.setEmail(adminEmail);

            admin.setPassword(passwordEncoder.encode(adminPassword));

            admin.setUserRole(UserRole.ADMIN);
            admin.setEnabled(true);

            userRepository.save(admin);

            System.out.println("--------------------------------------");
            System.out.println("SISTEMA FORZA: Admin criado com sucesso!");
            System.out.println("E-mail: " + adminEmail);
            System.out.println("--------------------------------------");
        } else {
            System.out.println("SISTEMA FORZA: Admin já presente no banco de dados.");
        }
    }
}
