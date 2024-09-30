package com.dianaglobal.support.incidents.consumers;

import com.dianaglobal.support.incidents.dtos.EmailDto;
import com.dianaglobal.support.incidents.models.EmailModel;
import com.dianaglobal.support.incidents.services.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    @Autowired
    EmailService emailService;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listen(@Payload EmailDto emailDto) {
        EmailModel emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDto, emailModel);
        emailService.sendEmail(emailModel);
        System.out.println("Email Sending for: " + emailModel.getStatusEmail().toString());
    }
}