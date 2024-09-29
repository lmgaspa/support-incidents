package com.dianaglobal.support.incidents.services;

import com.dianaglobal.support.incidents.enums.StatusEmail;
import com.dianaglobal.support.incidents.models.EmailModel;
import com.dianaglobal.support.incidents.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EmailService {

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;

    public EmailModel sendEmail(EmailModel emailModel) {
        emailModel.setSendDataEmail(LocalDateTime.now());
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            emailSender.send(message);

            emailModel.setStatusEmail(StatusEmail.SENT);
        }
        catch (MailException e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);

            Logger logger = Logger.getLogger(EmailService.class.getName());
            logger.log(Level.SEVERE, "Failed to send email: {0}", e.getMessage());

            // Optionally, print the stack trace for debugging
            e.printStackTrace();
        } finally {
            return emailRepository.save(emailModel);
        }
    }
}
