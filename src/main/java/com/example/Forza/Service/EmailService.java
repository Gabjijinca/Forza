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





}
