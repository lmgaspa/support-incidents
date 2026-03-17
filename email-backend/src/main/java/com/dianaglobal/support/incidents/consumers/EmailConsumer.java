package com.dianaglobal.support.incidents.consumers;

import com.dianaglobal.support.incidents.dtos.EmailDto;
import com.dianaglobal.support.incidents.models.EmailModel;
import com.dianaglobal.support.incidents.services.EmailService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class EmailConsumer {

    private final EmailService emailService;

    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue}", containerFactory = "rabbitListenerContainerFactory")
    public void onMessage(EmailDto dto) {
        EmailModel model = EmailModel.builder()
                .ownerRef(dto.getOwnerRef())
                .emailFrom(dto.getEmailFrom())
                .emailTo(dto.getEmailTo())
                .subject(dto.getSubject())
                .text(dto.getText())
                .company(dto.getCompany())
                .problem(dto.getProblem())
                .priority(dto.getPriority())
                .build();

        emailService.sendEmail(model);
    }
}
