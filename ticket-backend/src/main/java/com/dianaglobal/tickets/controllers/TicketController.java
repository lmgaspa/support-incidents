package com.dianaglobal.tickets.controllers;

import com.dianaglobal.tickets.dtos.EmailDto;
import com.dianaglobal.tickets.dtos.TicketDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.micrometer.observation.annotation.Observed;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "Tickets", description = "Endpoints for sending support tickets")
public class TicketController {

    private final RabbitTemplate rabbitTemplate;
    private final String queueName;

    public TicketController(
            RabbitTemplate rabbitTemplate,
            @Value("${spring.rabbitmq.queue:incident_queue}") String queueName) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueName = queueName;
    }

    @PostMapping("/send-ticket")
    @Observed(name = "ticket.send", contextualName = "send-ticket")
    @Operation(
            summary = "Send a support ticket",
            description = "Receives the ticket payload, records correlatable logs, and publishes the message to the RabbitMQ queue for backend2 processing.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ticket published successfully"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid payload",
                            content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    public ResponseEntity<String> sendTicket(@Valid @RequestBody TicketDto ticketDto) {
        log.info("Publishing support ticket to RabbitMQ queue={} company={} priority={} emailTo={}",
                queueName, ticketDto.getCompany(), ticketDto.getPriority(), ticketDto.getEmailTo());

        EmailDto emailDto = new EmailDto();
        emailDto.setOwnerRef(ticketDto.getUser());
        emailDto.setCompany(ticketDto.getCompany());
        emailDto.setProblem(ticketDto.getProblem());
        emailDto.setPriority(ticketDto.getPriority());
        emailDto.setEmailFrom(ticketDto.getEmailFrom());
        emailDto.setEmailTo(ticketDto.getEmailTo());
        emailDto.setSubject("Support Ticket: " + ticketDto.getProblem());
        emailDto.setText(ticketDto.getDescription());

        rabbitTemplate.convertAndSend(queueName, emailDto);

        return new ResponseEntity<>("Ticket published to RabbitMQ for backend2 processing", HttpStatus.OK);
    }
}
