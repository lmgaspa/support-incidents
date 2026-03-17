package com.dianaglobal.support.incidents.services;

import com.dianaglobal.support.incidents.enums.StatusEmail;
import com.dianaglobal.support.incidents.models.EmailModel;
import com.dianaglobal.support.incidents.repositories.EmailRepository;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private EmailRepository emailRepository;

    @Mock
    private JavaMailSender emailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(emailService, "brandName", "AndesCore Software");
        ReflectionTestUtils.setField(emailService, "configuredFrom", "support@andescore.test");
        ReflectionTestUtils.setField(emailService, "logoUrl", "https://example.com/logo.png");
    }

    @Test
    void getAllEmailsShouldDelegateToRepository() {
        List<EmailModel> emails = List.of(EmailModel.builder().emailTo("a@test.com").build());
        when(emailRepository.findAll()).thenReturn(emails);

        List<EmailModel> result = emailService.getAllEmails();

        assertThat(result).isSameAs(emails);
        verify(emailRepository).findAll();
    }

    @Test
    void findByEmailToShouldDelegateToRepository() {
        List<EmailModel> emails = List.of(EmailModel.builder().emailTo("dest@test.com").build());
        when(emailRepository.findByEmailTo("dest@test.com")).thenReturn(emails);

        List<EmailModel> result = emailService.findByEmailTo("dest@test.com");

        assertThat(result).isSameAs(emails);
        verify(emailRepository).findByEmailTo("dest@test.com");
    }

    @Test
    void sendEmailShouldMarkAsSentAndPersistResult() throws Exception {
        EmailModel email = sampleEmailModel();
        MimeMessage mimeMessage = new MimeMessage(Session.getInstance(new Properties()));
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(emailRepository.save(any(EmailModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        EmailModel saved = emailService.sendEmail(email);

        ArgumentCaptor<EmailModel> modelCaptor = ArgumentCaptor.forClass(EmailModel.class);
        verify(emailSender).send(mimeMessage);
        verify(emailRepository).save(modelCaptor.capture());

        EmailModel persisted = modelCaptor.getValue();
        assertThat(saved).isSameAs(persisted);
        assertThat(persisted.getStatusEmail()).isEqualTo(StatusEmail.SENT);
        assertThat(persisted.getSendDataEmail()).isNotNull();
        assertThat(Arrays.stream(mimeMessage.getAllRecipients()).map(Object::toString).toList())
                .contains("to@test.com");
        assertThat(mimeMessage.getSubject()).isEqualTo("Support subject");
        assertThat(mimeMessage.getContent().toString())
                .contains("AndesCore Software")
                .contains("&lt;b&gt;critical&lt;/b&gt;")
                .contains("High");
    }

    @Test
    void sendEmailShouldMarkAsErrorAndPersistWhenMailSenderFails() {
        EmailModel email = sampleEmailModel();
        MimeMessage mimeMessage = new MimeMessage(Session.getInstance(new Properties()));
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(emailRepository.save(any(EmailModel.class))).thenAnswer(invocation -> invocation.getArgument(0));
        org.mockito.Mockito.doThrow(new MailSendException("smtp down")).when(emailSender).send(mimeMessage);

        EmailModel saved = emailService.sendEmail(email);

        verify(emailRepository).save(saved);
        assertThat(saved.getStatusEmail()).isEqualTo(StatusEmail.ERROR);
        assertThat(saved.getSendDataEmail()).isNotNull();
    }

    private static EmailModel sampleEmailModel() {
        return EmailModel.builder()
                .ownerRef("owner-1")
                .emailFrom("from@test.com")
                .emailTo("to@test.com")
                .subject("Support subject")
                .text("Problem with <b>critical</b> details")
                .company("ACME")
                .problem("API outage")
                .priority("High")
                .build();
    }
}
