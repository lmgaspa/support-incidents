package com.dianaglobal.support.incidents.services;

import com.dianaglobal.support.incidents.enums.StatusEmail;
import com.dianaglobal.support.incidents.models.EmailModel;
import com.dianaglobal.support.incidents.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Transactional
    public EmailModel sendEmail(EmailModel emailModel) {
        emailModel.setSendDataEmail(LocalDateTime.now());
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());

            String emailBody = "User: " + emailModel.getOwnerRef() + "\n"
                    + "Company: " + emailModel.getCompany() + "\n"
                    + "Problem: " + emailModel.getProblem() + "\n"
                    + "Priority: " + emailModel.getPriority() + "\n\n"
                    + "Description: " + emailModel.getText();

            message.setText(emailBody);
            emailSender.send(message);

            emailModel.setStatusEmail(StatusEmail.SENT);
        }
        catch (MailException e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);

        } finally {
            return emailRepository.save(emailModel);
        }
    }
}
