package com.dianaglobal.tickets.controllers;

import com.dianaglobal.tickets.dtos.EmailDto;
import com.dianaglobal.tickets.dtos.TicketDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/send-ticket")
    public ResponseEntity<String> sendTicket(@RequestBody TicketDto ticketDto) {
        EmailDto emailDto = new EmailDto();
        emailDto.setOwnerRef(ticketDto.getUser());
        emailDto.setCompany(ticketDto.getCompany());
        emailDto.setProblem(ticketDto.getProblem());
        emailDto.setPriority(ticketDto.getPriority());
        emailDto.setEmailTo(ticketDto.getEmailTo());
        emailDto.setSubject("Support Ticket: " + ticketDto.getProblem());
        emailDto.setText(ticketDto.getDescription());

        rabbitTemplate.convertAndSend("incident_queue", emailDto);

        return new ResponseEntity<>("Ticket information sent to RabbitMQ", HttpStatus.OK);
    }
}
