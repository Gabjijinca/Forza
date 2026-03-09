package com.example.Forza.Service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {


private final JavaMailSender MailSender;


    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender MailSender) {
        this.MailSender = MailSender;
    }


    public void SentVerificationCode(String to, String code){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject("Código de Verificação - Forza Montadora");
        message.setText("Seja bem-vindo à Forza! \n\n" +
                "Seu código de ativação é: " + code + "\n" +
                "Este código expira em 15 minutos.");
        MailSender.send(message);
    }

    public void sendPasswordResetCode(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("Forza <seu-email-configurado@gmail.com>"); // Nome que aparece no e-mail
        message.setTo(email);
        message.setSubject("Recuperação de Senha - Forza");
        message.setText("Olá! Você solicitou a recuperação de senha.\n\n" +
                "Seu código de verificação é: " + code + "\n" +
                "Este código expira em 15 minutos.");

        MailSender.send(message);
    }





}
