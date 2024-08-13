package talent_mate_system.talent_mate_system.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import talent_mate_system.talent_mate_system.model.User;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    public boolean sendConfirmationEmail(User user) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("Email Confirmation");
            message.setText("Dear " + user.getFirstName() + ",\n\nPlease confirm your email by clicking on the following link: [Confirmation Link]\n\nThank you!");
            mailSender.send(message);
            logger.info("Confirmation email sent to: {}", user.getEmail());
            return true;
        } catch (Exception e) {
            logger.error("Error sending confirmation email to: {}", user.getEmail(), e);
            return false;
        }
    }



}
