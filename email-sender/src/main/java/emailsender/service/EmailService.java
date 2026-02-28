package emailsender.service;

import emailsender.model.EmailRequestDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    private final String APPLICATION_EMAIL = "alexandrabirlea2407@gmail.com";

    public void sendEmail(EmailRequestDto emailRequestDto) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(APPLICATION_EMAIL);
            helper.setTo(emailRequestDto.getRecipientEmail());
            helper.setSubject(emailRequestDto.getSubject());

            String styledBody = "<div style=\"font-family: Arial, sans-serif;\">" +
                    "<p style=\"color: #40ad77;\"><strong>Salutare, " + emailRequestDto.getFirstName() + " " +
                    emailRequestDto.getLastName() + "!</strong></p>" +
                    "<p>" + emailRequestDto.getBody() + "</p>" +
                    "<img src=\"https://cdn5.tropicalsky.co.uk/images/800x600/maldives-getty-image-infinity-pool.jpg\" alt=\"Signature Photo\" width=\"160\" height=\"100\">" +
                    "</div>";

            helper.setText(styledBody, true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        mailSender.send(message);
    }
}
