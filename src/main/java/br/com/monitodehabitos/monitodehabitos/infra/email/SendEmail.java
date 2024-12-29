package br.com.monitodehabitos.monitodehabitos.infra.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SendEmail {

    private final JavaMailSender emailSender;

    public SendEmail(JavaMailSender javaMailSender) {
        this.emailSender = javaMailSender;
    }


    public void sendEmail(String subject, String from, String messageText) {
        try {
//            var email = new SimpleMailMessage();
//            email.setFrom("bmozart.dev@gmail.com");
//            email.setSubject(subject);
//            email.setTo(from);
//            email.setText(messageText);
//            emailSender.send(email);
            System.out.println("Email enviando com sucesso");
            Thread.sleep(3000);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar email!" + e.getMessage());
        }
    }
}
