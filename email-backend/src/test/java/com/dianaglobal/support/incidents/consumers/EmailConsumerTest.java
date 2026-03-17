package com.dianaglobal.support.incidents.consumers;

import com.dianaglobal.support.incidents.dtos.EmailDto;
import com.dianaglobal.support.incidents.models.EmailModel;
import com.dianaglobal.support.incidents.services.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailConsumerTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EmailConsumer emailConsumer;

    @Test
    void onMessageShouldMapDtoToModelAndSend() {
        EmailDto dto = new EmailDto();
        dto.setOwnerRef("owner-1");
        dto.setEmailFrom("from@test.com");
        dto.setEmailTo("to@test.com");
        dto.setSubject("subject");
        dto.setText("body");
        dto.setCompany("ACME");
        dto.setProblem("outage");
        dto.setPriority("high");

        emailConsumer.onMessage(dto);

        ArgumentCaptor<EmailModel> captor = ArgumentCaptor.forClass(EmailModel.class);
        verify(emailService).sendEmail(captor.capture());
        EmailModel sent = captor.getValue();
        assertThat(sent.getOwnerRef()).isEqualTo("owner-1");
        assertThat(sent.getEmailFrom()).isEqualTo("from@test.com");
        assertThat(sent.getEmailTo()).isEqualTo("to@test.com");
        assertThat(sent.getSubject()).isEqualTo("subject");
        assertThat(sent.getText()).isEqualTo("body");
        assertThat(sent.getCompany()).isEqualTo("ACME");
        assertThat(sent.getProblem()).isEqualTo("outage");
        assertThat(sent.getPriority()).isEqualTo("high");
    }
}
