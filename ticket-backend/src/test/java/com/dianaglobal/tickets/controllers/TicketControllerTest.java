package com.dianaglobal.tickets.controllers;

import com.dianaglobal.tickets.dtos.EmailDto;
import com.dianaglobal.tickets.dtos.TicketDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TicketControllerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Test
    void sendTicketMapsPayloadAndPublishesMessage() {
        TicketController ticketController = new TicketController(rabbitTemplate, "incident_queue");

        TicketDto ticketDto = new TicketDto();
        ticketDto.setUser("Diana");
        ticketDto.setCompany("Global Corp");
        ticketDto.setEmailFrom("from@example.com");
        ticketDto.setEmailTo("support@example.com");
        ticketDto.setProblem("Login failure");
        ticketDto.setDescription("Users cannot log in.");
        ticketDto.setPriority("HIGH");

        ResponseEntity<String> response = ticketController.sendTicket(ticketDto);

        ArgumentCaptor<EmailDto> emailCaptor = ArgumentCaptor.forClass(EmailDto.class);
        verify(rabbitTemplate).convertAndSend(eq("incident_queue"), emailCaptor.capture());

        EmailDto emailDto = emailCaptor.getValue();
        assertThat(emailDto.getOwnerRef()).isEqualTo(ticketDto.getUser());
        assertThat(emailDto.getCompany()).isEqualTo(ticketDto.getCompany());
        assertThat(emailDto.getProblem()).isEqualTo(ticketDto.getProblem());
        assertThat(emailDto.getPriority()).isEqualTo(ticketDto.getPriority());
        assertThat(emailDto.getEmailFrom()).isEqualTo(ticketDto.getEmailFrom());
        assertThat(emailDto.getEmailTo()).isEqualTo(ticketDto.getEmailTo());
        assertThat(emailDto.getSubject()).isEqualTo("Support Ticket: " + ticketDto.getProblem());
        assertThat(emailDto.getText()).isEqualTo(ticketDto.getDescription());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Ticket published to RabbitMQ for backend2 processing");
    }
}
