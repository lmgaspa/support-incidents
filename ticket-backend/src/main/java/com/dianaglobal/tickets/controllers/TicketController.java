package com.dianaglobal.tickets.controllers;

import com.dianaglobal.tickets.configs.RabbitMQConfig;
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

    @PostMapping("/sending-ticket")
    public ResponseEntity<String> sendIncident(@RequestBody TicketDto ticketDto) {

        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, ticketDto);
        return new ResponseEntity<>("Incident sent to RabbitMQ", HttpStatus.OK);
    }
}
