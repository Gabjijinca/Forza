package com.example.Forza.Service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import sendinblue.ApiClient;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailSender;
import sibModel.SendSmtpEmailTo;

import java.util.Collections;

@Service
public class EmailService {

    @Value("${BREVO_API_KEY}")
    private String apiKey;

    private final String EMAIL_REMETENTE = "gabicanjica@gmail.com";


    private TransactionalEmailsApi getApiInstance() {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        ApiKeyAuth apiKeyAuth = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKeyAuth.setApiKey(apiKey);
        return new TransactionalEmailsApi();
    }

    /**
     * Envio de código para ativação de conta (Registro)
     */
    public void sendEmail(String toEmail, String code) {
        TransactionalEmailsApi apiInstance = getApiInstance();

        SendSmtpEmailSender sender = new SendSmtpEmailSender();
        sender.setEmail(EMAIL_REMETENTE);
        sender.setName("Forza App");

        SendSmtpEmailTo to = new SendSmtpEmailTo();
        to.setEmail(toEmail);

        SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
        sendSmtpEmail.setSender(sender);
        sendSmtpEmail.setTo(Collections.singletonList(to));
        sendSmtpEmail.setSubject("Seu Código de Verificação Forza");
        sendSmtpEmail.setHtmlContent("<html><body>" +
                "<h1>Bem-vindo à Forza!</h1>" +
                "<p>Seu código de ativação é: <strong>" + code + "</strong></p>" +
                "</body></html>");

        try {
            apiInstance.sendTransacEmail(sendSmtpEmail);
            System.out.println("E-mail de verificação enviado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao enviar verificação: " + e.getLocalizedMessage());
        }
    }

    /**
     * Envio de código para recuperação de senha
     */
    public void sendPasswordResetCode(String email, String code) {
        TransactionalEmailsApi apiInstance = getApiInstance();

        SendSmtpEmailSender sender = new SendSmtpEmailSender();
        sender.setEmail(EMAIL_REMETENTE);
        sender.setName("Forza Support");

        SendSmtpEmailTo to = new SendSmtpEmailTo();
        to.setEmail(email);

        SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
        sendSmtpEmail.setSender(sender);
        sendSmtpEmail.setTo(Collections.singletonList(to));
        sendSmtpEmail.setSubject("Recuperação de Senha - Forza");

        String html = "<html><body>" +
                "<h2>Solicitação de nova senha</h2>" +
                "<p>Você solicitou a recuperação de acesso à sua conta Forza.</p>" +
                "<p>Seu código de segurança é: <b style='font-size: 20px; color: #E63946;'>" + code + "</b></p>" +
                "<p>Este código expira em 10 minutos.</p>" +
                "<hr><p style='font-size: 12px;'>Se você não solicitou esta alteração, ignore este e-mail.</p>" +
                "</body></html>";

        sendSmtpEmail.setHtmlContent(html);

        try {
            apiInstance.sendTransacEmail(sendSmtpEmail);
            System.out.println("E-mail de recuperação enviado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao enviar recuperação: " + e.getLocalizedMessage());
        }
    }
}
